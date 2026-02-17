use serde::Serialize;

#[derive(Serialize)]
pub struct R<T> {
    pub flag: bool,
    pub data: Option<T>,
    pub message: String,
    pub code: i32,
}

impl<T> R<T> {
    pub fn ok(data: T, message: &str) -> R<T> {
        R {
            flag: true,
            data: Some(data),
            message: message.to_string(),
            code: 200,
        }
    }

    pub fn ok_message(message: &str) -> R<T> {
        R {
            flag: true,
            data: None,
            message: message.to_string(),
            code: 200,
        }
    }
}

#[derive(Serialize)]
#[serde(rename_all = "camelCase")]
pub struct PageResult<T> {
    pub count: usize,
    pub record_list: Vec<T>,
}
