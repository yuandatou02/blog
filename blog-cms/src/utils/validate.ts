/**
 * 判断给定路径是否为外部链接
 * @param path - 需要判断的路径字符串
 * @returns 如果路径是外部链接则返回true，否则返回false
 */
export const isExternalFunc = (path: string) => {
  // 使用正则表达式检测路径是否以http://、https://、mailto:或tel:开头
  return /^(https?:|mailto:|tel:)/.test(path);
};
