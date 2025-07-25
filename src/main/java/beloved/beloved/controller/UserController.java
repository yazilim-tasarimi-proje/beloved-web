package beloved.beloved.controller;


import beloved.beloved.dto.AuthResponse;
import beloved.beloved.dto.LoginDto;
import beloved.beloved.dto.RegisterDto;
import beloved.beloved.service.impl.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(400).body("Invalid token");
        }

        String token = authorizationHeader.substring(7);

        userService.logout(token);

        return ResponseEntity.ok("Logged out successfully");
    }
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteAccount(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Invalid token");
        }

        String token = authorizationHeader.substring(7);
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
    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        throw new RuntimeException("Authorization header missing or invalid");
    }

}

