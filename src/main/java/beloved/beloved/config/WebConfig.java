/*package beloved.beloved.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//Uygulama başlarken yüklenir. Spring MVC yapılandırmasının özelleştirilebilmesini sağlar.
//Frontend-backend arasında güvenli bir şekilde veri alışverişi yapılabilir.
@Configuration
public class WebConfig implements WebMvcConfigurer {

    //Cors yapılandırmasını tanımlamak için override edilir
    //Başka domainlerden gelen HTTP isteklerine izin verir.
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") //tüm endpointlere izin ver
                .allowedOrigins("http://localhost:5173")  //reactın çalıştığı port
                .allowedMethods("GET", "POST", "PUT", "DELETE","OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true); //tarayıcı kimlik bilgileri gönderiyorsa bu ayar zorunlu.
        //bununla axios-fetch isteği atılırsa backend kabul eder.
    }

    //Dış kaynak klasörlerine erişimi yapılandırmak için override edilir.
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");  //resim yüklemek için
    }
}*/
