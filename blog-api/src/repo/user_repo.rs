use std::sync::Arc;

use sea_orm::{ColumnTrait, DatabaseConnection, EntityTrait, QueryFilter};

use crate::entity::{user, user_entity};

pub struct UserRepo {
    db: DatabaseConnection,
}

impl UserRepo {
    /// 创建一个新的实例
    ///
    /// # 参数
    /// * [db] - 数据库连接对象
    ///
    /// # 返回值
    /// 返回一个新的实例，包含传入的数据库连接
    pub fn new(db: DatabaseConnection) -> Arc<Self> {
        Arc::new(Self { db })
    }

    /// 根据用户名查找用户信息
    ///
    /// 该函数通过用户名在数据库中查找对应的用户记录，返回用户模型数据。
    ///
    /// # 参数
    /// * [username]- 要查找的用户名字符串引用
    ///
    /// # 返回值
    /// * `Option<user::Model>` - 如果找到用户则返回Some(用户模型)，否则返回None
    ///
    /// # Panics
    /// 当数据库查询发生错误时会panic
    pub async fn by_username(&self, username: &str) -> Option<user::Model> {
        // 构建查询条件：根据用户名过滤，只取第一条匹配记录
        user_entity::find()
            .filter(user::Column::Username.eq(username))
            .one(&self.db)
            .await
            .expect("查询失败")
    }
}
