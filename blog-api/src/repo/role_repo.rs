use crate::error::AppError;
use sqlx::PgPool;

pub struct RoleRepo {
    pool: PgPool,
}

impl RoleRepo {
    pub fn new(pool: PgPool) -> Self {
        Self { pool }
    }

    pub async fn get_role(&self, user_id: i32) -> Result<Vec<String>, AppError> {
        sqlx::query_scalar!(
            r#"SELECT r.id
        FROM t_role r
        INNER JOIN t_user_role ur ON r.id = ur.role_id
        WHERE ur.user_id = $1
        AND r.is_disable = false"#,
            user_id
        )
        .fetch_all(&self.pool)
        .await
        .map_err(|_| AppError::Internal("获取角色失败".to_string()))
    }
}
