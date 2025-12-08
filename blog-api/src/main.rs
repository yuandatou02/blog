mod database;
mod entity;
mod errors;
mod handler;
mod models;
mod repo;
mod router;
mod service;
mod utils;

use std::sync::Arc;

use axum::Router;
use sea_orm::DatabaseConnection;
use tokio::net::TcpListener;
use tower_http::trace::{DefaultMakeSpan, DefaultOnRequest, DefaultOnResponse, TraceLayer};

use crate::{repo::user_repo, router::user_router};

#[derive(Clone)]
pub struct AppState {
    pub user_repo: Arc<user_repo::UserRepo>,
}

impl AppState {
    pub fn new(db: DatabaseConnection) -> Self {
        Self {
            user_repo: user_repo::UserRepo::new(db),
        }
    }
}

#[tokio::main]
async fn main() {
    // 初始化tracing订阅器
    tracing_subscriber::fmt()
        .with_max_level(tracing::Level::INFO)
        .with_target(false)
        .with_thread_ids(false)
        .init();
    tracing::info!("正在启动axum后端服务器...");

    let db = database::connect()
        .await
        .expect("Failed to connect to database");

    let app_state = AppState::new(db);

    let app = Router::new()
        .merge(user_router())
        .layer(
            TraceLayer::new_for_http()
                .make_span_with(DefaultMakeSpan::new().level(tracing::Level::INFO))
                .on_request(DefaultOnRequest::new().level(tracing::Level::INFO))
                .on_response(DefaultOnResponse::new().level(tracing::Level::INFO)),
        )
        .with_state(app_state);

    // 监听8081端口
    let listener = TcpListener::bind("0.0.0.0:8081").await.unwrap();
    // 启动axum服务器
    tracing::info!("服务器启动成功");
    axum::serve(listener, app).await.unwrap();
}
