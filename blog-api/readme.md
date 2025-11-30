// 项目结构
src/
├── main.rs
├── routes/ // 路由层
│ ├── mod.rs
│ ├── user.rs
│ └── post.rs
├── handlers/ // 控制器层
│ ├── mod.rs
│ ├── user_handler.rs
│ └── post_handler.rs
├── services/ // 业务逻辑层
│ ├── mod.rs
│ ├── user_service.rs
│ └── post_service.rs
├── models/ // 数据模型层
│ ├── mod.rs
│ ├── user.rs
│ └── post.rs
├── repositories/ // 数据访问层
│ ├── mod.rs
│ ├── user_repo.rs
│ └── post_repo.rs
└── database.rs // 数据库连接
