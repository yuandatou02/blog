use crate::entity::account::{LoginReq, LoginRes};
use axum::{Json, Router, extract::State, http::StatusCode, routing::post};
use bcrypt::verify;
use chrono::{Duration, Utc};
use jsonwebtoken::{encode, EncodingKey, Header};
use serde::Serialize;
use sqlx::PgPool;
use uuid::Uuid;

async fn login(
    State(pool): State<PgPool>,
    Json(req): Json<LoginReq>,
) -> Result<Json<LoginRes>, StatusCode> {
    // 查询数据库
    let rec = sqlx::query!("select * from account where email = $1", req.email)
        .fetch_optional(&pool)
        .await
        .map_err(|_| StatusCode::INTERNAL_SERVER_ERROR)?
        .ok_or(StatusCode::UNAUTHORIZED)?;
    let valid =
        verify(&req.password, &rec.password).map_err(|_| StatusCode::INTERNAL_SERVER_ERROR)?;
    if !valid {
        return Err(StatusCode::UNAUTHORIZED);
    }

    let token = make_jwt(rec.id);
    Ok(Json(LoginRes { token }))
}

fn make_jwt(uid: Uuid) -> String {
    #[derive(Serialize)]
    struct Claims {
        sub: String,
        exp: usize,
    }
    let claims = Claims {
        sub: uid.to_string(),
        exp: (Utc::now() + Duration::days(7)).timestamp() as usize,
    };
    encode(
        &Header::default(),
        &claims,
        &EncodingKey::from_secret("secret".as_ref()),
    )
    .unwrap()
}

pub fn router() -> Router<PgPool> {
    Router::new().route("/login", post(login))
}
