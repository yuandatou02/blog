use std::sync::Arc;

use axum::{Json, extract::State, response::IntoResponse};

use crate::{
    AppState,
    models::{request::LoginForm, response::ApiResponse},
    service::user_service::UserService,
};

// 完整的登录处理器
#[axum::debug_handler]
pub async fn login(
    State(app): State<Arc<AppState>>,
    Json(payload): Json<LoginForm>,
) -> impl IntoResponse {
    // 调用服务
    let user = UserService::login(&app.user_repo, &payload.username).await;
    if let Some(user) = user {
        // 登录成功
        tracing::info!("用户登录成功: username={}", payload.username);
        // 构造 API 响应并返回 JSON
        ApiResponse::success(user, "登录成功")
    } else {
        // 登录失败
        tracing::warn!("用户登录失败: username={}", payload.username);
        // 返回统一错误结构或自定义状态码 + 错误信息
        ApiResponse::error("登录失败，请稍后重试", 401)
    }
}
