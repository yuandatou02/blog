use crate::AppState;
use crate::error::AppError;
use crate::model::app_result::R;
use crate::model::request::{LoginRequest, PasswordReq};
use crate::model::response::{RouterResp, UserInfoResp};
use crate::utils::jwt::verify_token;
use axum::Json;
use axum::extract::State;
use axum_extra::TypedHeader;
use axum_extra::headers::Authorization;
use axum_extra::headers::authorization::Bearer;
use crate::constants::redis_constant;

pub async fn login(
    State(state): State<AppState>,
    Json(login_request): Json<LoginRequest>,
) -> Result<Json<R<String>>, AppError> {
    let token = state.user_service.login(login_request).await?;
    Ok(Json(R::ok(token, "登录成功")))
}

pub async fn logout(State(state): State<AppState>,TypedHeader(auth): TypedHeader<Authorization<Bearer>>) -> Result<Json<R<()>>, AppError> {
    let user_id = verify_token(auth.token()).await?;
    state.redis_service.del(&format!("{}:{}",user_id.to_string(),redis_constant::REDIS_USER_INFO)).await?;
    state.redis_service.del(&format!("{}:{}",user_id.to_string(),redis_constant::REDIS_USER_MENU)).await?;
    Ok(Json(R::ok_message("退出成功")))
}

pub async fn get_user_info(
    State(state): State<AppState>,
    TypedHeader(auth): TypedHeader<Authorization<Bearer>>,
) -> Result<Json<R<UserInfoResp>>, AppError> {
    let user_id = verify_token(auth.token()).await?;
    let user_info = state.redis_service.get(&format!("{}:{}",user_id.to_string(),redis_constant::REDIS_USER_INFO)).await?;
    if let  Some(value) = user_info {
        let user_info = serde_json::from_str::<UserInfoResp>(&value).map_err(|_| AppError::Internal("反序列化失败".to_string()))?;
         Ok(Json(R::ok(user_info, "获取用户信息成功")))
    }else {
        let user_info = state.user_service.get_user_info(user_id).await?;
        state.redis_service.set(&format!("{}:{}",user_id.to_string(),redis_constant::REDIS_USER_INFO), &user_info).await?;
        Ok(Json(R::ok(user_info, "获取用户信息成功")))
    }
}

pub async fn get_user_menu(
    State(state): State<AppState>,
    TypedHeader(auth): TypedHeader<Authorization<Bearer>>,
) -> Result<Json<R<Vec<RouterResp>>>, AppError> {
    let user_id = verify_token(auth.token()).await?;
    let user_menu = state.redis_service.get(&format!("{}:{}",user_id.to_string(),redis_constant::REDIS_USER_MENU)).await?;
    if let Some(value) = user_menu{
        let user_menu = serde_json::from_str::<Vec<RouterResp>>(&value).map_err(|_| AppError::Internal("反序列化失败".to_string()))?;
        Ok(Json(R::ok(user_menu, "获取用户菜单成功")))
    }else {
        let user_menu = state.user_service.get_user_menu(user_id).await?;
        state.redis_service.set(&format!("{}:{}",user_id.to_string(),redis_constant::REDIS_USER_MENU), &user_menu).await?;
        Ok(Json(R::ok(user_menu, "获取用户菜单成功")))
    }
}

pub async fn update_user_password(
    State(state): State<AppState>,
    TypedHeader(auth): TypedHeader<Authorization<Bearer>>,
    Json(password_req): Json<PasswordReq>,
) -> Result<Json<R<()>>, AppError> {
    let user_id = verify_token(auth.token()).await?;
    state
        .user_service
        .update_password(user_id, password_req)
        .await?;
    Ok(Json(R::ok_message("修改密码成功")))
}
