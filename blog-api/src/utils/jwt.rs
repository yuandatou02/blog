use anyhow::{Context, Result};
use chrono::{Duration, Utc};
use jsonwebtoken::{EncodingKey, Header, encode};
use serde::{Deserialize, Serialize};

// JWT 声明（Payload）
#[derive(Debug, Serialize, Deserialize)]
pub struct Claims {
    pub sub: i32, // 用户ID
    pub exp: i64, // 过期时间
    pub iat: i64, // 签发时间
}

// JWT 配置
#[derive(Clone, Debug)]
pub struct JwtConfig {
    pub secret: String,
    pub expiration_hours: i64,
}

impl Default for JwtConfig {
    fn default() -> Self {
        Self {
            secret: "651908384".to_string(),
            expiration_hours: 24,
        }
    }
}

#[derive(Debug, Clone)]
pub struct JwtUtils {
    config: JwtConfig,
}

impl JwtUtils {
    pub fn new(config: JwtConfig) -> Self {
        Self { config }
    }
    /// 生成 JWT
    pub fn generate_token(&self, user_id: i32) -> Result<String> {
        let now = Utc::now();
        let exp = now + Duration::hours(self.config.expiration_hours);
        let claims = Claims {
            sub: user_id,
            exp: exp.timestamp(),
            iat: now.timestamp(),
        };
        let token = encode(
            &Header::default(),
            &claims,
            &EncodingKey::from_secret(self.config.secret.as_ref()),
        )
        .context("JWT token 生成失败")?;
        Ok(token)
    }
}
