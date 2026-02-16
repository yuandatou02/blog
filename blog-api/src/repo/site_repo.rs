use crate::entity::site_config::SiteConfig;
use crate::error::AppError;
use sqlx::PgPool;

pub struct SiteRepo {
    pool: PgPool,
}

impl SiteRepo {
    pub fn new(pool: PgPool) -> Self {
        Self { pool }
    }

    pub async fn get_one(&self) -> Result<SiteConfig, AppError> {
        sqlx::query_as!(
            SiteConfig,
            r#"select * from t_site_config where id = $1;"#,
            1
        )
        .fetch_one(&self.pool)
        .await
        .map_err(|e| AppError::Internal(e.to_string()))
    }

    pub async fn update(&self, site_config: SiteConfig) -> Result<(), AppError> {
        sqlx::query!(
            r#"
            UPDATE t_site_config 
            SET 
                user_avatar = $1,
                tourist_avatar = $2,
                site_name = $3,
                site_address = $4,
                site_intro = $5,
                site_notice = $6,
                create_site_time = $7,
                record_number = $8,
                author_avatar = $9,
                site_author = $10,
                article_cover = $11,
                about_me = $12,
                github = $13,
                gitee = $14,
                bilibili = $15,
                qq = $16,
                comment_check = $17,
                message_check = $18,
                is_reward = $19,
                wei_xin_code = $20,
                ali_code = $21,
                email_notice = $22,
                social_list = $23,
                login_list = $24,
                is_music = $25,
                music_id = $26,
                is_chat = $27,
                websocket_url = $28,
                update_time = NOW()
            WHERE id = $29
            "#,
            site_config.user_avatar,
            site_config.tourist_avatar,
            site_config.site_name,
            site_config.site_address,
            site_config.site_intro,
            site_config.site_notice,
            site_config.create_site_time,
            site_config.record_number,
            site_config.author_avatar,
            site_config.site_author,
            site_config.article_cover,
            site_config.about_me,
            site_config.github,
            site_config.gitee,
            site_config.bilibili,
            site_config.qq,
            site_config.comment_check,
            site_config.message_check,
            site_config.is_reward,
            site_config.wei_xin_code,
            site_config.ali_code,
            site_config.email_notice,
            site_config.social_list,
            site_config.login_list,
            site_config.is_music,
            site_config.music_id,
            site_config.is_chat,
            site_config.websocket_url,
            site_config.id,
        )
        .execute(&self.pool)
        .await?;

        Ok(())
    }
}
