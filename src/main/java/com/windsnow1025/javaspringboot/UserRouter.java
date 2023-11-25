package com.windsnow1025.javaspringboot;

import com.windsnow1025.javaspringboot.db.JDBCHelper;
import com.windsnow1025.javaspringboot.db.UserDAO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserRouter {

    private final UserDAO userDAO;

    public UserRouter() {
        JDBCHelper jdbcHelper = new JDBCHelper();
        this.userDAO = new UserDAO(jdbcHelper);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody Map<String, String> loginRequest) {
        String phoneNumber = loginRequest.get("phoneNumber");
        String password = loginRequest.get("password");

        try {
            UserDAO.User user = userDAO.login(phoneNumber, password);
            if (user != null) {
                return ResponseEntity.ok(Map.of("status", "Success", "message", "Login successful"));
            } else {
                return ResponseEntity.badRequest().body(Map.of("status", "Failure", "message", "Invalid credentials"));
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("status", "Error", "message", e.getMessage()));
        }
    }

    @GetMapping("/test")
    public ResponseEntity<Map<String, Object>> test() {
        return ResponseEntity.ok(Map.of("status", "Success", "message", "Test successful"));
    }
}
