pub mod file;
pub mod jwt;

use crate::error::AppError;
use argon2::password_hash::SaltString;
use argon2::password_hash::rand_core::OsRng;
use argon2::{Argon2, PasswordHash, PasswordHasher, PasswordVerifier};

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

/// 生成密码哈希
pub fn hash_password(password: &str) -> Result<String, AppError> {
    // 生成随机盐值
    let salt = SaltString::generate(&mut OsRng);

    // Argon2id 是推荐的变体（抵抗 GPU/ASIC 攻击）
    let argon2 = Argon2::default();

    // 哈希密码
    let password_hash = argon2
        .hash_password(password.as_bytes(), &salt)
        .map_err(|_| AppError::Internal("密码加密出现错误".to_string()))?
        .to_string();

    Ok(password_hash)
}
