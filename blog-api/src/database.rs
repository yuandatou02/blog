use std::time::Duration;

use anyhow::Context;
use sea_orm::{ConnectOptions, Database, DatabaseConnection};

pub async fn connect() -> anyhow::Result<DatabaseConnection> {
    // 从 .env 文件读取数据库连接字符串
    let database_url =
        dotenvy::var("DATABASE_URL").context("DATABASE_URL必须被设置在.env文件中")?;

    tracing::info!("连接到数据库: {}", database_url);

    // 配置连接选项
    let mut opt = ConnectOptions::new(database_url);

    opt.max_connections(100)
        .min_connections(5)
        .connect_timeout(Duration::from_secs(8))
        .acquire_timeout(Duration::from_secs(8))
        .idle_timeout(Duration::from_secs(8))
        .max_lifetime(Duration::from_secs(8))
        .sqlx_logging(true)
        .sqlx_logging_level(log::LevelFilter::Info);

    Database::connect(opt).await.context("数据库链接失败")
}
