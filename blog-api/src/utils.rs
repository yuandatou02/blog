pub mod jwt;

use crate::error::AppError;
use argon2::{Argon2, PasswordHash, PasswordVerifier};

pub fn verify_password(hash_password: &str, password: &str) -> Result<bool, AppError> {
    let parsed_hash = PasswordHash::new(hash_password)
        .map_err(|_| AppError::Internal("密码解析错误".to_string()))?;
    let argon2 = Argon2::default();

    match argon2.verify_password(password.as_bytes(), &parsed_hash) {
        Ok(()) => Ok(true),
        Err(argon2::password_hash::Error::Password) => Ok(false), // 密码错误
        Err(_) => Err(AppError::Internal("密码验证错误".to_string())),
    }
}
