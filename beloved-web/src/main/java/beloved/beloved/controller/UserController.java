package beloved.beloved.controller;

import beloved.beloved.controller.Facade.UserFacade;
import beloved.beloved.dto.AuthResponse;
import beloved.beloved.dto.LoginDto;
import beloved.beloved.dto.PasswordChangeRequest;
import beloved.beloved.dto.RegisterDto;
import beloved.beloved.dto.UserProfileDto;
import beloved.beloved.dto.UserUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserFacade userFacade;

    public UserController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterDto registerDto) {
        return userFacade.register(registerDto);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@RequestBody LoginDto loginDto) {
        return userFacade.login(loginDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authorizationHeader) {
        return userFacade.logout(authorizationHeader);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteAccount(@RequestHeader("Authorization") String authorizationHeader) {
        return userFacade.deleteAccount(authorizationHeader);
    }

    @PutMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        return userFacade.forgotPassword(email);
    }

    @PutMapping("/set-password")
    public ResponseEntity<String> setPassword(@RequestParam String email,
                                              @RequestBody String newPassword) {
        return userFacade.setPassword(email, newPassword);
    }

    @PutMapping("/update-profile")
    public ResponseEntity<String> updateUserInfo(@RequestBody UserUpdateRequest request,
                                                 @AuthenticationPrincipal UserDetails userDetails) {
        return userFacade.updateUserInfo(request, userDetails.getUsername());
    }

    @GetMapping("/profile")
    public ResponseEntity<UserProfileDto> getUserProfile(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(userFacade.getUserProfile(userDetails.getUsername()));
    }

    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody PasswordChangeRequest request,
                                                 @AuthenticationPrincipal UserDetails userDetails) {
        return userFacade.changePassword(request, userDetails.getUsername());
    }
}
