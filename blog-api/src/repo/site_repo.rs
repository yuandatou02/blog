use crate::entity::site_config::SiteConfig;
use crate::error::AppError;
use sqlx::PgPool;

pub struct SiteRepo {
    pool: PgPool,
}

impl SiteRepo {
    pub fn new(pool: PgPool) -> Self {
        Self { pool }
    }

    pub async fn get_one(&self) -> Result<SiteConfig, AppError> {
        sqlx::query_as!(
            SiteConfig,
            r#"select * from t_site_config where id = $1;"#,
            1
        )
        .fetch_one(&self.pool)
        .await
        .map_err(|e| AppError::Internal(e.to_string()))
    }
}
