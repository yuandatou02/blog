use axum::response::{IntoResponse, Response};

use crate::models::api_result::ApiResult;

#[derive(Debug)]
pub enum AppError {
    BadRequest(String),   // 400
    Unauthorized(String), // 401
    Forbidden(String),    // 403
    NotFound(String),     // 404
    Internal(String),     // 500
}

impl IntoResponse for AppError {
    fn into_response(self) -> Response {
        let (code, message) = match self {
            AppError::BadRequest(msg) => (400, msg),
            AppError::Unauthorized(msg) => (401, msg),
            AppError::Forbidden(msg) => (403, msg),
            AppError::NotFound(msg) => (404, msg),
            AppError::Internal(msg) => (500, msg),
        };

        let result = ApiResult::<()>::error(code, message);
        result.into_response()
    }
}

impl From<anyhow::Error> for AppError {
    fn from(err: anyhow::Error) -> Self {
        AppError::Internal(err.to_string())
    }
}
