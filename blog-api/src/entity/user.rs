use sea_orm::{entity::prelude::*, sqlx::types::chrono};
use serde::{Deserialize, Serialize};

#[derive(Clone, Debug, PartialEq, DeriveEntityModel, Serialize, Deserialize)]
#[sea_orm(table_name = "user")]
pub struct Model {
    #[sea_orm(primary_key)]
    pub id: i64,

    #[sea_orm(column_name = "username", column_type = "String(StringLen::N(255))")]
    pub username: String,

    #[sea_orm(column_name = "password", column_type = "String(StringLen::N(255))")]
    pub password: String,

    #[sea_orm(column_name = "nickname", column_type = "String(StringLen::N(255))")]
    pub nickname: String,

    #[sea_orm(column_name = "avatar", column_type = "String(StringLen::N(255))")]
    pub avatar: String,

    #[sea_orm(column_name = "email", column_type = "String(StringLen::N(255))")]
    pub email: String,

    #[sea_orm(column_name = "create_time")]
    pub create_time: chrono::NaiveDateTime,

    #[sea_orm(column_name = "update_time")]
    pub update_time: chrono::NaiveDateTime,

    #[sea_orm(column_name = "role", column_type = "String(StringLen::N(255))")]
    pub role: String,
}

#[derive(Copy, Clone, Debug, EnumIter, DeriveRelation)]
pub enum Relation {}

impl ActiveModelBehavior for ActiveModel {}
