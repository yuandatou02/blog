use chrono::{Duration, Utc};
use jsonwebtoken::{DecodingKey, EncodingKey, Header, Validation, decode, encode};
use serde::{Deserialize, Serialize};

use crate::errors::auth_error::AuthError;

#[derive(Debug, Serialize, Deserialize)]
pub struct Claims {
    // 用户标识
    sub: i64,
    // 过期时间（UTC 时间戳）
    exp: i64,
    // 签发时间
    iat: i64,
}

/// 为指定用户创建JWT令牌
///
/// 该函数使用提供的用户ID和密钥生成一个有效期为2小时的JWT令牌。
/// 令牌包含用户标识、签发时间和过期时间等标准声明。
///
/// # 参数
/// * `user_id` - 用户唯一标识符，将作为令牌的subject声明
/// * `secret` - 用于签名令牌的密钥字节数组
///
/// # 返回值
/// * `Ok(String)` - 成功时返回生成的JWT令牌字符串
/// * `Err(AuthError)` - 失败时返回认证错误
///
/// # 错误处理
/// 当令牌编码过程中发生错误时，会返回相应的AuthError错误
pub fn crate_token(user_id: i64, secret: &[u8]) -> Result<String, AuthError> {
    // 获取当前UTC时间作为令牌签发时间基础
    let now = Utc::now();

    // 构造JWT声明信息，包括用户ID、签发时间和2小时后过期时间
    let claims = Claims {
        sub: user_id,
        iat: now.timestamp(),
        exp: (now + Duration::hours(2)).timestamp(),
    };

    // 使用默认头部、声明信息和密钥编码生成JWT令牌
    Ok(encode(
        &Header::default(),
        &claims,
        &EncodingKey::from_secret(secret),
    )
    .map_err(AuthError::TokenCreate)?)
}

/// 验证JWT令牌并返回声明信息
///
/// 该函数使用提供的密钥验证JWT令牌的有效性，如果验证成功则返回令牌中的声明信息。
///
/// # 参数
/// * `token` - 需要验证的JWT令牌字符串
/// * `secret` - 用于验证令牌的密钥字节数组
///
/// # 返回值
/// * `Ok(Claims)` - 验证成功时返回令牌中的声明信息
/// * `Err(AuthError)` - 验证失败时返回认证错误
///
/// # 错误处理
/// 如果令牌验证失败，将返回AuthError::TokenVerify错误
pub fn verify_token(token: &str, secret: &[u8]) -> Result<Claims, AuthError> {
    // 创建HS256算法的验证配置
    let validation = Validation::new(jsonwebtoken::Algorithm::HS256);

    // 解码并验证JWT令牌
    let token_data = decode::<Claims>(token, &DecodingKey::from_secret(secret), &validation)
        .map_err(AuthError::TokenVerify)?;
    Ok(token_data.claims)
}
