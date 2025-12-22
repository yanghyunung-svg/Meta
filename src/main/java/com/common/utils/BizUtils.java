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
        sb.append(clazz.getSimpleName());
        sb.append("\n");

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
        sb.append(clazz.getSimpleName());
        sb.append("\n");

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
        StackTraceElement caller = stackTrace[2]; 
        String fullClassName  = caller.getClassName();  
        String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1); 
        String methodName = caller.getMethodName(); 
        int lineNumber = caller.getLineNumber();
        return String.format("[03%d] ✅●●●●● %s.%s", lineNumber, className, methodName);
    }

    public static String logInfo(String str) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement caller = stackTrace[2]; 
        String fullClassName  = caller.getClassName();  
        String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1); 
        String methodName = caller.getMethodName(); 
        int lineNumber = caller.getLineNumber();
        return String.format("[%03d] ✅●●●●● %s.%s : %s", lineNumber, className, methodName, str);
    }

    public static String logInfo(String ke,  String va) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement caller = stackTrace[2]; 
        String fullClassName  = caller.getClassName();  
        String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1); 
        String methodName = caller.getMethodName(); 
        int lineNumber = caller.getLineNumber();
        return String.format("[%03d] ✅●●●●● %s.%s : %s = [%s]", lineNumber, className, methodName, ke, va);
    }

    public static String getClientIp(HttpServletRequest request) {
        String[] headerKeys = {
                "X-Forwarded-For",
                "Proxy-Client-IP",
                "WL-Proxy-Client-IP",
                "HTTP_CLIENT_IP",
                "HTTP_X_FORWARDED_FOR",
                "X-Real-IP"
        };

        for (String header : headerKeys) {
            String ip = request.getHeader(header);
            if (ip != null && ip.length() > 0 && !"unknown".equalsIgnoreCase(ip)) {
                // 다중 프록시 대응
                return ip.split(",")[0].trim();
            }
        }

        String ip = request.getRemoteAddr();

        // IPv6 localhost 처리
        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            ip = "127.0.0.1";
        }

        return ip;
    }


}
