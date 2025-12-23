package beloved.beloved.service.impl;


import beloved.beloved.dto.AuthResponse;
import beloved.beloved.dto.LoginDto;
import beloved.beloved.dto.PasswordChangeRequest;
import beloved.beloved.dto.RegisterDto;
import beloved.beloved.dto.UserProfileDto;
import beloved.beloved.dto.UserUpdateRequest;

import beloved.beloved.entity.User;
import beloved.beloved.repository.AddressRepository;
import beloved.beloved.repository.UserRepository;
import beloved.beloved.service.IUserService;
import beloved.beloved.util.EmailUtil;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.mail.MessagingException;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Service
public class UserService implements IUserService {


    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final EmailUtil emailUtil;
    private final AddressRepository addressRepository;

    public UserService(
            UserRepository userRepository,
            JWTUtil jwtUtil,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            EmailUtil emailUtil,
            AddressRepository addressRepository
    ) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.emailUtil = emailUtil;
        this.addressRepository = addressRepository;
    }

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private Set<String> blacklistedTokens = new HashSet<>(); //Çıkış yapan ya da silinen kullanıcıların tokenlarını geçersiz kılmak için.

    @Override
    public ResponseEntity<String> registerUser(RegisterDto registerDto) {
        // Email varsa kayıt etme
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

    @Override
    public ResponseEntity<AuthResponse> loginUser(LoginDto loginDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getEmail(), loginDto.getPassword()
                    )
            );

            User user = userRepository.findByEmail(loginDto.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            String jwt = jwtUtil.generateToken(user.getEmail(), user.getRole().toString());
            return ResponseEntity.ok(new AuthResponse(jwt, user.getRole().toString()));

        } catch (Exception e) {
            log.error("Authentication failed: ", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<String> deleteAccount(String token) {
        String email = jwtUtil.extractUsername(token);
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            return ResponseEntity.status(404).body("User not found");
        }

        userRepository.delete(user.get());
        blacklistedTokens.add(token);
        return ResponseEntity.ok("Account deleted successfully");
    }

    @Override
    public void logout(String token) {
        DecodedJWT jwt = jwtUtil.decodeJWT(token);
        if (jwt != null) {
            blacklistedTokens.add(token);
        }
    }

    @Override
    public boolean isTokenBlacklisted(String token) {
        return blacklistedTokens.contains(token);
    }

    @Override
    public String forgotPassword(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) return null;

        try {
            emailUtil.sendSetPasswordEmail(email);
            return "Please check your email to set a new password.";
        } catch (MessagingException e) {
            return "Unable to send email. Try again.";
        }
    }

    @Override
    public String setPassword(String email, String newPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(passwordEncoder.encode(newPassword)); // Güvenlik için hash'le!
        userRepository.save(user);
        return "Password updated successfully";
    }
    @Override
    public void updateUserInfo(UserUpdateRequest request, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (request.getFirstName() != null && !request.getFirstName().isBlank()) {
            user.setFirstName(request.getFirstName());
        }

        if (request.getLastName() != null && !request.getLastName().isBlank()) {
            user.setLastName(request.getLastName());
        }

        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            user.setEmail(request.getEmail());
        }

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        userRepository.save(user);
    }


    @Override
    public UserProfileDto getUserProfile(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new UserProfileDto(user.getFirstName(), user.getLastName(), user.getEmail());
    }


    @Override
    public void changePassword(PasswordChangeRequest request, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Eski şifre doğru mu?
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }

        // Aynı şifreyle güncellemeye izin verme (opsiyonel)
        if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
            throw new IllegalArgumentException("New password must be different from the old password");
        }

        // Yeni şifreyi encode edip kaydet
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

}



