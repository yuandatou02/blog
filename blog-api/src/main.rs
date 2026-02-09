use std::net::SocketAddr;
use axum::Router;
use axum::routing::get;
use time::macros::format_description;
use tracing_subscriber::fmt::time::LocalTime;

#[tokio::main]
async fn main() {
    // 1. 读取.env配置文件
    dotenvy::dotenv().expect("Failed to load .env file");
    // 2. 初始化日志订阅器
    let timer = LocalTime::new(format_description!(
        "[year]年[month]月[day]日 [hour]:[minute]:[second]"
    ));
    tracing_subscriber::fmt()
        .with_timer(timer)
        .with_level(true)
        .with_target(true)
        .with_file(true)
        .with_line_number(true)
        .with_env_filter(tracing_subscriber::EnvFilter::from_default_env())
        .init();

    tracing::info!("日志服务启动成功！");

    let app = Router::new().route("/", get(|| async { "Hello, World!" }));
    let addr = SocketAddr::from(([127, 0, 0, 1], 8081));

    tracing::info!("🚀 Server starting on http://{}", addr);

    let listener = tokio::net::TcpListener::bind(addr).await.unwrap();
    axum::serve(listener, app).await.unwrap();
}
