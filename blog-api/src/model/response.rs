use serde::Serialize;

#[derive(Debug, Serialize)]
#[serde(rename_all = "camelCase")]
pub struct UserInfoResp {
    pub id: i32,
    pub avatar: String,
    pub role_list: Vec<String>,
    pub permission_list: Vec<Option<String>>,
}
