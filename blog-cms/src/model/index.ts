/**
 * 通用结果返回接口
 *
 * @template T - 返回数据的类型
 * @property code - 状态码，用于表示请求处理的结果状态
 * @property data - 返回的数据内容，类型由泛型T指定
 * @property message - 返回的消息描述，通常用于错误信息或成功提示
 */
export interface Result<T> {
    code: number;
    data: T;
    message: string;
}
