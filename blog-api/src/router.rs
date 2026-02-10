use crate::AppState;
use crate::handler::user_handler::login;
use axum::Router;
use axum::routing::post;

pub fn user_router() -> Router<AppState> {
    Router::new()
        // 健康检查（不需要认证）
        .route("/login", post(login))
}
