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

//Otomatik bir beandir. JWT ile doğrulama işlemi yapar.
@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final OurUserDetailsService userDetailsService;

    public JWTAuthFilter(JWTUtil jwtUtil, OurUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    //HTTP isteği geldiğinde çalışır, token kontrolü burada sağlanır.
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        //Authorization headerı alınır.Bearer token şeklindedir.
        final String authHeader = request.getHeader("Authorization");

        String jwt = null;
        String username = null;

        //Header "Bearer <token>" şeklindedir
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7); // "Bearer " kısmını çıkar
            try {
                username = jwtUtil.extractUsername(jwt); //token içinden mail çıkarılır. Kontrol edilir.
            } catch (Exception e) {
                // Token geçersiz veya hatalıysa log veya silent geçiş
                logger.error("JWT token extract error: " + e.getMessage());
            }
        }

        // Tokendan email başarılı şekilde çıkartılmışsa ve
        //  henüz SecurityContext dolu değilse(oturum açılmamışsa) doğrulamaya geçilir.
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {


           //Veritabanından bilgiler alınır.
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

           //Token süresi,email,token imzalanması düzgün mü kontrolü yapılır.
            if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 6. SecurityContextHolder'a oturum bilgilerini ekle
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        //Bu filter görevini bitirdi, diğer filterlara geç.
        filterChain.doFilter(request, response);
    }
}
