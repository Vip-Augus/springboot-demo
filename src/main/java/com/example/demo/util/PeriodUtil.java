package com.example.demo.util;

import java.text.SimpleDateFormat;

/**
 * 日期工具
 * Author JingQ on 2017/12/26.
 */
public class PeriodUtil {

    private static ThreadLocal<SimpleDateFormat> t1 = new ThreadLocal<SimpleDateFormat>();

    /**
     * 日期格式化
     * @param datePattern   参数
     * @return              线程安全
     */
    public static SimpleDateFormat getSimpleDateFormat(String datePattern) {
        SimpleDateFormat sdf = t1.get();
        if (sdf == null) {
            sdf = new SimpleDateFormat(datePattern);
            t1.set(sdf);
        }
        return sdf;
    }
}
