use crate::error::AppError;
use chrono::{Duration, Utc};
use jsonwebtoken::{Algorithm, DecodingKey, EncodingKey, Header, Validation, decode, encode};
use serde::{Deserialize, Serialize};

#[derive(Debug, Serialize, Deserialize)]
pub struct Claims {
    pub sub: i32, // 用户ID (subject)
    pub exp: i64, // 过期时间
    pub iat: i64, // 签发时间
}

const SECRET: &'static str = "651908384";

pub fn generate_token(
    user_id: i32,
    expire_hours: i64,
) -> Result<String, jsonwebtoken::errors::Error> {
    let now = Utc::now();
    let exp = now + Duration::hours(expire_hours);

    let claims = Claims {
        sub: user_id,
        exp: exp.timestamp(),
        iat: now.timestamp(),
    };
    encode(
        &Header::default(),
        &claims,
        &EncodingKey::from_secret(SECRET.as_bytes()),
    )
}

pub async fn verify_token(token: &str) -> Result<i32, AppError> {
    let validation = Validation::new(Algorithm::HS256);

    let claims = decode::<Claims>(
        token,
        &DecodingKey::from_secret(SECRET.as_bytes()),
        &validation,
    )
    .map(|data| data.claims)
    .map_err(|_| AppError::TokenError)?;
    Ok(claims.sub)
}
