use crate::entity::user::User;
use crate::error::AppError;
use crate::repo::user_repo::UserRepo;
use std::sync::Arc;

pub struct UserService {
    user_repo: Arc<UserRepo>,
}

impl UserService {
    pub fn new(pool: sqlx::PgPool) -> Self {
        UserService {
            user_repo: Arc::new(UserRepo::new(pool)),
        }
    }

    pub async fn get_user(&self, username: &str) -> Result<User, AppError> {
        self.user_repo.get_one(username).await
    }
}
