package beloved.beloved.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionHandlingAspect {

    @Around("execution(public org.springframework.http.ResponseEntity beloved.beloved.service.impl..*(..))")
    public Object handleServiceExceptions(ProceedingJoinPoint pjp) throws Throwable {
        try {
            return pjp.proceed();
        } catch (Exception e) {
            // İstersen Logger ile kaydet
            return ResponseEntity.status(500).body("Beklenmeyen hata oluştu: " + e.getMessage());
        }
    }
}
