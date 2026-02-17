use crate::AppState;
use crate::handler::friend_handler::{add_friend, delete_friend, get_list_friends, update_friend};
use crate::handler::site_handler::{get_site_config, update_site_config, upload_site_img};
use crate::handler::user_handler::{
    get_user_info, get_user_menu, login, logout, update_user_password,
};
use axum::Router;
use axum::routing::{delete, get, post, put};

pub fn user_router() -> Router<AppState> {
    Router::new()
        // 健康检查（不需要认证）
        .route("/login", post(login))
        .route("/logout", get(logout))
        .route("/admin/user/getUserInfo", get(get_user_info))
        .route("/admin/user/getUserMenu", get(get_user_menu))
        .route("/admin/password", put(update_user_password))
}

pub fn site_router() -> Router<AppState> {
    Router::new()
        // 健康检查（不需要认证）
        .route("/admin/site/list", get(get_site_config))
        .route("/admin/site/upload", post(upload_site_img))
        .route("/admin/site/update", put(update_site_config))
}

pub fn friend_router() -> Router<AppState> {
    Router::new()
        // 健康检查（不需要认证）
        .route("/admin/friend/list", get(get_list_friends))
        .route("/admin/friend/update", put(update_friend))
        .route("/admin/friend/delete", delete(delete_friend))
        .route("/admin/friend/add", post(add_friend))
}
