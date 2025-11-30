use axum::{Json, extract::State};

use crate::{
    AppState,
    models::{api_result::ApiResult, error::AppError, request::login_request::LoginRequest},
};

pub async fn login(
    State(state): State<AppState>,
    Json(payload): Json<LoginRequest>,
) -> Result<ApiResult<String>, AppError> {
    // 输入验证
    if payload.username.is_empty() || payload.password.is_empty() {
        return Err(AppError::BadRequest("用户名和密码不能为空".to_string()));
    }
    // 调用 Service 层
    let login_result = state
        .user_service
        .login(payload, &state.jwt_utils)
        .await
        .map_err(|e| AppError::Unauthorized(e.to_string()))?;

    Ok(ApiResult::success(login_result, "登录成功"))
}
