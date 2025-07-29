package beloved.beloved.service;

import beloved.beloved.dto.AddressUpdateRequest;
import beloved.beloved.dto.AuthResponse;
import beloved.beloved.dto.LoginDto;
import beloved.beloved.dto.PasswordChangeRequest;
import beloved.beloved.dto.RegisterDto;
import beloved.beloved.dto.UserUpdateRequest;
import beloved.beloved.entity.User;
import org.springframework.http.ResponseEntity;

public interface IUserService {
    ResponseEntity<String> registerUser(RegisterDto registerDto);
    ResponseEntity<AuthResponse> loginUser(LoginDto loginDto);
    ResponseEntity<String> deleteAccount(String token);
    void logout(String token);
    boolean isTokenBlacklisted(String token);
    String forgotPassword(String email);
    String setPassword(String email, String newPassword);
    void updateUserInfo(UserUpdateRequest request, String userEmail); // email JWT'den alınır
    User getUserProfile(String userEmail);
    void changePassword(PasswordChangeRequest request, String userEmail);
}

