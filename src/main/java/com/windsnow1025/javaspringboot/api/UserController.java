package com.windsnow1025.javaspringboot.api;

import com.windsnow1025.javaspringboot.db.JDBCHelper;
import com.windsnow1025.javaspringboot.db.UserDAO;
import com.windsnow1025.javaspringboot.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserDAO userDAO;

    public UserController() {
        JDBCHelper jdbcHelper = new JDBCHelper();
        this.userDAO = new UserDAO(jdbcHelper);
    }

    @GetMapping("/info")
    public ResponseEntity<User> getUser(@RequestParam String phoneNumber) {
        try {
            User user = userDAO.selectUser(phoneNumber);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<User> loginUser(@RequestBody Map<String, String> request) {
        String phoneNumber = request.get("phoneNumber");
        String password = request.get("password");

        try {
            User user = userDAO.signin(phoneNumber, password);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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

    @PostMapping("/exist")
    public ResponseEntity<Map<String, Object>> isExistUser(@RequestBody Map<String, String> request) {
        String phoneNumber = request.get("phoneNumber");

        try {
            boolean isExist = userDAO.isExist(phoneNumber);
            if (isExist) {
                return ResponseEntity.ok(Map.of("status", "Success", "message", "User exist"));
            } else {
                return ResponseEntity.ok(Map.of("status", "Failure", "message", "User not exist"));
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("status", "Error", "message", e.getMessage()));
        }
    }

    // Not safe, need verification code or original password
    @PutMapping("/password")
    public ResponseEntity<Map<String, Object>> updatePassword(@RequestBody Map<String, String> request) {
        String phoneNumber = request.get("phoneNumber");
        String password = request.get("password");

        try {
            boolean isUpdated = userDAO.updatePassword(phoneNumber, password);
            if (isUpdated) {
                return ResponseEntity.ok(Map.of("status", "Success", "message", "Password updated"));
            } else {
                return ResponseEntity.badRequest().body(Map.of("status", "Failure", "message", "Password not updated"));
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("status", "Error", "message", e.getMessage()));
        }
    }

    @PutMapping("/username")
    public ResponseEntity<Map<String, Object>> updateUsername(@RequestBody Map<String, String> request) {
        String phoneNumber = request.get("phoneNumber");
        String username = request.get("username");

        try {
            boolean isUpdated = userDAO.updateUsername(phoneNumber, username);
            if (isUpdated) {
                return ResponseEntity.ok(Map.of("status", "Success", "message", "Username updated"));
            } else {
                return ResponseEntity.badRequest().body(Map.of("status", "Failure", "message", "Username not updated"));
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("status", "Error", "message", e.getMessage()));
        }
    }

    @PutMapping("/birthday")
    public ResponseEntity<Map<String, Object>> updateBirthday(@RequestBody Map<String, String> request) {
        String phoneNumber = request.get("phoneNumber");
        String birthday = request.get("birthday");

        try {
            boolean isUpdated = userDAO.updateBirthday(phoneNumber, birthday);
            if (isUpdated) {
                return ResponseEntity.ok(Map.of("status", "Success", "message", "Birthday updated"));
            } else {
                return ResponseEntity.badRequest().body(Map.of("status", "Failure", "message", "Birthday not updated"));
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("status", "Error", "message", e.getMessage()));
        }
    }

}
