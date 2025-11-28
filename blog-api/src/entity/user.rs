use sea_orm::entity::prelude::*;
#[derive(Clone, Debug, PartialEq, DeriveEntityModel)]
#[sea_orm(table_name = "t_user")]
pub struct Model {
    #[sea_orm(primary_key, auto_increment = true)]
    pub id: i32,
    #[sea_orm(column_type = "String(StringLen::None)")]
    pub nickname: String,
    #[sea_orm(column_type = "String(StringLen::None)")]
    pub username: String,
    #[sea_orm(column_type = "String(StringLen::None)")]
    pub password: String,
    #[sea_orm(column_type = "String(StringLen::None)")]
    pub avatar: String,
    #[sea_orm(column_type = "String(StringLen::None)", nullable)]
    pub web_site: Option<String>,
    #[sea_orm(column_type = "String(StringLen::None)", nullable)]
    pub intro: Option<String>,
    #[sea_orm(column_type = "String(StringLen::None)", nullable)]
    pub email: Option<String>,
    #[sea_orm(column_type = "String(StringLen::None)", nullable)]
    pub ip_address: Option<String>,
    #[sea_orm(column_type = "String(StringLen::None)", nullable)]
    pub ip_source: Option<String>,
    pub login_type: i16,  // PG smallint → i16
    pub is_disable: bool, // PG boolean → bool
    #[sea_orm(nullable)]
    pub login_time: Option<DateTime>,
    pub create_time: DateTime,
    #[sea_orm(nullable)]
    pub update_time: Option<DateTime>,
}

#[derive(Copy, Clone, Debug, EnumIter, DeriveRelation)]
pub enum Relation {}

impl ActiveModelBehavior for ActiveModel {}
