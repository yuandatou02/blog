use serde::Deserialize;

#[derive(Deserialize)]
pub struct LoginRequest {
    pub username: String,
    pub password: String,
}

#[derive(Deserialize)]
#[serde(rename_all = "camelCase")]
pub struct PasswordReq {
    pub old_password: String,
    pub new_password: String,
}

#[derive(Deserialize)]
pub struct PageRequest {
    pub current: i64,
    pub size: i64,
    pub keyword: Option<String>,
}

#[derive(Deserialize)]
pub struct FriendRequest {
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
}
