use crate::entity::friend::Friend;
use crate::error::AppError;
use crate::model::request::{FriendRequest, PageRequest};
use crate::repo::friend_repo::FriendRepo;
use std::sync::Arc;

pub struct FriendService {
    friend_repo: Arc<FriendRepo>,
}

impl FriendService {
    pub fn new(pool: sqlx::PgPool) -> Self {
        FriendService {
            friend_repo: Arc::new(FriendRepo::new(pool)),
        }
    }

    pub async fn get_friends(&self, page_request: PageRequest) -> Result<Vec<Friend>, AppError> {
        self.friend_repo.get_page_list(page_request).await
    }

    pub async fn delete_friend(&self, id: Vec<i32>) -> Result<(), AppError> {
        self.friend_repo.delete_friends(id).await?;
        Ok(())
    }

    pub async fn add_friend(&self, friend: FriendRequest) -> Result<(), AppError> {
        self.friend_repo.add_friend(friend).await
    }

    pub async fn update_friend(&self, friend: Friend) -> Result<(), AppError> {
        self.friend_repo.update_friend(friend).await
    }
}
