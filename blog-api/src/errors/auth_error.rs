use jsonwebtoken::errors::Error as JwtError;
use thiserror::Error;

#[derive(Debug, Error)]
pub enum AuthError {
    #[error("JWT 签发失败")]
    TokenCreate(JwtError),

    #[error("JWT 验证失败")]
    TokenVerify(JwtError),
}
