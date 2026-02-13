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

    pub async fn get_one_by_username(&self, username: &str) -> Result<User, AppError> {
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

    pub async fn update_password(&self, password: String, user_id: i32) -> Result<bool, AppError> {
        let a = sqlx::query!(
            r#"update t_user set password = $1 where id = $2;"#,
            password,
            user_id
        )
        .execute(&self.pool)
        .await
        .map_err(|_| AppError::Internal("更新用户密码失败".to_string()))?;
        Ok(a.rows_affected() > 0)
    }

    pub async fn get_one_by_id(&self, user_id: i32) -> Result<User, AppError> {
        sqlx::query_as!(User, r#"select * from t_user where id=$1;"#, user_id)
            .fetch_optional(&self.pool)
            .await?
            .ok_or(AppError::UserNotFound)
    }
}
