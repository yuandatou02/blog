use std::time::Duration;

use sea_orm::{ConnectOptions, Database, DatabaseConnection, DbErr};

/// 建立数据库连接池
///
/// 该函数用于创建并初始化数据库连接池，配置连接参数并返回连接池句柄。
///
/// # 返回值
///
/// * `Ok(DatabaseConnection)` - 成功建立的数据库连接池句柄
/// * `Err(DbErr)` - 数据库连接或配置过程中发生的错误
///
/// # 环境变量依赖
///
/// 需要设置 `DATABASE_URL` 环境变量来指定数据库连接地址
pub async fn connect() -> Result<DatabaseConnection, DbErr> {
    dotenvy::dotenv().ok();
    let database_url = std::env::var("DATABASE_URL").expect("DATABASE_URL must be set");

    tracing::info!("Connecting to database: {}", database_url);

    // 设置数据库连接参数
    let mut opt = ConnectOptions::new(database_url);
    opt.max_connections(20)
        .min_connections(5)
        .sqlx_logging(true)
        .sqlx_logging_level(log::LevelFilter::Info)
        .connect_timeout(Duration::from_secs(8))
        .acquire_timeout(Duration::from_secs(8))
        .idle_timeout(Duration::from_secs(8))
        .max_lifetime(Duration::from_secs(8));
    tracing::info!("Connecting to database...");
    Database::connect(opt).await
}
