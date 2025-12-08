use axum::{Json, extract::State, response::IntoResponse};

use crate::{
    AppState,
    models::{request::LoginForm, response::ApiResponse},
    service::user_service::UserService,
    utils::password::verify_password,
};

// 完整的登录处理器
#[axum::debug_handler]
pub async fn login(
    State(app): State<AppState>,
    Json(payload): Json<LoginForm>,
) -> impl IntoResponse {
    // 调用服务
    let user = UserService::login(&app.user_repo, &payload.username).await;
    if let Some(user) = user {
        // 验证密码是否匹配
        if verify_password(&payload.password, &user.password) {
            tracing::info!("用户登录成功: username={}", payload.username);
            // 创建token
            
        }
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
