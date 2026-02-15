mod constants;
mod entity;
mod error;
mod handler;
mod model;
mod repo;
mod router;
mod service;
mod utils;

use crate::router::{site_router, user_router};
use crate::service::redis_service::RedisService;
use crate::service::site_service::SiteService;
use crate::service::user_service::UserService;
use axum::Router;
use sqlx::postgres::PgPoolOptions;
use std::net::SocketAddr;
use std::sync::Arc;
use std::time::Duration;
use time::macros::format_description;
use tower_http::services::ServeDir;
use tracing_subscriber::fmt::time::LocalTime;

#[derive(Clone)]
pub struct AppState {
    site_service: Arc<SiteService>,
    user_service: Arc<UserService>,
    redis_service: Arc<RedisService>,
}

impl AppState {
    pub fn new(pool: sqlx::PgPool, redis_url: &str) -> Self {
        Self {
            site_service: Arc::new(SiteService::new(pool.clone())),
            user_service: Arc::new(UserService::new(pool)),
            redis_service: Arc::new(RedisService::new(redis_url)),
        }
    }
}

#[tokio::main]
async fn main() {
    // 1. 读取.env配置文件
    dotenvy::dotenv().expect("Failed to load .env file");
    // 2. 初始化日志订阅器
    let timer = LocalTime::new(format_description!(
        "[year]年[month]月[day]日 [hour]:[minute]:[second]"
    ));
    tracing_subscriber::fmt()
        .compact()
        .with_timer(timer)
        .with_level(true)
        .with_target(true)
        .with_env_filter(tracing_subscriber::EnvFilter::from_default_env())
        .init();

    tracing::info!("日志服务启动成功！");

    // 连接数据库
    let database_url = std::env::var("DATABASE_URL").expect("DATABASE_URL没有设置！！");

    // 创建连接池
    let pool = PgPoolOptions::new()
        .max_connections(5) // 最大连接数
        .min_connections(2) // 最小连接数
        .acquire_timeout(Duration::from_secs(3)) // 获取连接超时
        .idle_timeout(Duration::from_secs(600)) // 空闲连接超时
        .max_lifetime(Duration::from_secs(1800)) // 连接最大生命周期
        .connect(&database_url)
        .await
        .expect("连接数据库失败！");
    tracing::info!("数据库连接成功！");

    // 创建redis连接服务
    let redis_url = std::env::var("REDIS_URL").expect("REDIS_URL没有设置！！");
    // ✅ 获取项目根目录的绝对路径
    let upload_dir = std::env::current_dir()
        .unwrap()
        .join("uploads");

    tracing::info!("上传目录: {:?}", upload_dir); // 调试用
    let app = Router::new()
        .merge(user_router())
        .merge(site_router())
        // ✅ 关键：将 /files/ 路径映射到磁盘上的 uploads 目录
        .nest_service("/files", ServeDir::new(upload_dir))
        .with_state(AppState::new(pool, redis_url.as_str()));
    let addr = SocketAddr::from(([127, 0, 0, 1], 8081));

    tracing::info!("🚀 开始监听 http://{}", addr);

    let listener = tokio::net::TcpListener::bind(addr).await.unwrap();
    axum::serve(listener, app).await.unwrap();
}
