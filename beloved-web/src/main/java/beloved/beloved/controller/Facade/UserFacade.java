package beloved.beloved.controller.Facade;

import beloved.beloved.dto.*;
import beloved.beloved.service.impl.JWTUtil;
import beloved.beloved.service.impl.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class UserFacade {

    private final UserService userService;
    private final JWTUtil jwtUtil;

    public UserFacade(UserService userService, JWTUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    public ResponseEntity<String> register(RegisterDto dto) {
        return userService.registerUser(dto);
    }

    public ResponseEntity<AuthResponse> login(LoginDto dto) {
        return userService.loginUser(dto);
    }

    public ResponseEntity<String> logout(String authorizationHeader) {
        String token = jwtUtil.extractToken(authorizationHeader);
        userService.logout(token);
        return ResponseEntity.ok("Logged out successfully");
    }

    public ResponseEntity<String> deleteAccount(String authorizationHeader) {
        String token = jwtUtil.extractToken(authorizationHeader);
        return userService.deleteAccount(token);
    }

    public ResponseEntity<String> forgotPassword(String email) {
        String result = userService.forgotPassword(email);
        if (result == null) {
            return ResponseEntity.badRequest().body("User not found: " + email);
        }
        return ResponseEntity.ok(result);
    }

    public ResponseEntity<String> setPassword(String email, String newPassword) {
        return ResponseEntity.ok(userService.setPassword(email, newPassword));
    }

    public ResponseEntity<String> updateUserInfo(UserUpdateRequest request, String username) {
        userService.updateUserInfo(request, username);
        return ResponseEntity.ok("User info updated");
    }

    public UserProfileDto getUserProfile(String username) {
        return userService.getUserProfile(username);
    }

    public ResponseEntity<String> changePassword(PasswordChangeRequest request, String username) {
        userService.changePassword(request, username);
        return ResponseEntity.ok("Password changed successfully");
    }
}
