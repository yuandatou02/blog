use axum::{
    Json,
    response::{IntoResponse, Response},
};
use serde::Serialize;

#[derive(Debug, Serialize)]
pub struct ApiResult<T> {
    /// 返回状态
    pub flag: bool,
    /// 状态码
    pub code: i32,
    /// 返回信息
    pub msg: String,
    /// 返回数据
    pub data: T,
}

impl<T> ApiResult<T> {
    /// 成功响应
    pub fn success(data: T, msg: impl Into<String>) -> Self {
        Self {
            flag: true,
            code: 200,
            msg: msg.into(),
            data,
        }
    }

    /// 成功响应（无数据）
    pub fn success_msg(msg: impl Into<String>) -> ApiResult<()> {
        ApiResult {
            flag: true,
            code: 200,
            msg: msg.into(),
            data: (),
        }
    }

    /// 创建成功
    pub fn created(data: T, msg: impl Into<String>) -> Self {
        Self {
            flag: true,
            code: 201,
            msg: msg.into(),
            data,
        }
    }

    /// 错误响应
    pub fn error(code: i32, msg: impl Into<String>) -> ApiResult<()> {
        ApiResult {
            flag: false,
            code,
            msg: msg.into(),
            data: (),
        }
    }
}

/// 实现 IntoResponse 以便在 Handler 中直接返回
impl<T> IntoResponse for ApiResult<T>
where
    T: Serialize,
{
    fn into_response(self) -> Response {
        let status_code = match self.code {
            200..=299 => axum::http::StatusCode::OK,
            400 => axum::http::StatusCode::BAD_REQUEST,
            401 => axum::http::StatusCode::UNAUTHORIZED,
            403 => axum::http::StatusCode::FORBIDDEN,
            404 => axum::http::StatusCode::NOT_FOUND,
            500 => axum::http::StatusCode::INTERNAL_SERVER_ERROR,
            _ => axum::http::StatusCode::OK,
        };

        (status_code, Json(self)).into_response()
    }
}

/// 空数据响应
pub type EmptyData = ();
