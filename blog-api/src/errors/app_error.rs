use axum::{
    Json,
    http::StatusCode,
    response::{IntoResponse, Response},
};
use sea_orm::sqlx::types::chrono;
use serde_json::json;
use thiserror::Error;
#[derive(Debug, Error)]
pub enum AppError {
    #[error("未授权: {0}")]
    Unauthorized(String),

    #[error("禁止访问: {0}")]
    Forbidden(String),

    #[error("未找到: {0}")]
    NotFound(String),

    #[error("请求参数错误: {0}")]
    BadRequest(String),

    #[error("用户不存在")]
    UserNotFound,

    #[error("无效的凭据")]
    InvalidCredentials,

    #[error("用户已被禁用")]
    UserDisabled,

    #[error("内部服务器错误")]
    Internal(#[from] Box<dyn std::error::Error + Send + Sync>),
}

impl IntoResponse for AppError {
    fn into_response(self) -> Response {
        let (status, message) = match self {
            AppError::Unauthorized(msg) => (StatusCode::UNAUTHORIZED, msg),
            AppError::Forbidden(msg) => (StatusCode::FORBIDDEN, msg),
            AppError::NotFound(msg) => (StatusCode::NOT_FOUND, msg),
            AppError::BadRequest(msg) => (StatusCode::BAD_REQUEST, msg),
            AppError::UserNotFound => (StatusCode::UNAUTHORIZED, "用户不存在".to_string()),
            AppError::InvalidCredentials => {
                (StatusCode::UNAUTHORIZED, "用户名或密码错误".to_string())
            }
            AppError::UserDisabled => (StatusCode::FORBIDDEN, "用户已被禁用".to_string()),
            AppError::Internal(msg) => {
                tracing::error!("内部错误: {}", msg);
                (
                    StatusCode::INTERNAL_SERVER_ERROR,
                    "服务器内部错误".to_string(),
                )
            }
        };

        let body = Json(json!({
            "success": false,
            "message": message,
            "code": status.as_u16(),
            "timestamp": chrono::Utc::now().to_rfc3339(),
        }));

        (status, body).into_response()
    }
}
