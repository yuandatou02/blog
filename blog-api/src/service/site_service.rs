use crate::entity::site_config::SiteConfig;
use crate::error::AppError;
use crate::repo::site_repo::SiteRepo;
use crate::utils::file::upload_image;
use axum::extract::Multipart;
use std::sync::Arc;

pub struct SiteService {
    site_repo: Arc<SiteRepo>,
}

impl SiteService {
    pub fn new(pool: sqlx::PgPool) -> Self {
        SiteService {
            site_repo: Arc::new(SiteRepo::new(pool)),
        }
    }

    pub async fn get_site(&self) -> Result<SiteConfig, AppError> {
        self.site_repo.get_one().await
    }

    pub async fn upload_site_img(&self, multipart: Multipart) -> Result<String, AppError> {
        upload_image(multipart).await
    }

    pub async fn update_site(&self, site_config: SiteConfig) -> Result<(), AppError> {
        self.site_repo.update(site_config).await
    }
}
