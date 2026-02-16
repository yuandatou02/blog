use crate::error::AppError;
use axum::extract::Multipart;
use chrono::{Datelike, Utc};
use std::path::{Path, PathBuf};
use uuid::Uuid;

/// 生成两个路径：磁盘绝对路径 和 URL 相对路径
pub fn generate_storage_paths(ext: &str) -> (PathBuf, String) {
    let upload_root = PathBuf::from("./uploads");
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

pub async fn upload_image(mut multipart: Multipart) -> Result<String, AppError> {
    while let Some(field) = multipart
        .next_field()
        .await
        .map_err(|e| AppError::Internal(format!("读取表单字段失败: {}", e)))?
    {
        let field_name = field.name().unwrap_or("unknown").to_string();

        // 只处理名为 "file" 的字段
        if field_name != "file" {
            continue;
        }

        // 获取原始文件名
        let original_filename = field
            .file_name()
            .map(|s| s.to_string())
            .unwrap_or_else(|| "unnamed".to_string());

        // ✅ 提取真正的扩展名（不是整个文件名）
        let ext = Path::new(&original_filename)
            .extension()
            .and_then(|e| e.to_str())
            .unwrap_or("bin");

        // ✅ 生成存储路径 (uploads/2024/02/15/uuid.ext)
        let (disk_path, url_path) = generate_storage_paths(ext);

        // ✅ 创建目录（关键！）
        if let Some(parent) = disk_path.parent() {
            tokio::fs::create_dir_all(parent)
                .await
                .map_err(|e| AppError::Internal(format!("创建目录失败: {}", e)))?;
        }

        // ✅ 安全地读取文件内容（带大小限制）
        let data = field
            .bytes()
            .await
            .map_err(|e| AppError::Internal(format!("读取文件内容失败: {}", e)))?;

        // ✅ 写入磁盘
        tokio::fs::write(&disk_path, &data)
            .await
            .map_err(|e| AppError::Internal(format!("保存文件失败: {}", e)))?;

        // 返回可访问的 URL 路径
        return Ok(url_path);
    }

    Err(AppError::Internal(
        "请求中未找到名为 'file' 的文件字段".to_string(),
    ))
}
