use argon2::{Argon2, PasswordHash, PasswordVerifier};
use axum::{Json, Router, routing::post};
use sea_orm::{ColumnTrait, EntityTrait, QueryFilter};

use crate::{
    db::get_db,
    entity::users,
    model::{request::LoginRequest, response::LoginResponse},
    utils::{
        jwt::create_jwt_token,
        myerror::{AppError, Result},
    },
};

pub async fn login(Json(login_request): Json<LoginRequest>) -> Result<Json<LoginResponse>> {
    // 获取数据库连接
    let db = get_db().await;
    // 查找用户
    let user = users::Entity::find()
        .filter(users::Column::Username.eq(&login_request.username))
        .one(db)
        .await
        .map_err(|_| AppError::Internal("DB error".into()))?
        .ok_or(AppError::Unauthorized)?;
    // 2. 验证密码
    let hash =
        PasswordHash::new(&user.password).map_err(|_| AppError::Internal("Invalid hash".into()))?;
    Argon2::default()
        .verify_password(login_request.password.as_bytes(), &hash)
        .map_err(|_| AppError::Unauthorized)?;

    // 3. 签发令牌
    let token = create_jwt_token(user.id);
    Ok(Json(LoginResponse {
        token,
        user_id: user.id,
    }))
}

// 子路由打包
pub fn auth_routes() -> Router {
    Router::new().route("/login", post(login))
}
