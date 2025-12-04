mod database;

use axum::{Router, routing::get};
use tokio::net::TcpListener;
use tower_http::trace::{DefaultMakeSpan, DefaultOnRequest, DefaultOnResponse, TraceLayer};

use crate::database::connect;

#[tokio::main]
async fn main() {
    // 初始化tracing订阅器
    tracing_subscriber::fmt()
        .with_max_level(tracing::Level::INFO)
        .with_target(false)
        .with_thread_ids(false)
        .init();
    tracing::info!("正在启动axum后端服务器...");

    // 连接数据库
    let db = connect().await.expect("数据库连接失败！");
    tracing::info!("数据库连接成功！");

    let app = Router::new()
        .route("/", get(handler))
        .layer(
            TraceLayer::new_for_http()
                .make_span_with(DefaultMakeSpan::new().level(tracing::Level::INFO))
                .on_request(DefaultOnRequest::new().level(tracing::Level::INFO))
                .on_response(DefaultOnResponse::new().level(tracing::Level::INFO)),
        )
        .with_state(db);
    
    // 监听8081端口
    let listener = TcpListener::bind("0.0.0.0:8081").await.unwrap();
    // 启动axum服务器
    tracing::info!("服务器启动成功");
    axum::serve(listener, app).await.unwrap();
}

async fn handler() -> &'static str {
    tracing::debug!("Handler called");
    "Hello, World!"
}
