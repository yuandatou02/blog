use std::sync::Arc;

use axum::{Router, routing::post};

use crate::{AppState, handler::user_handler::login};

pub fn user_router() -> Router<Arc<AppState>> {
    Router::new().route("/login", post(login))
}
