use chrono::NaiveDateTime;
use serde::{Deserialize, Serialize};
use sqlx::FromRow;

/// 友链表实体
#[derive(Debug, Clone, FromRow, Serialize, Deserialize)]
#[serde(rename_all = "camelCase")]
pub struct Friend {
    /// 友链id
    pub id: i32,

    /// 友链名称
    pub name: String,

    /// 友链颜色
    pub color: String,

    /// 友链头像
    pub avatar: String,

    /// 友链地址
    pub url: String,

    /// 友链介绍
    pub introduction: String,

    /// 创建时间
    pub create_time: NaiveDateTime,

    /// 更新时间
    pub update_time: Option<NaiveDateTime>,
}
