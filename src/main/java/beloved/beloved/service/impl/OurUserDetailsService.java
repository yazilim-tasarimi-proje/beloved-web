package beloved.beloved.service.impl;

import beloved.beloved.entity.User;
import beloved.beloved.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class OurUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

   //Kullanıcı doğrulamasının yapıldığı metottur.
    //Spring Security bu metodu çağırarak giriş yapan kullanıcıyı bulmaya çalışır.
    //Kullanıcı veritabanında aranır. User nesnesi securitynin tanıdığı UserDetails türüne dönüştürülür.
    //UserDetails nesnesi ile JWt tabanlı girişlerde kimlik doğrulama yapılabilir.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getRole().name())
                .build();
    }
}