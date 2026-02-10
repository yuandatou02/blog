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
    let user = state
        .user_service
        .get_user(login_request.username.as_str())
        .await?;
    Ok(Json(R::ok(user.nickname, "登录成功")))
}
