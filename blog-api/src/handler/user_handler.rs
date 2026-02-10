use crate::AppState;
use crate::error::AppError;
use crate::model::app_result::R;
use crate::model::request::LoginRequest;
use axum::Json;
use axum::extract::State;

pub async fn login(
    State(state): State<AppState>,
    Json(login_request): Json<LoginRequest>,
) -> Result<Json<R<String>>, AppError> {
    let token = state
        .user_service
        .login(login_request)
        .await?;
    Ok(Json(R::ok(token, "登录成功")))
}
