use serde::{Deserialize, Serialize};

#[derive(Debug, Serialize, Deserialize)]
#[serde(rename_all = "camelCase")]
pub struct Result<T> {
    /// 返回状态
    pub flag: bool,
    /// 状态码
    pub code: i32,
    /// 返回信息
    pub msg: String,
    /// 返回数据
    #[serde(skip_serializing_if = "Option::is_none")]
    pub data: Option<T>,
}

// 常用构造器
impl<T> Result<T> {
    /// 成功-无数据
    pub fn success() -> Self {
        Self {
            flag: true,
            code: 200,
            msg: "success".into(),
            data: None,
        }
    }

    /// 成功-带数据
    pub fn success_with(data: T) -> Self {
        Self {
            flag: true,
            code: 200,
            msg: "success".into(),
            data: Some(data),
        }
    }

    /// 失败-自定义消息
    pub fn fail<M: Into<String>>(msg: M) -> Self {
        Self {
            flag: false,
            code: 500,
            msg: msg.into(),
            data: None,
        }
    }

    /// 失败-自定义码 + 消息
    pub fn fail_with<M: Into<String>>(code: i32, msg: M) -> Self {
        Self {
            flag: false,
            code,
            msg: msg.into(),
            data: None,
        }
    }
}

// 快捷类型别名，避免到处写 Result<T, E>
pub type R<T> = Result<T>;
