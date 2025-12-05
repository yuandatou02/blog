use crate::{entity::user, repo::user_repo};

pub struct UserService;

impl UserService {
    pub async fn login(user_repo: &user_repo::UserRepo, username: &str) -> Option<user::Model> {
        user_repo.by_username(username).await
    }
}
