use axum::Router;
use controller::login;
use dotenvy::dotenv;
use sqlx::postgres::PgPoolOptions;

pub mod controller;
pub mod entity;

#[tokio::main]
async fn main() {
    // 读取环境变量
    dotenv().ok();
    // 把 Rust 程序里的日志打印到终端
    tracing_subscriber::fmt::init();
    // 读取数据库连接地址
    let db_url = std::env::var("DATABASE_URL").expect("DATABASE_URL必须被设置!");
    // 连接数据库
    let popl = PgPoolOptions::new()
        .max_connections(5)
        .connect(&db_url)
        .await
        .expect("连接数据库失败!");
    // 挂载路由并将数据库连接作为全局状态
    let app = Router::new().nest("", login::router()).with_state(popl);
    println!("Database URL: {}", db_url);
}
