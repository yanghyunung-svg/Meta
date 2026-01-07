package com.meta.common.util;

import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;

public class SessionUtil {

    public static final String LOGIN_USER_ID = "userId";
    public static final String LOGIN_TIME = "loginTime";
    public static final String USER_ROLE = "role";

    private SessionUtil() {}

    public static void login(
            HttpSession session,
            String userId,
            String role
    ) {
        session.setAttribute(LOGIN_USER_ID, userId);
        session.setAttribute(USER_ROLE, role);
        session.setAttribute(LOGIN_TIME, LocalDateTime.now());
    }

    public static void logout(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
    }

    public static boolean isLoggedIn(HttpSession session) {
        return session != null &&
                session.getAttribute(LOGIN_USER_ID) != null;
    }

    public static String getUserId(HttpSession session) {
        return (String) session.getAttribute(LOGIN_USER_ID);
    }

    public static String getRole(HttpSession session) {
        return (String) session.getAttribute(USER_ROLE);
    }

    public static LocalDateTime getLoginTime(HttpSession session) {
        return (LocalDateTime) session.getAttribute(LOGIN_TIME);
    }
}
