package com.meta.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/session")
public class SessionController {

    @GetMapping("/expire-time")
    public Map<String, Long> getExpireTime(HttpSession session) {

        long lastAccess = session.getLastAccessedTime(); // ms
        int maxInactiveSec = session.getMaxInactiveInterval();

        long expireTime =
                lastAccess + (maxInactiveSec * 1000L);

        return Map.of("expireTime", expireTime);
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/login";
    }

}

