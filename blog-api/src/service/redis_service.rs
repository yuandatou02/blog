use redis::AsyncTypedCommands;
use serde::Serialize;
use crate::error::AppError;

#[derive(Clone)]
pub struct RedisService {
    redis_client: redis::Client,
}

impl RedisService {
    pub fn new(redis_url: &str) -> Self {
        Self {
            redis_client:redis::Client::open(redis_url).expect("redis连接错误")
        }
    }

    /// 封装 get 操作，内部处理 mut
    pub async fn get(&self, key: &str) -> Result<Option<String>, AppError> {
        let mut conn = self.redis_client.get_multiplexed_async_connection().await.map_err(|_|AppError::Internal("redis连接错误".to_string()))?;
        Ok(conn.get(key).await.map_err(|e| AppError::RedisError(e.to_string()))?)
    }

    pub async fn set<T: Serialize>(
        &self,
        key: &str,
        value: &T,
    ) -> Result<(), AppError> {
        // 1. 序列化为 JSON
        let json = serde_json::to_string(value)
            .map_err(|e| AppError::SerializeError(e.to_string()))?;

        // 2. 获取连接
        let mut conn = self
            .redis_client
            .get_multiplexed_async_connection()
            .await
            .map_err(|_| AppError::Internal("redis连接错误".to_string()))?;

        // 3. 存储（不需要 as_str()，String 可以解引用为 &str）
        conn.set(key, &json)
            .await
            .map_err(|e| AppError::RedisError(e.to_string()))?;

        Ok(())
    }

    pub async fn del(&self, key: &str) -> Result<(), AppError> {
        let mut conn = self
            .redis_client
            .get_multiplexed_async_connection()
            .await
            .map_err(|_| AppError::Internal("redis连接错误".to_string()))?;

        conn.del(key)
            .await
            .map_err(|e| AppError::RedisError(e.to_string()))?;

        Ok(())
    }
}