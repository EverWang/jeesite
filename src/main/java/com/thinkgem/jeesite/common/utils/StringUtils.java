/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.thinkgem.jeesite.common.utils;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.LocaleResolver;

/**
 * 字符串工具类, 继承org.apache.commons.lang3.StringUtils类
 * 
 * @author ThinkGem
 * @version 2013-05-22
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils
{

    // 替换固定格式的字符串（支持正则表达式）
    public static String replaceAll(String str, String regex, String replacement)
    {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (m.find())
        {
            m.appendReplacement(sb, replacement);
        }
        m.appendTail(sb);
        return sb.toString();
    }

    // 是否为数字（整数或小数）
    public static boolean isNumber(String str)
    {
        return NumberUtils.isNumber(str);
    }

    // 是否为十进制数（整数）
    public static boolean isDigits(String str)
    {
        return NumberUtils.isDigits(str);
    }

    // 将驼峰风格替换为下划线风格
    public static String toUnderline(String str)
    {
        Matcher matcher = Pattern.compile("[A-Z]").matcher(str);
        StringBuilder builder = new StringBuilder(str);
        for (int i = 0; matcher.find(); i++)
        {
            builder.replace(matcher.start() + i, matcher.end() + i, "_" + matcher.group().toLowerCase());
        }
        if (builder.charAt(0) == '_')
        {
            builder.deleteCharAt(0);
        }
        return builder.toString();
    }

    // 将下划线风格替换为驼峰风格
    public static String toCamelhump(String str)
    {
        Matcher matcher = Pattern.compile("_[a-z]").matcher(str);
        StringBuilder builder = new StringBuilder(str);
        for (int i = 0; matcher.find(); i++)
        {
            builder.replace(matcher.start() - i, matcher.end() - i, matcher.group().substring(1).toUpperCase());
        }
        if (Character.isUpperCase(builder.charAt(0)))
        {
            builder.replace(0, 1, String.valueOf(Character.toLowerCase(builder.charAt(0))));
        }
        return builder.toString();
    }

    // 分割固定格式的字符串
    public static String[] splitString(String str, String separator)
    {
        return StringUtils.splitByWholeSeparator(str, separator);
    }

    // 将字符串首字母大写
    public static String firstToUpper(String str)
    {
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    // 将字符串首字母小写
    public static String firstToLower(String str)
    {
        return Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }

    /**
     * 替换掉HTML标签方法
     */
    public static String replaceHtml(String html)
    {
        if (isBlank(html))
        {
            return "";
        }
        String regEx = "<.+?>";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(html);
        String s = m.replaceAll("");
        return s;
    }

    /**
     * 缩略字符串（不区分中英文字符）
     * 
     * @param str
     *            目标字符串
     * @param length
     *            截取长度
     * @return
     */
    public static String abbr(String str, int length)
    {
        if (str == null)
        {
            return "";
        }
        try
        {
            StringBuilder sb = new StringBuilder();
            int currentLength = 0;
            for (char c : str.toCharArray())
            {
                currentLength += String.valueOf(c).getBytes("GBK").length;
                if (currentLength <= length - 3)
                {
                    sb.append(c);
                }
                else
                {
                    sb.append("...");
                    break;
                }
            }
            return sb.toString();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 转换为Double类型
     */
    public static Double toDouble(Object val)
    {
        if (val == null)
        {
            return 0D;
        }
        try
        {
            return Double.valueOf(trim(val.toString()));
        }
        catch (Exception e)
        {
            return 0D;
        }
    }

    /**
     * 转换为Float类型
     */
    public static Float toFloat(Object val)
    {
        return toDouble(val).floatValue();
    }

    /**
     * 转换为Long类型
     */
    public static Long toLong(Object val)
    {
        return toDouble(val).longValue();
    }

    /**
     * 转换为Integer类型
     */
    public static Integer toInteger(Object val)
    {
        return toLong(val).intValue();
    }

    /**
     * 获得i18n字符串
     */
    public static String getMessage(String code, Object[] args)
    {
        LocaleResolver localLocaleResolver = (LocaleResolver) SpringContextHolder.getBean(LocaleResolver.class);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        Locale localLocale = localLocaleResolver.resolveLocale(request);
        return SpringContextHolder.getApplicationContext().getMessage(code, args, localLocale);
    }

    /**
     * 获得用户远程地址
     */
    public static String getRemoteAddr(HttpServletRequest request)
    {
        String remoteAddr = request.getHeader("X-Real-IP");
        if (isNotBlank(remoteAddr))
        {
            remoteAddr = request.getHeader("X-Forwarded-For");
        }
        else if (isNotBlank(remoteAddr))
        {
            remoteAddr = request.getHeader("Proxy-Client-IP");
        }
        else if (isNotBlank(remoteAddr))
        {
            remoteAddr = request.getHeader("WL-Proxy-Client-IP");
        }
        return remoteAddr != null ? remoteAddr : request.getRemoteAddr();
    }

}
