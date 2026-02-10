use crate::entity::user::User;
use crate::error::AppError;
use sqlx::{Error, PgPool};

pub struct UserRepo {
    pool: PgPool,
}

impl UserRepo {
    pub fn new(pool: PgPool) -> Self {
        Self { pool }
    }

    pub async fn get_one(&self, username: &str) -> Result<User, AppError> {
        sqlx::query_as::<_, User>(r#"select * from t_user where username=$1;"#)
            .bind(username)
            .fetch_optional(&self.pool)
            .await?
            .ok_or(AppError::UserNotFound)
    }
}
