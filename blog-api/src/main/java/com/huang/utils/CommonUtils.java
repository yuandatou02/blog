package com.huang.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 公共工具类
 *
 * @author Ikaros
 * @since 2025/8/27 12:12 星期三
 */
public class CommonUtils {

    /**
     * 转换List
     *
     * @param obj   obj
     * @param clazz clazz
     * @return {@link List<T>}
     */
    public static <T> List<T> castList(Object obj, Class<T> clazz) {
        List<T> result = new ArrayList<T>();
        if (obj instanceof List<?>) {
            for (Object o : (List<?>) obj) {
                result.add(clazz.cast(o));
            }
            return result;
        }
        return result;
    }

}