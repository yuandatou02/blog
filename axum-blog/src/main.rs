use dotenvy::dotenv;
use tracing::info;

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
    // 连接数据库
    connect_db();
    //
    println!("hello world");
}

fn connect_db() {
    let connect_url = std::env::var("DATABASE_URL").ok();
    info!("connect_url is {}", connect_url.unwrap());
}
