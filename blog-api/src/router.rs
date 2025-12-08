
use axum::{Router, routing::post};

use crate::{AppState, handler::user_handler::login};

pub fn user_router() -> Router<AppState> {
    Router::new().route("/login", post(login))
}
