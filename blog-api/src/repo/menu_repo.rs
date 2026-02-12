use crate::error::AppError;
use crate::model::response::UserMenuResp;
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

    pub async fn select_menu(&self, user_id: i32) -> Result<Vec<UserMenuResp>, AppError> {
        sqlx::query_as!(
            UserMenuResp,
            r#"SELECT DISTINCT m.id,
                m.parent_id,
                m.menu_name,
                m.menu_type,
                m.path,
                m.icon,
                m.order_num,
                m.component,
                m.is_hidden
FROM t_menu m
         INNER JOIN t_role_menu rm ON m.id = rm.menu_id
         INNER JOIN t_user_role ur ON rm.role_id = ur.role_id
         INNER JOIN t_role r ON ur.role_id = r.id
WHERE m.menu_type in ('M', 'C')
  AND m.is_disable = false
  AND r.is_disable = false
  AND ur.user_id = $1
ORDER BY m.parent_id, m.order_num"#,
            user_id
        )
        .fetch_all(&self.pool)
        .await
        .map_err(|_| AppError::Internal("查询菜单失败".to_string()))
    }
}
