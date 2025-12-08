use argon2::{
    Argon2, Error, Params, PasswordHash, PasswordVerifier,
    password_hash::{PasswordHasher, SaltString, rand_core::OsRng},
};

/// 使用 Argon2 算法对密码进行哈希处理
///
/// 该函数使用 Argon2id 算法和随机生成的盐值对密码进行哈希处理，
/// 生成安全的密码哈希值用于存储和验证。
///
/// # 参数
/// * `password` - 需要进行哈希处理的原始密码字符串
///
/// # 返回值
/// * `Ok(String)` - 成功时返回经过哈希处理的密码字符串
/// * `Err(argon2::password_hash::Error)` - 失败时返回相应的错误信息
///
/// # 示例
/// ```
/// let hashed = hash_password("my_password")?;
/// ```
pub fn hash_password(password: &str) -> Result<String, argon2::password_hash::Error> {
    // 生成随机盐值用于密码哈希
    let salt = SaltString::generate(&mut OsRng);

    // 创建 Argon2 实例，使用 Argon2id 算法和默认参数
    let argon2 = Argon2::new(
        argon2::Algorithm::Argon2id,
        argon2::Version::V0x13,
        Params::default(),
    );

    // 对密码进行哈希处理并返回结果
    Ok(argon2
        .hash_password(password.as_bytes(), &salt)?
        .to_string())
}

/// 验证密码是否与哈希值匹配
///
/// 该函数使用 Argon2 算法验证明文密码与其哈希值是否匹配。
///
/// # 参数
/// * `password` - 待验证的明文密码字符串
/// * `hashed_password` - 已存储的哈希密码字符串
///
/// # 返回值
/// 如果密码匹配返回 true，否则返回 false
pub fn verify_password(password: &str, hashed_password: &str) -> bool {
    // 解析存储的哈希密码
    let parsed = match PasswordHash::new(hashed_password) {
        Ok(p) => p,
        Err(_) => return false,
    };

    // 使用 Argon2 算法验证密码
    Argon2::default()
        .verify_password(password.as_bytes(), &parsed)
        .is_ok()
}
