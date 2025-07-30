use crate::entity::account::{LoginReq, LoginRes};
use axum::{Json, Router, extract::State, http::StatusCode, routing::post};
use sqlx::PgPool;

async fn login(
    State(pool): State<PgPool>,
    Json(req): Json<LoginReq>,
) -> Result<Json<LoginRes>, StatusCode> {
    // 查询数据库
    let rec = sqlx::query!(
        "select * from account where email = $1",
        req.email).fetch_optional(&pool)
        .await
        .map_err(|_| StatusCode::INTERNAL_SERVER_ERROR)?
        .ok_or(StatusCode::UNAUTHORIZED)?;
}

pub fn router() -> Router<PgPool> {
    Router::new().route("/login", post(login))
}
