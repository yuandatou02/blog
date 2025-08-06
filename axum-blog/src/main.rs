use std::net::{IpAddr, Ipv4Addr, SocketAddr};

use axum::Router;
use db::init_db;
use dotenvy::dotenv;
use tokio::net::TcpListener;

use crate::controller::account;

pub mod controller;
pub mod db;
pub mod entity;
pub mod model;
pub mod utils;
pub mod test;

#[tokio::main]
async fn main() {
    // 加载 .env 文件，失败时（文件不存在）忽略
    dotenv().ok();
    // 2. 初始化日志（读取 RUST_LOG）
    tracing_subscriber::fmt()
        .with_env_filter(
            tracing_subscriber::EnvFilter::try_from_default_env().unwrap_or_else(|_| "info".into()),
        )
        .init();
    // 初始化数据库
    init_db().await;

    // 3. 路由
    let app = Router::new().merge(account::auth_routes());
    // 4. 启动
    let socket = SocketAddr::new(IpAddr::V4(Ipv4Addr::new(127, 0, 0, 1)), 8080);
    let listener = TcpListener::bind(socket).await.expect("监听端口失败");
    // 2. 使用 axum::serve
    axum::serve(listener, app).await.expect("启动服务失败!");
}
