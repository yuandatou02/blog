use std::sync::Arc;

use salvo::{Depot, Request, Response, handler, http::StatusCode, writing::Json};
use tracing::info;

use crate::{AppState, entity::user, model::user::LoginRequest};

#[handler]
pub async fn login(request: &mut Request, response: &mut Response, depot: &Depot) {
    let login_request = match request.parse_json::<LoginRequest>().await {
        Ok(data) => data,
        Err(_) => {
            response.status_code(StatusCode::BAD_REQUEST);
            response.render(Json("Invalid request data"));
            return;
        }
    };
    //取出数据库链接
    let state = depot.obtain::<Arc<AppState>>().unwrap();
    let db = &state.conn;
    let result = match user::Entity::find_user(db, &login_request).await {
        Ok(data) => data,
        Err(_) => {
            response.status_code(StatusCode::BAD_REQUEST);
            response.render(Json("Invalid request data"));
            return;
        }
    };
    if let Some(value) = result {
        info!("Login success, user: {:?}", value);
    }
}
