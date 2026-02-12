use serde::Serialize;

#[derive(Debug, Serialize)]
#[serde(rename_all = "camelCase")]
pub struct UserInfoResp {
    pub id: i32,
    pub avatar: String,
    pub role_list: Vec<String>,
    pub permission_list: Vec<Option<String>>,
}

#[derive(Debug, Serialize)]
pub struct UserMenuResp {
    pub id: i32,
    pub parent_id: i32,
    pub menu_name: String,
    pub menu_type: String,
    pub order_num: i32,
    pub path: Option<String>,
    pub icon: Option<String>,
    pub component: Option<String>,
    pub is_hidden: bool,
}
#[derive(Debug, Serialize)]
pub struct MetaResp {
    pub title: Option<String>,
    pub icon: Option<String>,
    pub hidden: Option<bool>,
}
#[derive(Debug, Serialize)]
pub struct RouterResp {
    pub name: String,
    pub path: String,
    pub component: String,
    pub always_show: Option<bool>,
    pub redirect: Option<String>,
    pub meta: Option<MetaResp>,
    pub children: Option<Vec<RouterResp>>,
}
