package com.example.demo.util;

import com.google.common.collect.Lists;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 日期工具
 * Author JingQ on 2017/12/26.
 */
public class PeriodUtil {

    private static final String REGEX_CLASS_TIME_PATTERN = "[0-9]{2}:[0-9]{2}";

    private static final Pattern CLASS_TIME_PATTERN = Pattern.compile(REGEX_CLASS_TIME_PATTERN);

    /**
     * 锁对象
     */
    private static final Object LOCK_OBJ = new Object();

    /**
     * 存放不同的日期模板格式的sdf的Map
     */
    private static Map<String, ThreadLocal<SimpleDateFormat>> sdfMap = new HashMap<String, ThreadLocal<SimpleDateFormat>>();


    /**
     * 返回一个ThreadLocal的sdf,每个线程只会new一次sdf
     *
     * @param pattern
     * @return
     */
    public static SimpleDateFormat getSdf(final String pattern) {
        ThreadLocal<SimpleDateFormat> tl = sdfMap.get(pattern);

        // 此处的双重判断和同步是为了防止sdfMap这个单例被多次put重复的sdf
        if (tl == null) {
            synchronized (LOCK_OBJ) {
                tl = sdfMap.get(pattern);
                if (tl == null) {
                    // 只有Map中还没有这个pattern的sdf才会生成新的sdf并放入map
                    // 这里是关键,使用ThreadLocal<SimpleDateFormat>替代原来直接new SimpleDateFormat
                    tl = new ThreadLocal<SimpleDateFormat>() {
                        @Override
                        protected SimpleDateFormat initialValue() {
                            return new SimpleDateFormat(pattern);
                        }
                    };
                    sdfMap.put(pattern, tl);
                }
            }
        }
        return tl.get();
    }

    /**
     * 是用ThreadLocal<SimpleDateFormat>来获取SimpleDateFormat,这样每个线程只会有一个SimpleDateFormat
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Date date, String pattern) {
        return getSdf(pattern).format(date);
    }

    /**
     * 使用System.currentTime()
     * @param time
     * @param pattern
     * @return
     */
    public static String format(long time, String pattern) {
        return getSdf(pattern).format(time);
    }

    /**
     * 判断上课时间格式
     *
     * @param pattern 上课时间
     * @return 格式是否正确
     */
    public static boolean checkClassTimeFormat(String pattern) {
        Matcher matcher = CLASS_TIME_PATTERN.matcher(pattern);
        return matcher.matches();
    }

    public static List<Integer> getSectionClass(int classBegin, int classEnd) {
        List<Integer> result = Lists.newArrayList();
        for (int i = classBegin; i <= classEnd; i++) {
            result.add(i);
        }
        return result;
    }
}
