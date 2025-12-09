package com.common.utils;

import jakarta.servlet.http.HttpServletRequest;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BizUtils {

    public static String logVo(Object obj)  {
        if (obj == null) {
            return "logVo : obj null .......";
        }

        Class<?> clazz = obj.getClass();
        int iSize = 1;

        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("------------------------------------------\n");
        sb.append(clazz.getSimpleName());
        sb.append("\n");
        sb.append("------------------------------------------\n");

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true); // private 필드 접근 허용
            try {
                Object value = field.get(obj);

                if (field.getName().length() < 30) iSize = 30 - field.getName().length();
                sb.append(field.getName()).append(" ".repeat(iSize)).append(": [").append(value).append("]\n");

            } catch (IllegalAccessException e) {
                sb.append(field.getName()).append(": 접근 불가");
            }
        }
        return sb.toString();
    }

    public static String logVoKey(Object obj)  {
        if (obj == null) {
            return "logVo : obj null .......";
        }

        Class<?> clazz = obj.getClass();
        int iSize = 1;

        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("------------------------------------------\n");
        sb.append(clazz.getSimpleName());
        sb.append("\n");
        sb.append("------------------------------------------\n");

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true); // private 필드 접근 허용
            try {
                Object value = field.get(obj);
                if (value != null) {
                    if (field.getName().length() < 30) iSize = 30 - field.getName().length();
                    sb.append(field.getName()).append(" ".repeat(iSize)).append(": [").append(value).append("]\n");
                }

            } catch (IllegalAccessException e) {
                sb.append(field.getName()).append(": 접근 불가");
            }
        }
        return sb.toString();
    }

    // 현재날짜 시간
    public static String getDateTime(int i)  {
        LocalDateTime now = LocalDateTime.now();
        String str = "";
        switch(i) {
            case 1:
                str = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
                break;
            case 2:
                str = now.format(DateTimeFormatter.ofPattern("HHmmss"));
                break;
            case 3:
                str = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
                break;
            case 4:
                str = now.format(DateTimeFormatter.ofPattern("MMddHHmmss"));
                break;
            default:
                break;
        }
        return str;
    }


    public static String logInfo() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement caller = stackTrace[2]; // 호출한 메서드의 정보
        String fullClassName  = caller.getClassName();   // 클래스 이름
        String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);  // 클래스 이름
        String methodName = caller.getMethodName(); // 메서드 이름
        int lineNumber = caller.getLineNumber();
        return String.format("[03%d] ✅●●●●● %s", lineNumber, methodName);
    }

    public static String logInfo(String str) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement caller = stackTrace[2]; // 호출한 메서드의 정보

        String fullClassName  = caller.getClassName();   // 클래스 이름
        String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);  // 클래스 이름
        String methodName = caller.getMethodName(); // 메서드 이름
        int lineNumber = caller.getLineNumber();
        return String.format("[%03d] ✅●●●●● %s : %s", lineNumber, methodName, str);
    }

    public static String logInfo(String ke,  String va) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement caller = stackTrace[2]; // 호출한 메서드의 정보

        String fullClassName  = caller.getClassName();   // 클래스 이름
        String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);  // 클래스 이름
        String methodName = caller.getMethodName(); // 메서드 이름
        int lineNumber = caller.getLineNumber();
        return String.format("[%03d] ✅●●●●● %s : %s = [%s]", lineNumber, methodName, ke, va);
    }

    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) { return ip.split(",")[0].trim(); }

        ip = request.getHeader("Proxy-Client-IP");
        if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) { return ip; }

        ip = request.getHeader("WL-Proxy-Client-IP");
        if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) { return ip; }

        ip = request.getHeader("HTTP_CLIENT_IP");
        if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) { return ip; }

        ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) { return ip; }

        return request.getRemoteAddr(); // 마지막 fallback
    }


}
