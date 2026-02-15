use std::path::Path;
use crate::entity::site_config::SiteConfig;
use crate::error::AppError;
use crate::repo::site_repo::SiteRepo;
use crate::utils::file::generate_storage_paths;
use axum::extract::Multipart;
use std::sync::Arc;

pub struct SiteService {
    site_repo: Arc<SiteRepo>,
}

impl SiteService {
    pub fn new(pool: sqlx::PgPool) -> Self {
        SiteService {
            site_repo: Arc::new(SiteRepo::new(pool)),
        }
    }

    pub async fn get_site(&self) -> Result<SiteConfig, AppError> {
        self.site_repo.get_one().await
    }

    pub async fn upload_site_img(&self, mut multipart: Multipart) -> Result<String, AppError> {
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

        Err(AppError::Internal("请求中未找到名为 'file' 的文件字段".to_string()))
    }
}
