/**
 * 定义一个通用的结果接口，用于封装操作的执行结果。
 *
 * @template T - 泛型参数，表示返回数据的具体类型。
 * @property flag - 操作是否成功的标志，true 表示成功，false 表示失败。
 * @property code - 状态码，通常用于标识具体的错误类型或操作状态。
 * @property message - 描述信息，通常用于提供更详细的错误说明或操作结果描述。
 * @property data - 返回的数据内容，类型由泛型 T 决定。
 */
export interface Result<T> {
  flag: boolean;
  code: number;
  message: string;
  data: T;
}

/**
 * 分页参数
 */
export interface PageQuery {
  /**
   * 当前页
   */
  current: number;
  /**
   * 每页大小
   */
  size: number;
}

/**
 * 分页返回接口
 */
export interface PageResult<T> {
  /**
   * 分页结果
   */
  recordList: T;
  /**
   * 总数
   */
  count: number;
}
