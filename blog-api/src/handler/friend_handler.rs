use crate::AppState;
use crate::entity::friend::Friend;
use crate::error::AppError;
use crate::model::app_result::{PageResult, R};
use crate::model::request::{FriendRequest, PageRequest};
use crate::utils::jwt::verify_token;
use axum::Json;
use axum::extract::{Query, State};
use axum_extra::TypedHeader;
use axum_extra::headers::Authorization;
use axum_extra::headers::authorization::Bearer;

pub async fn get_list_friends(
    State(state): State<AppState>,
    Query(page_request): Query<PageRequest>,
    TypedHeader(auth): TypedHeader<Authorization<Bearer>>,
) -> Result<Json<R<PageResult<Friend>>>, AppError> {
    verify_token(auth.token()).await?;
    let list = state.friend_service.get_friends(page_request).await?;
    let result = PageResult {
        count: list.len(),
        record_list: list,
    };
    Ok(Json(R::ok(result, "获取好友列表成功")))
}

pub async fn add_friend(
    State(state): State<AppState>,
    TypedHeader(auth): TypedHeader<Authorization<Bearer>>,
    Json(friend): Json<FriendRequest>,
) -> Result<Json<R<String>>, AppError> {
    verify_token(auth.token()).await?;
    state.friend_service.add_friend(friend).await?;
    Ok(Json(R::ok_message("添加好友成功")))
}

pub async fn delete_friend(
    State(state): State<AppState>,
    TypedHeader(auth): TypedHeader<Authorization<Bearer>>,
    Json(ids): Json<Vec<i32>>,
) -> Result<Json<R<String>>, AppError> {
    verify_token(auth.token()).await?;
    state.friend_service.delete_friend(ids).await?;
    Ok(Json(R::ok_message("删除好友成功")))
}

pub async fn update_friend(
    State(state): State<AppState>,
    TypedHeader(auth): TypedHeader<Authorization<Bearer>>,
    Json(friend): Json<Friend>,
) -> Result<Json<R<String>>, AppError> {
    verify_token(auth.token()).await?;
    state.friend_service.update_friend(friend).await?;
    Ok(Json(R::ok_message("更新好友成功")))
}
