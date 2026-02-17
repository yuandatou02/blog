use crate::repo::friend_repo::FriendRepo;
use crate::repo::tag_repo::TagRepo;
use crate::service::friend_service::FriendService;
use std::sync::Arc;

pub struct TagService {
    tag_repo: Arc<TagRepo>,
}

impl TagService {
    pub fn new(pool: sqlx::PgPool) -> Self {
        TagService {
            tag_repo: Arc::new(TagRepo::new(pool)),
        }
    }
}
