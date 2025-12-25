package beloved.beloved.controller;

import beloved.beloved.dto.AuthResponse;
import beloved.beloved.dto.LoginDto;
import beloved.beloved.dto.PasswordChangeRequest;
import beloved.beloved.dto.RegisterDto;
import beloved.beloved.dto.UserProfileDto;
import beloved.beloved.dto.UserUpdateRequest;
import beloved.beloved.service.impl.JWTUtil;
import beloved.beloved.service.impl.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;
    private final JWTUtil jwtUtil;

    public UserController(UserService userService, JWTUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterDto registerDto) {
        return userService.registerUser(registerDto);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@RequestBody LoginDto loginDto) {
        return userService.loginUser(loginDto);
    }

   /* @PostMapping("/adminuser/{email}")
    public ResponseEntity<String> assignAdminRole(@PathVariable String email) {
        return userService.assignAdminRole(email);
    }*/

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authorizationHeader) {
        String token = jwtUtil.extractToken(authorizationHeader);
        userService.logout(token);
        return ResponseEntity.ok("Logged out successfully");
    }
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteAccount(@RequestHeader("Authorization") String authorizationHeader) {
        String token = jwtUtil.extractToken(authorizationHeader);
        return userService.deleteAccount(token);
    }

     @PutMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        String result = userService.forgotPassword(email);

        if (result == null) {
            return new ResponseEntity<>("User not found: " + email, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @PutMapping("/set-password")
    public ResponseEntity<String> setPassword(@RequestParam String email, @RequestBody String newPassword) {
        return new ResponseEntity<>(userService.setPassword(email, newPassword), HttpStatus.OK);
    }

   /* @GetMapping("/me")
    public AdminDto getAdminDetails(HttpServletRequest request) {
        String token = extractToken(request);
        return userService.getAdminDetailsByToken(token);
    }

    @PutMapping("/update")
    public String updateAdmin(@RequestBody AdminDto adminDto, HttpServletRequest request) {
        String token = extractToken(request);
        userService.updateAdminInfo(token, adminDto);
        return "Admin info updated successfully.";
    }
*/

    @PutMapping("/update-profile")
    public ResponseEntity<String> updateUserInfo(@RequestBody UserUpdateRequest request,
                                                 @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        userService.updateUserInfo(request, userDetails.getUsername());
        return ResponseEntity.ok("User info updated");
    }


    @GetMapping("/profile")
    public ResponseEntity<UserProfileDto> getUserProfile(@AuthenticationPrincipal UserDetails userDetails) {
        UserProfileDto profile = userService.getUserProfile(userDetails.getUsername());
        return ResponseEntity.ok(profile);
    }

    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody PasswordChangeRequest request,
                                                 @AuthenticationPrincipal UserDetails userDetails) {
        userService.changePassword(request, userDetails.getUsername());
        return ResponseEntity.ok("Password changed successfully");
    }
}



