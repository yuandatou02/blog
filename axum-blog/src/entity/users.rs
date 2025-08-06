use sea_orm::entity::prelude::*;
use serde::{Deserialize, Serialize};

#[derive(Clone, Debug, PartialEq, DeriveEntityModel, Serialize, Deserialize)]
#[sea_orm(table_name = "t_user")]
#[sea_orm(comment = "用户表")]
pub struct Model {
    #[sea_orm(primary_key, comment = "用户id")]
    pub id: i32,

    #[sea_orm(comment = "用户昵称", column_type = "String(StringLen::N(50))")]
    pub nickname: String,

    #[sea_orm(comment = "用户名", column_type = "String(StringLen::N(50))")]
    pub username: String,

    #[sea_orm(comment = "用户密码", column_type = "String(StringLen::N(100))")]
    pub password: String,

    #[sea_orm(comment = "头像", column_type = "String(StringLen::N(100))")]
    pub avatar: String,

    #[sea_orm(
        comment = "个人网站",
        column_type = "String(StringLen::N(100))",
        default_value = ""
    )]
    pub web_site: String,

    #[sea_orm(
        comment = "个人简介",
        column_type = "String(StringLen::N(100))",
        default_value = ""
    )]
    pub intro: String,

    #[sea_orm(comment = "邮箱", column_type = "String(StringLen::N(50))", default_value = "")]
    pub email: String,

    #[sea_orm(
        comment = "登录ip",
        column_type = "String(StringLen::N(50))",
        default_value = ""
    )]
    pub ip_address: String,

    #[sea_orm(
        comment = "登录地址",
        column_type = "String(StringLen::N(50))",
        default_value = ""
    )]
    pub ip_source: String,

    #[sea_orm(comment = "登录方式 (1邮箱 2QQ 3Gitee 4Github)", default_value = "0")]
    pub login_type: LoginType,

    #[sea_orm(comment = "是否禁用 (0否 1是)", default_value = "0")]
    pub is_disable: IsDisable,

    #[sea_orm(comment = "登录时间", column_type = "Timestamp", nullable)]
    pub login_time: Option<DateTime>,

    #[sea_orm(comment = "创建时间", column_type = "Timestamp")]
    pub create_time: DateTime,

    #[sea_orm(comment = "更新时间", column_type = "Timestamp", nullable)]
    pub update_time: Option<DateTime>,
}

#[derive(Debug, Clone, Copy, PartialEq, Eq, EnumIter, DeriveActiveEnum, Serialize, Deserialize)]
#[sea_orm(rs_type = "i16", db_type = "SmallInteger")]
pub enum LoginType {
    #[sea_orm(num_value = 0)]
    Unknown,
    #[sea_orm(num_value = 1)]
    Email,
    #[sea_orm(num_value = 2)]
    Qq,
    #[sea_orm(num_value = 3)]
    Gitee,
    #[sea_orm(num_value = 4)]
    Github,
}

#[derive(Debug, Clone, Copy, PartialEq, Eq, EnumIter, DeriveActiveEnum, Serialize, Deserialize)]
#[sea_orm(rs_type = "i16", db_type = "SmallInteger")]
pub enum IsDisable {
    #[sea_orm(num_value = 0)]
    No,
    #[sea_orm(num_value = 1)]
    Yes,
}

#[derive(Copy, Clone, Debug, EnumIter, DeriveRelation)]
pub enum Relation {}

impl ActiveModelBehavior for ActiveModel {}
