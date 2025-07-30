#[derive(serde::Deserialize)]
pub struct LoginReq {
    pub email: String,
    pub password: String,
}


#[derive(serde::Serialize)]
pub struct LoginRes {
    pub token: String,
}