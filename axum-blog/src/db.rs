use sea_orm::{ConnectOptions, Database, DatabaseConnection};
use std::{sync::OnceLock, time::Duration};
use tracing::info;

// 全局静态
static DB: OnceLock<DatabaseConnection> = OnceLock::new();

pub async fn init_db() {
    // 获数据库连接地址
    let connect_url = std::env::var("DATABASE_URL").expect("数据库地址没有配置！");
    // 创建数据库连接池
    let mut opt = ConnectOptions::new(connect_url);
    opt.max_connections(100)
        .min_connections(5)
        .connect_timeout(Duration::from_secs(8))
        .acquire_timeout(Duration::from_secs(8))
        .idle_timeout(Duration::from_secs(8))
        .max_lifetime(Duration::from_secs(3600))
        .sqlx_logging(true)
        .sqlx_logging_level(log::LevelFilter::Info);
    // 建立连接
    let connect = Database::connect(opt).await.expect("数据库连接失败!");
    DB.set(connect).ok();
    info!("数据库初始化成功!");
}

// 随处调用，返回 &'static DatabaseConnection
pub fn get_db() -> &'static DatabaseConnection {
    DB.get().expect("数据库尚未初始化")
}
