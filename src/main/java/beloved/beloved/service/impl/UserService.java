package beloved.beloved.service.impl;

import beloved.beloved.dto.AuthResponse;
import beloved.beloved.dto.LoginDto;
import beloved.beloved.dto.RegisterDto;
import beloved.beloved.entity.User;
import beloved.beloved.repository.UserRepository;
import beloved.beloved.util.EmailUtil;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private EmailUtil emailUtil;

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    // Blacklist işlemi için geçici bir depolama
    private Set<String> blacklistedTokens = new HashSet<>();


    public ResponseEntity<String> registerUser(RegisterDto registerDto) {
        if (userRepository.findByEmail(registerDto.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already in use");
        }
        User user = new User();
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRole(User.Role.USER);
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }

    public ResponseEntity<AuthResponse> loginUser(LoginDto loginDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getEmail(),
                            loginDto.getPassword()
                    )
            );

            User user = userRepository.findByEmail(loginDto.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            String role = user.getRole().toString();
            String jwtToken = jwtUtil.generateToken(user.getEmail(), role);

            AuthResponse response = new AuthResponse(jwtToken, role);

            return ResponseEntity.ok(response);
        }catch (Exception e) {
                log.error("Authentication failed: ", e);
                return ResponseEntity.badRequest().build();
            }

        }
    public ResponseEntity<String> deleteAccount(String token) {
        String email = jwtUtil.extractUsername(token);
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(404).body("User not found");
        }

        userRepository.delete(optionalUser.get());
        blacklistedTokens.add(token);  // Silinen hesabın token'ı da geçersiz olsun

        return ResponseEntity.ok("Account deleted successfully");
    }

   /* public ResponseEntity<String> assignAdminRole(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setRole(User.Role.ADMIN);

        userRepository.save(user);

        return ResponseEntity.ok("Role assigned successfully");
    }*/

    public void logout(String token) {
        DecodedJWT decodedJWT = jwtUtil.decodeJWT(token);
        if (decodedJWT != null) {
            blacklistedTokens.add(token);
        }
    }

    // Token'ın geçerli olup olmadığını kontrol et
    public boolean isTokenBlacklisted(String token) {
        return blacklistedTokens.contains(token);
    }



    public String forgotPassword(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            return null;
        }

        try {
            emailUtil.sendSetPasswordEmail(email);
        } catch (MessagingException e) {
            return "Unable to send set password email. Please try again.";
        }

        return "Please check your email to set a new password to your account.";
    }


    public String setPassword(String email, String newPassword) {
        User user=userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new RuntimeException("User not found" + email));
        user.setPassword(newPassword);
        userRepository.save(user);
        return "New password set successfully";
    }

   /* public AdminDto getAdminDetailsByToken(String token) {
        String email = jwtUtil.extractUsername(token);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new AdminDto(user.getFirstName(), user.getLastName(), user.getEmail());
    }

    public void updateAdminInfo(String token, AdminDto adminDto) {
        String emailFromToken = jwtUtil.extractUsername(token); // e.g. email
        User user = userRepository.findByEmail(emailFromToken)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setFirstName(adminDto.getFirstName());
        user.setLastName(adminDto.getLastName());
        user.setEmail(adminDto.getEmail());

        userRepository.save(user);
    }*/
}
