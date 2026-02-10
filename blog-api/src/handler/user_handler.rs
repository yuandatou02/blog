use axum::extract::State;
use axum::Json;
use crate::AppState;
use crate::entity::user::User;
use crate::model::request::LoginRequest;

pub async  fn login(State(state):State<AppState>, Json(login_request):Json<LoginRequest>) -> Json<User>{
    let user = state.user_service.get_user(login_request.username.as_str()).await.unwrap().unwrap();
    Json(user)
}