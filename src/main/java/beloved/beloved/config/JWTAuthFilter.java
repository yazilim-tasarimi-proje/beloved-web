package beloved.beloved.config;

import beloved.beloved.service.impl.JWTUtil;
import beloved.beloved.service.impl.OurUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final OurUserDetailsService userDetailsService;

    public JWTAuthFilter(JWTUtil jwtUtil, OurUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 1. Authorization header'ı al
        final String authHeader = request.getHeader("Authorization");

        String jwt = null;
        String username = null;

        // 2. Header "Bearer <token>" şeklinde olmalı
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7); // "Bearer " kısmını çıkar
            try {
                username = jwtUtil.extractUsername(jwt);
            } catch (Exception e) {
                // Token geçersiz veya hatalıysa log veya silent geçiş
                logger.error("JWT token extract error: " + e.getMessage());
            }
        }

        // 3. Username varsa ve henüz SecurityContext dolu değilse devam et
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // 4. Token geçerliliğini kontrol et (kullanıcı email'i ve expiration)
            if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {
                // 5. Authentication token oluştur
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 6. SecurityContextHolder'a ekle
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 7. Zinciri devam ettir
        filterChain.doFilter(request, response);
    }
}
