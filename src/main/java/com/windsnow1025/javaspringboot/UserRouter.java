package com.windsnow1025.javaspringboot;

import com.windsnow1025.javaspringboot.db.JDBCHelper;
import com.windsnow1025.javaspringboot.db.UserDAO;
import com.windsnow1025.javaspringboot.model.User;
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
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody Map<String, String> request) {
        String phoneNumber = request.get("phoneNumber");
        String password = request.get("password");

        try {
            User user = userDAO.signin(phoneNumber, password);
            if (user != null) {
                return ResponseEntity.ok(Map.of("status", "Success", "message", "Login successful"));
            } else {
                return ResponseEntity.badRequest().body(Map.of("status", "Failure", "message", "Invalid credentials"));
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("status", "Error", "message", e.getMessage()));
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<Map<String, Object>> signupUser(@RequestBody Map<String, String> request) {
        String phoneNumber = request.get("phoneNumber");
        String password = request.get("password");
        String sex = request.get("sex");
        String birthday = request.get("birthday");

        try {
            boolean isSignedUp = userDAO.signup(phoneNumber, password, sex, birthday);
            if (isSignedUp) {
                return ResponseEntity.ok(Map.of("status", "Success", "message", "Signup successful"));
            } else {
                return ResponseEntity.badRequest().body(Map.of("status", "Failure", "message", "Signup failed"));
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("status", "Error", "message", e.getMessage()));
        }
    }
}
