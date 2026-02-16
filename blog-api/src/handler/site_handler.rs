use crate::AppState;
use crate::constants::redis_constant;
use crate::entity::site_config::SiteConfig;
use crate::error::AppError;
use crate::model::app_result::R;
use crate::utils::jwt::verify_token;
use axum::Json;
use axum::extract::{Multipart, State};
use axum_extra::TypedHeader;
use axum_extra::headers::Authorization;
use axum_extra::headers::authorization::Bearer;

pub async fn get_site_config(
    State(state): State<AppState>,
    TypedHeader(auth): TypedHeader<Authorization<Bearer>>,
) -> Result<Json<R<SiteConfig>>, AppError> {
    verify_token(auth.token()).await?;
    let site_config = state
        .redis_service
        .get(redis_constant::REDIS_SITE_CONFIG)
        .await?;
    if let Some(value) = site_config {
        let site_config = serde_json::from_str::<SiteConfig>(&value)
            .map_err(|_| AppError::Internal("反序列化失败".to_string()))?;
        Ok(Json(R::ok(site_config, "获取网站配置信息成功")))
    } else {
        let site_config = state.site_service.get_site().await?;
        state
            .redis_service
            .set(redis_constant::REDIS_SITE_CONFIG, &site_config, 86400)
            .await?;
        Ok(Json(R::ok(site_config, "获取用户信息成功")))
    }
}

pub async fn upload_site_img(
    State(state): State<AppState>,
    TypedHeader(auth): TypedHeader<Authorization<Bearer>>,
    multipart: Multipart,
) -> Result<Json<R<String>>, AppError> {
    verify_token(auth.token()).await?;
    let site_img_url = state.site_service.upload_site_img(multipart).await?;
    Ok(Json(R::ok(site_img_url, "上传成功")))
}

pub async fn update_site_config(
    State(state): State<AppState>,
    TypedHeader(auth): TypedHeader<Authorization<Bearer>>,
    Json(site_config): Json<SiteConfig>,
) -> Result<Json<R<String>>, AppError> {
    verify_token(auth.token()).await?;
    state.site_service.update_site(site_config).await?;
    state
        .redis_service
        .delete(redis_constant::REDIS_SITE_CONFIG)
        .await?;
    Ok(Json(R::ok_message("网站配置更新成功")))
}
