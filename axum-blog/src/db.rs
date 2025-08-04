use sea_orm::{ConnectOptions, Database, DatabaseConnection};
use std::time::Duration;

pub async fn connect_db() -> Result<DatabaseConnection, sea_orm::DbErr> {
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
    // 建立连接并返回
    Database::connect(opt).await
}
