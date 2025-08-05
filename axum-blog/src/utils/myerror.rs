use axum::{
    Json,
    http::StatusCode,
    response::{IntoResponse, Response},
};
use serde_json::json;

// 自定义错误
pub enum AppError {
    NotFound,           // 未找到资源
    BadRequest(String), // 请求参数错误
    Unauthorized,       // 未授权
    Internal(String),   // 网络错误
    Validation(String), // 验证错误
}

impl IntoResponse for AppError {
    fn into_response(self) -> Response {
        let (status, message) = match self {
            Self::NotFound => (StatusCode::NOT_FOUND, "Not Found".to_owned()),
            Self::BadRequest(msg) => (StatusCode::BAD_REQUEST, msg),
            Self::Unauthorized => (StatusCode::UNAUTHORIZED, "Unauthorized".to_owned()),
            Self::Internal(msg) => (StatusCode::INTERNAL_SERVER_ERROR, msg),
            Self::Validation(msg) => (StatusCode::UNPROCESSABLE_ENTITY, msg),
        };
        let body = Json(json!({
            "error": message
        }));
        (status, body).into_response()
    }
}

pub type Result<T> = std::result::Result<T, AppError>;
