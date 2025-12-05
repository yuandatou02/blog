use axum::{
    Json,
    response::{IntoResponse, Response},
};
use sea_orm::sqlx::types::chrono::{DateTime, Utc};
use serde::Serialize;

// API 通用响应
#[derive(Debug, Serialize)]
pub struct ApiResponse<T> {
    pub success: bool,
    pub data: Option<T>,
    pub message: String,
    pub timestamp: DateTime<Utc>,
    pub code: u16,
}

impl<T> ApiResponse<T> {
    pub fn success(data: T, message: &str) -> Self {
        Self {
            success: true,
            data: Some(data),
            message: message.to_string(),
            timestamp: Utc::now(),
            code: 200,
        }
    }

    pub fn error(message: &str, code: u16) -> Self {
        Self {
            success: false,
            data: None,
            message: message.to_string(),
            timestamp: Utc::now(),
            code,
        }
    }
}

impl<T: Serialize> IntoResponse for ApiResponse<T> {
    fn into_response(self) -> Response {
        Json(self).into_response()
    }
}
