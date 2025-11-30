mod database;
mod handler;
mod models;
mod repositories;
mod routes;
mod services;
mod utils;

use anyhow::Context;
use axum::{Router, body::Body};
use sea_orm::DatabaseConnection;
use tower_http::{
    classify::ServerErrorsFailureClass,
    trace::{DefaultOnResponse, TraceLayer},
};
use tracing::Level;

use crate::{
    database::connect,
    routes::user_router,
    services::user_service::UserService,
    utils::jwt::{JwtConfig, JwtUtils},
};

#[derive(Clone)]
pub struct AppState {
    pub user_service: UserService,
    pub jwt_utils: JwtUtils,
}

impl AppState {
    pub fn new(db: DatabaseConnection) -> Self {
        let jwt_config = JwtConfig {
            secret: dotenvy::var("JWT_SECRET").unwrap_or_else(|_| "651908384".to_string()),
            expiration_hours: dotenvy::var("JWT_EXPIRATION_HOURS")
                .ok()
                .and_then(|v| v.parse().ok())
                .unwrap_or(24),
        };
        Self {
            user_service: UserService::new(db.clone()),
            jwt_utils: JwtUtils::new(jwt_config),
        }
    }
}

#[tokio::main]
async fn main() -> anyhow::Result<()> {
    // 初始化日志系统
    // 设置日志级别为INFO
    // 不显示目标模块路径
    // 使用紧凑格式输出
    tracing_subscriber::fmt()
        .with_max_level(Level::INFO)
        .with_target(false)
        .compact()
        .init();
    // 初始化tracing
    let trace_layer = TraceLayer::new_for_http()
        .make_span_with(|request: &axum::http::Request<Body>| {
            // 记录请求方法、URI 和查询参数
            let method = request.method();
            let uri = request.uri();

            tracing::info_span!(
                "request",
                method = %method,
                uri = %uri,
                query = ?uri.query(),
                body = ?request.body()
            )
        })
        .on_request(
            |request: &axum::http::Request<Body>, _span: &tracing::Span| {
                // 记录请求头
                tracing::info!(
                    "请求开始: {} {}, headers: {:?}, body:{:#?}",
                    request.method(),
                    request.uri(),
                    request.headers(),
                    request.body()
                );
            },
        )
        .on_response(
            DefaultOnResponse::new()
                .include_headers(false)
                .level(Level::INFO),
        )
        .on_failure(
            |error: ServerErrorsFailureClass, _latency, _span: &tracing::Span| {
                tracing::error!("请求失败:{:?}", error)
            },
        );
    // 连接数据库
    let db = connect().await?;
    let state = AppState::new(db);

    let app = Router::new()
        .merge(user_router::public_router())
        .with_state(state)
        .layer(trace_layer);

    let listener = tokio::net::TcpListener::bind("0.0.0.0:3000")
        .await
        .context("端口绑定失败")?;

    tracing::info!("🚀 服务器启动在 http://{}", listener.local_addr().unwrap());

    axum::serve(listener, app).await.context("服务启动失败")?;

    Ok(())
}
