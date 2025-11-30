use axum::{Router, routing::post};

use crate::{AppState, handler::user_handler};

pub fn public_router() -> Router<AppState> {
    Router::new().route("/login", post(user_handler::login))
}
