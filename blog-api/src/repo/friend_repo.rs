use crate::entity::friend::Friend;
use crate::error::AppError;
use crate::model::request::{FriendRequest, PageRequest};
use chrono::Local;
use sqlx::{PgPool, QueryBuilder};

pub struct FriendRepo {
    pool: PgPool,
}

impl FriendRepo {
    pub fn new(pool: PgPool) -> Self {
        FriendRepo { pool }
    }

    pub async fn get_page_list(&self, page_request: PageRequest) -> Result<Vec<Friend>, AppError> {
        let offset = (page_request.current - 1) * page_request.size;

        let mut query_builder = QueryBuilder::new(
            "SELECT id, name, color, avatar, url, introduction, create_time, update_time FROM t_friend ",
        );

        // 动态条件
        if let Some(ref keyword) = page_request.keyword {
            query_builder.push("WHERE name LIKE ");
            query_builder.push_bind(format!("%{}%", keyword)); // 模糊匹配
        }

        query_builder.push(" ORDER BY create_time DESC LIMIT ");
        query_builder.push_bind(page_request.size);
        query_builder.push(" OFFSET ");
        query_builder.push_bind(offset);

        query_builder
            .build_query_as::<Friend>()
            .fetch_all(&self.pool)
            .await
            .map_err(|e| AppError::Internal(e.to_string()))
    }

    pub async fn add_friend(&self, friend: FriendRequest) -> Result<(), AppError> {
        let now = Local::now().naive_local();
        sqlx::query!(
            "INSERT INTO t_friend (name, color, avatar, url, introduction, create_time, update_time) VALUES ($1, $2, $3, $4, $5, $6, $7)",
            friend.name,
            friend.color,
            friend.avatar,
            friend.url,
            friend.introduction,
            now,
            now
        )
        .execute(&self.pool)
        .await
        .map_err(|e| AppError::Internal(e.to_string()))?;
        Ok(())
    }
    pub async fn update_friend(&self, friend: Friend) -> Result<(), AppError> {
        let now = Local::now().naive_local();
        sqlx::query!(
            "UPDATE t_friend SET name = $1, color = $2, avatar = $3, url = $4, introduction = $5, update_time = $6 WHERE id = $7",
            friend.name,
            friend.color,
            friend.avatar,
            friend.url,
            friend.introduction,
            now,
            friend.id
        )
        .execute(&self.pool)
        .await
        .map_err(|e| AppError::Internal(e.to_string()))?;
        Ok(())
    }

    pub async fn delete_friends(&self, ids: Vec<i32>) -> Result<u64, AppError> {
        if ids.is_empty() {
            return Ok(0);
        }

        let result = sqlx::query!("DELETE FROM t_friend WHERE id = ANY($1)", &ids)
            .execute(&self.pool)
            .await
            .map_err(|e| AppError::Internal(e.to_string()))?;

        Ok(result.rows_affected())
    }
}
