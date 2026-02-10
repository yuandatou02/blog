use crate::error::AppError;
use crate::repo::user_repo::UserRepo;
use std::sync::Arc;
use crate::model::request::LoginRequest;
use crate::utils::jwt::generate_token;
use crate::utils::verify_password;

pub struct UserService {
    user_repo: Arc<UserRepo>,
}

impl UserService {
    pub fn new(pool: sqlx::PgPool) -> Self {
        UserService {
            user_repo: Arc::new(UserRepo::new(pool)),
        }
    }

    pub async fn login(&self, login_request: LoginRequest) -> Result<String, AppError> {
        let user = self.user_repo.get_one(&login_request.username).await?;
        match verify_password(&user.password,&login_request.password) {
            Ok(_)=> {
                generate_token(user.id,3).map_err(|_| AppError::Internal("token生成失败!".to_string()))
            },
            Err(_)=> {
                Err(AppError::PasswordVerifyError)
            }
        }
    }
}
