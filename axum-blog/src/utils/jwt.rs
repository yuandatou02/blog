use axum::http::HeaderMap;
use jsonwebtoken::{Algorithm, DecodingKey, EncodingKey, Header, Validation, decode, encode};
use serde::{Deserialize, Serialize};
use tracing::info;

use crate::utils::myerror::{AppError, Result};

// 常量定义
const TOKEN_PREFIX: &str = "Bearer ";
const TOKEN_EXPIRATION_HOURS: i64 = 24;

#[derive(Debug, Serialize, Deserialize)]
pub struct Claims {
    pub sub: String,
    pub exp: usize,
}
// 创建JWT令牌
pub fn create_jwt_token(id: i32) -> String {
    let exp =
        (chrono::Utc::now() + chrono::Duration::hours(TOKEN_EXPIRATION_HOURS)).timestamp() as usize;

    let claims = Claims {
        sub: id.to_string(),
        exp,
    };

    let secret = std::env::var("JWT_SECRET").expect("读取JWT_SECRET配置失败!");
    let token = encode(
        &Header::new(Algorithm::HS512), // 使用更强的算法
        &claims,
        &EncodingKey::from_secret(secret.as_bytes()),
    )
    .expect("令牌创建失败！");

    info!("JWT token created for user {}: {}", id, token);
    token
}

// 从JWT令牌中获取用户ID
pub fn extract_id(headers: &HeaderMap) -> Result<i32> {
    let token = headers
        .get("Authorization")
        .ok_or(AppError::MissingToken)?
        .to_str()
        .map_err(|_| AppError::MissingToken)?
        .strip_prefix(TOKEN_PREFIX)
        .ok_or(AppError::MissingToken)?;
    let secret = std::env::var("JWT_SECRET").map_err(|_| AppError::ConfigError)?;

    let token_data = decode::<Claims>(
        token,
        &DecodingKey::from_secret(secret.as_bytes()),
        &Validation::new(Algorithm::HS512), // 与编码算法一致
    )
    .map_err(|_| AppError::MissingToken)?;

    token_data
        .claims
        .sub
        .parse::<i32>()
        .map_err(|_| AppError::InvalidUserId)
}
