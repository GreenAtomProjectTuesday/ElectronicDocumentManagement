package electonic.document.management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Bean
    public org.springframework.security.crypto.password.PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(8);
    }
}
