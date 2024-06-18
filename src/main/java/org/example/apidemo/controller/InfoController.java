package org.example.apidemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
public class InfoController {

    @GetMapping("/info")
    public Map<String, String> getInfo(HttpServletRequest request) throws UnknownHostException {
        Map<String, String> response = new HashMap<>();

        // Get server IP address
        String serverIp = InetAddress.getLocalHost().getHostAddress();
        response.put("serverIp_action", serverIp);

        // Get current time
        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        response.put("currentTime_action", currentTime);

        return response;
    }
}