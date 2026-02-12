use crate::AppState;
use crate::handler::user_handler::{get_user_info, get_user_menu, login, logout};
use axum::Router;
use axum::routing::{get, post};

pub fn user_router() -> Router<AppState> {
    Router::new()
        // 健康检查（不需要认证）
        .route("/login", post(login))
        .route("/logout", get(logout))
        .route("/admin/user/getUserInfo", get(get_user_info))
        .route("/admin/user/getUserMenu", get(get_user_menu))
}
