use salvo::{Listener, Server, conn::TcpListener};
mod entity;
#[tokio::main]
async fn main() {
    // 初始化日志
    tracing_subscriber::fmt().init();
    // 绑定服务端口3000
    let acceptor = TcpListener::new("0.0.0.0:3000").bind().await;
    // 开始监听服务
    Server::new(acceptor).serve().await;
}
