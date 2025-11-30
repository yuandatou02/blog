use anyhow::Context;
use sea_orm::{ColumnTrait, DatabaseConnection, EntityTrait, QueryFilter};

use crate::{
    models::request::login_request::LoginRequest,
    repositories::{UserColumn, user},
    utils::{jwt::JwtUtils, tools::verify_password},
};

#[derive(Debug, Clone)]
pub struct UserService {
    db: DatabaseConnection,
}

impl UserService {
    pub fn new(db: DatabaseConnection) -> Self {
        Self { db }
    }
    // 登录方法
    pub async fn login(
        &self,
        login_request: LoginRequest,
        jwt_utils: &JwtUtils,
    ) -> anyhow::Result<String> {
        // 根据用户名查找用户
        let one = user::find()
            .filter(UserColumn::Username.eq(&login_request.username))
            .one(&self.db)
            .await
            .context("数据库查询失败")?
            .ok_or_else(|| anyhow::anyhow!("用户名不存在"))?;

        // 2. 检查用户是否被禁用
        if one.is_disable {
            return Err(anyhow::anyhow!("用户已被禁用"));
        }
        // 3. 验证密码
        let is_password_vaild =
            verify_password(&login_request.password, &one.password).context("密码验证失败")?;

        if !is_password_vaild {
            return Err(anyhow::anyhow!("用户名或密码错误"));
        }
        // 使用jwt生成token
        let token = jwt_utils.generate_token(one.id).context("生成token失败")?;
        Ok(token)
    }
}
