use chrono::{DateTime, Utc};
use serde::{Deserialize, Serialize};
use sqlx::FromRow;

/// 用户实体
#[derive(Debug, Clone, FromRow, Serialize, Deserialize)]
pub struct User {
    /// 用户id
    pub id: i32,

    /// 用户昵称
    pub nickname: String,

    /// 用户名
    pub username: String,

    /// 用户密码（加密存储）
    #[serde(skip_serializing)] // 序列化时隐藏密码
    pub password: String,

    /// 头像URL
    pub avatar: String,

    /// 个人网站
    pub web_site: Option<String>,

    /// 个人简介
    pub intro: Option<String>,

    /// 邮箱
    pub email: Option<String>,

    /// 登录IP
    pub ip_address: Option<String>,

    /// 登录地址
    pub ip_source: Option<String>,

    /// 登录方式 (1邮箱 2QQ 3Gitee 4Github)
    pub login_type: i16,

    /// 是否禁用 (0否 1是)
    pub is_disable: i16,

    /// 登录时间
    pub login_time: Option<DateTime<Utc>>,

    /// 创建时间
    pub create_time: DateTime<Utc>,

    /// 更新时间
    pub update_time: Option<DateTime<Utc>>,
}
