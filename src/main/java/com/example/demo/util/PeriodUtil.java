package com.example.demo.util;

import com.google.common.collect.Lists;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 日期工具
 * Author JingQ on 2017/12/26.
 */
public class PeriodUtil {

    private static final String REGEX_CLASS_TIME_PATTERN = "[0-9]{2}:[0-9]{2}";
    private static final Pattern CLASS_TIME_PATTERN = Pattern.compile(REGEX_CLASS_TIME_PATTERN);

    private static ThreadLocal<SimpleDateFormat> SDFHOLD = new ThreadLocal<SimpleDateFormat>();

    /**
     * 日期格式化
     * @param datePattern   参数
     * @return              线程安全
     */
    public static SimpleDateFormat getSimpleDateFormat(String datePattern) {
        SimpleDateFormat sdf = SDFHOLD.get();
        if (sdf == null) {
            sdf = new SimpleDateFormat(datePattern);
            SDFHOLD.set(sdf);
        }
        return sdf;
    }

    /**
     * 判断上课时间格式
     * @param pattern     上课时间
     * @return            格式是否正确
     */
    public static boolean checkClassTimeFormat(String pattern) {
        Matcher matcher = CLASS_TIME_PATTERN.matcher(pattern);
        return matcher.matches();
    }

    public static List<Integer> getSectionClass(int classBegin, int classEnd) {
        List<Integer> result = Lists.newArrayList();
        for (int i = classBegin; i <= classBegin; i++) {
            result.add(i);
        }
        return result;
    }
}
