#[cfg(test)]
mod tests {
    use argon2::{
        Argon2, PasswordHash, PasswordVerifier,
        password_hash::{PasswordHasher, SaltString, rand_core::OsRng},
    };

    fn hash_password(password: &str) -> String {
        let salt = SaltString::generate(&mut OsRng);
        Argon2::default()
            .hash_password(password.as_bytes(), &salt)
            .unwrap()
            .to_string()
    }

    #[test]
    fn test_hash_verify() {
        let password = "123456";
        let hash = hash_password(password);
        println!("{}",hash);
        assert!(
            argon2::Argon2::default()
                .verify_password(password.as_bytes(), &PasswordHash::new(&hash).unwrap())
                .is_ok()
        );
    }
}
