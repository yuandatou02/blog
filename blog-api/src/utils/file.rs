use chrono::{Datelike, Utc};
use std::path::PathBuf;
use uuid::Uuid;
/// 生成两个路径：磁盘绝对路径 和 URL 相对路径
pub fn generate_storage_paths(ext: &str) -> (PathBuf, String) {
    let upload_root = PathBuf::from( "./uploads");
    let now = Utc::now();

    // 相对路径: 2024/02/15/uuid.ext
    let rel_dir = format!("{:04}/{:02}/{:02}", now.year(), now.month(), now.day());
    let file_name = format!("{}.{}", Uuid::new_v4(), ext);

    // 磁盘路径: ./uploads/2024/02/15/uuid.ext
    let disk_path = upload_root.join(&rel_dir).join(&file_name);

    // URL 路径: /files/2024/02/15/uuid.ext （供前端访问）
    let url_path = format!("/files/{}/{}", rel_dir, file_name);

    (disk_path, url_path)
}
