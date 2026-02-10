use std::sync::Arc;
use crate::entity::user::User;
use crate::repo::user_repo::UserRepo;

pub struct UserService{
    user_repo:Arc<UserRepo>
}

impl UserService{
    pub fn new(pool: sqlx::PgPool) -> Self{
        UserService{
            user_repo: Arc::new(UserRepo::new(pool))
        }
    }

    pub async fn get_user(&self, username:&str) -> Result<Option<User>,sqlx::Error>{
        self.user_repo.get_one(username).await
    }
}