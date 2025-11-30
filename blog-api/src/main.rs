mod database;
mod handler;
mod repositories;

use anyhow::Context;
use axum::{Router, routing::get};
use tower_http::{
    classify::ServerErrorsFailureClass,
    trace::{DefaultMakeSpan, DefaultOnResponse, TraceLayer},
};
use tracing::Level;

use crate::database::connect;

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
        .make_span_with(
            DefaultMakeSpan::new()
                .include_headers(false)
                .level(Level::INFO),
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

    let app = Router::new()
        .route("/", get(|| async { "hello,world!" }))
        .with_state(db)
        .layer(trace_layer);

    let listener = tokio::net::TcpListener::bind("0.0.0.0:3000")
        .await
        .context("端口绑定失败")?;

    tracing::info!("🚀 服务器启动在 http://{}", listener.local_addr().unwrap());

    axum::serve(listener, app).await.context("服务启动失败")?;

    Ok(())
}
