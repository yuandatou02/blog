use chrono::NaiveDateTime;
use serde::Serialize;
use sqlx::FromRow;

#[derive(Debug, Clone, FromRow, Serialize)]
pub struct Tag {
    pub id: i32,
    pub tag_name: String,
    pub create_time: NaiveDateTime,
    pub update_time: Option<NaiveDateTime>,
}
