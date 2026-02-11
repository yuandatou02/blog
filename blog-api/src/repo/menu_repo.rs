use crate::error::AppError;
use sqlx::PgPool;

pub struct MenuRepo {
    pool: PgPool,
}

impl MenuRepo {
    pub fn new(pool: PgPool) -> Self {
        Self { pool }
    }

    pub async fn select_permission(
        &self,
        role_id: String,
    ) -> Result<Vec<Option<String>>, AppError> {
        sqlx::query_scalar!(
            r#"SELECT DISTINCT m.perms
            FROM t_menu m INNER JOIN t_role_menu rm ON m.id = rm.menu_id
             WHERE rm.role_id = $1 AND m.is_disable = false"#,
            role_id
        )
        .fetch_all(&self.pool)
        .await
        .map_err(|_| AppError::Internal("查询权限失败".to_string()))
    }
}
