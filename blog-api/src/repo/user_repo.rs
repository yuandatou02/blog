use crate::entity::user::User;
use crate::error::AppError;
use sqlx::PgPool;

pub struct UserRepo {
    pool: PgPool,
}

impl UserRepo {
    pub fn new(pool: PgPool) -> Self {
        Self { pool }
    }

    pub async fn get_one(&self, username: &str) -> Result<User, AppError> {
        sqlx::query_as!(User, r#"select * from t_user where username=$1;"#, username)
            .fetch_optional(&self.pool)
            .await?
            .ok_or(AppError::UserNotFound)
    }

    pub async fn get_avatar(&self, user_id: i32) -> Result<String, AppError> {
        sqlx::query_scalar!(r#"select avatar from t_user where id=$1;"#, user_id)
            .fetch_one(&self.pool)
            .await
            .map_err(|_| AppError::Internal("查询用户头像失败".to_string()))
    }
}
