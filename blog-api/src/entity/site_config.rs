use chrono::NaiveDateTime;
use serde::{Deserialize, Serialize};

/// 网站配置表
#[derive(Debug, Clone, Serialize, Deserialize)]
#[serde(rename_all = "camelCase")]
pub struct SiteConfig {
    /// 主键
    pub id: i32,

    /// 用户头像
    pub user_avatar: String,

    /// 游客头像
    pub tourist_avatar: String,

    /// 网站名称
    pub site_name: String,

    /// 网站地址
    pub site_address: String,

    /// 网站简介
    pub site_intro: String,

    /// 网站公告
    pub site_notice: String,

    /// 建站日期
    pub create_site_time: String,

    /// 备案号
    pub record_number: String,

    /// 作者头像
    pub author_avatar: String,

    /// 网站作者
    pub site_author: String,

    /// 文章默认封面
    pub article_cover: String,

    /// 关于我
    pub about_me: Option<String>,

    /// Github
    pub github: Option<String>,

    /// Gitee
    pub gitee: Option<String>,

    /// 哔哩哔哩
    pub bilibili: Option<String>,

    /// QQ
    pub qq: Option<String>,

    /// 是否评论审核 (0否 1是)
    pub comment_check: i16,

    /// 是否留言审核 (0否 1是)
    pub message_check: i16,

    /// 是否开启打赏 (0否 1是)
    pub is_reward: i16,

    /// 微信二维码
    pub wei_xin_code: Option<String>,

    /// 支付宝二维码
    pub ali_code: Option<String>,

    /// 是否邮箱通知 (0否 1是)
    pub email_notice: i16,

    /// 社交列表
    pub social_list: String,

    /// 登录方式
    pub login_list: String,

    /// 是否开启音乐播放器 (0否 1是)
    pub is_music: i16,

    /// 网易云歌单id
    pub music_id: Option<String>,

    /// 是否开启聊天室 (0否 1是)
    pub is_chat: i16,

    /// websocket链接
    pub websocket_url: Option<String>,

    /// 创建时间
    pub create_time: NaiveDateTime,

    /// 更新时间
    pub update_time: Option<NaiveDateTime>,
}
