use axum::Json;
use axum::http::StatusCode;
use axum::response::{IntoResponse, Response};
use serde_json::json;
use thiserror::Error;

#[derive(Debug, Error)]
pub enum AppError {
    #[error("用户不存在")]
    UserNotFound,

    #[error("token错误")]
    TokenError,

    #[error("密码错误请重试")]
    PasswordVerifyError,

    // 500 - 数据库错误（自动转换）
    #[error("数据库错误")]
    Sqlx(#[from] sqlx::Error),
    // 500 - 其他内部错误
    #[error("内部错误: {0}")]
    Internal(String),

    #[error("redis错误: {0}")]
    RedisError(String),

    #[error("序列化错误: {0}")]
    SerializeError(String)
}

impl AppError {
    pub fn status_code(&self) -> StatusCode {
        match self {
            AppError::UserNotFound => StatusCode::OK,
            AppError::PasswordVerifyError => StatusCode::OK,
            AppError::Internal(_) => StatusCode::INTERNAL_SERVER_ERROR, // 500
            AppError::Sqlx(_) => StatusCode::INTERNAL_SERVER_ERROR,     // 500
            AppError::TokenError => StatusCode::UNAUTHORIZED,
            AppError::RedisError(_) => StatusCode::INTERNAL_SERVER_ERROR,
            AppError::SerializeError(_) => StatusCode::INTERNAL_SERVER_ERROR,
        }
    }

    // 对外暴露的错误消息（隐藏内部细节）
    pub fn client_message(&self) -> String {
        match self {
            // 内部错误不暴露细节给客户端
            AppError::Sqlx(_) | AppError::Internal(_) => "服务器内部错误".to_string(),
            // 业务错误直接返回
            _ => self.to_string(),
        }
    }
}

// 转换为 Axum 响应
impl IntoResponse for AppError {
    fn into_response(self) -> Response {
        let status = self.status_code();
        let message = self.client_message();

        let body = Json(json!({
            "flag": false,
            "code": status.as_u16(),
            "message": message,
            "data": null
        }));

        (status, body).into_response()
    }
}
