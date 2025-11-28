use std::sync::Arc;

use salvo::{Listener, Router, Server, affix_state, conn::TcpListener};
use sea_orm::{Database, DatabaseConnection};

use crate::controller::user_controller::login;
mod controller;
mod entity;
mod model;

#[derive(Clone)] // 必须 Clone
pub struct AppState {
    pub conn: DatabaseConnection,
}

#[tokio::main]
async fn main() -> Result<(), Box<dyn std::error::Error>> {
    // 初始化日志
    tracing_subscriber::fmt().init();
    // 读取配置文件来链接pgsql
    let db = Database::connect(dotenv::var("DATABASE_URL")?).await?;
    let state = Arc::new(AppState { conn: db });
    // 绑定服务端口3000
    let acceptor = TcpListener::new("0.0.0.0:3000").bind().await;
    // 创建路由
    let router = Router::new()
        .hoop(affix_state::inject(state))
        .push(Router::with_path("/login").post(login));
    // 开始监听服务
    Server::new(acceptor).serve(router).await;
    Ok(())
}
