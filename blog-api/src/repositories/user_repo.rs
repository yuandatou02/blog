use sea_orm::entity::prelude::*;
use sea_orm::{
    ActiveModelBehavior, DeriveEntityModel, DeriveRelation, EnumIter, prelude::DateTime,
};
use serde::{Deserialize, Serialize};

#[derive(Debug, Clone, PartialEq, DeriveEntityModel, Serialize, Deserialize)]
#[sea_orm(table_name = "t_user")]
pub struct Model {
    #[sea_orm(primary_key)]
    pub id: i32,
    pub nickname: String,
    pub username: String,
    pub password: String,
    pub avatar: String,
    #[sea_orm(column_type = "Text")]
    pub web_site: Option<String>,
    #[sea_orm(column_type = "Text")]
    pub intro: Option<String>,
    #[sea_orm(column_type = "Text")]
    pub email: Option<String>,
    #[sea_orm(column_type = "Text")]
    pub ip_address: Option<String>,
    #[sea_orm(column_type = "Text")]
    pub ip_source: Option<String>,
    pub login_type: i16,
    pub is_disable: bool,
    pub login_time: Option<DateTime>,
    pub create_time: DateTime,
    pub update_time: Option<DateTime>,
}

#[derive(Copy, Clone, Debug, EnumIter, DeriveRelation)]
pub enum Relation {}

impl ActiveModelBehavior for ActiveModel {}
