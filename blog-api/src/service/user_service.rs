use crate::error::AppError;
use crate::model::request::LoginRequest;
use crate::model::response::UserInfoResp;
use crate::repo::menu_repo::MenuRepo;
use crate::repo::role_repo::RoleRepo;
use crate::repo::user_repo::UserRepo;
use crate::utils::jwt::generate_token;
use crate::utils::verify_password;
use std::sync::Arc;

pub struct UserService {
    user_repo: Arc<UserRepo>,
    menu_repo: Arc<MenuRepo>,
    role_repo: Arc<RoleRepo>,
}

impl UserService {
    pub fn new(pool: sqlx::PgPool) -> Self {
        UserService {
            user_repo: Arc::new(UserRepo::new(pool.clone())),
            menu_repo: Arc::new(MenuRepo::new(pool.clone())),
            role_repo: Arc::new(RoleRepo::new(pool)),
        }
    }

    pub async fn login(&self, login_request: LoginRequest) -> Result<String, AppError> {
        let user = self.user_repo.get_one(&login_request.username).await?;
        match verify_password(&user.password, &login_request.password) {
            Ok(_) => generate_token(user.id, 3)
                .map_err(|_| AppError::Internal("token生成失败!".to_string())),
            Err(_) => Err(AppError::PasswordVerifyError),
        }
    }

    pub async fn get_user_info(&self, user_id: i32) -> Result<UserInfoResp, AppError> {
        let avatar = self.user_repo.get_avatar(user_id).await?;
        let role_list = self.role_repo.get_role(user_id).await?;
        let permission_list = self
            .menu_repo
            .select_permission(user_id.to_string())
            .await?;
        Ok(UserInfoResp {
            id: user_id,
            avatar,
            role_list,
            permission_list,
        })
    }
}
