use anyhow::Result;
use argon2::{Argon2, PasswordHash, PasswordVerifier};

pub fn verify_password(password: &str, hashed_password: &str) -> Result<bool> {
    let parsed_hash = PasswordHash::new(hashed_password)
        .map_err(|e| anyhow::anyhow!("密码哈希解析失败: {}", e))?;

    let result = Argon2::default()
        .verify_password(password.as_bytes(), &parsed_hash)
        .is_ok();

    Ok(result)
}
