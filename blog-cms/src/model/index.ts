/**
 * 结果返回接口
 */
export interface Result<T> {
    /**
     * 返回状态
     */
    flag: boolean;
    /**
     * 状态码
     */
    code: number;
    /**
     * 返回信息
     */
    msg: string;
    /**
     * 返回数据
     */
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

/**
 * 上传图片
 */
export interface Picture {
    /**
     * 链接
     */
    url: string;
}