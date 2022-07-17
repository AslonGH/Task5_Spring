package uz.pdp.rest_api_jwt.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uz.pdp.rest_api_jwt.entity.Employee;
import uz.pdp.rest_api_jwt.security.JwtFilter;
import uz.pdp.rest_api_jwt.service.AuthService;
import java.util.Properties;
import java.util.UUID;

  // PASSWORD NI DB ga ochiq saqlash mumkin emas.Shu sababli PasswordEncoder ni chaqirib ishlatamiz
  // @Configuration bölgan Class ichida Bean Method yaratish mumkin.Va shu Method ni boshqa Classdan, Autoweired qilish mumkin
  @Configuration
  @EnableWebSecurity
  public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  JwtFilter jwtFilter;

  @Autowired
  AuthService authService;



    // authService(User qaytaradi) va password ni berib AuthenticationManager NI YASAB OLAMIZ
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(authService).passwordEncoder(passwordEncoder());
    }


    // TEPADA YASALGAN <<AuthenticationManager>>.  BU METHOD USERNAME VA PASSWORDNI TEKSHIRIB BERADI:
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
   }


    // Authservice Class ining, registerUser METHOD IDA BERILGAN PASSWORDNI ÖZIDA SAQLAYDI.
    @Bean
    PasswordEncoder passwordEncoder(){ return new BCryptPasswordEncoder();
    };


    @Override
    protected void configure(HttpSecurity http) throws Exception {
    http
            .csrf().disable()
            .httpBasic().disable()
            .authorizeRequests()
            .antMatchers( "/api/**"
    //"/api/auth/register/director","/api/auth/login/**","/api/auth/verifyEmail/**"

            ).permitAll()
            .anyRequest().authenticated(); // QOLGAN YÖLLAR QULFLANSIN,Ammo Token b-n kirsa böladi.
    // UsernamePasswordAuthenticationFilter ning Classidan oldin jwtFilter ni ishlat
    http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    // Userni SESSIONDA USHLAMASDAN ISHLA
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }


    // EMAILDAN XABAR JÖNATISH METHODI.
    @Bean
    public JavaMailSender javaMailSender(){

    JavaMailSenderImpl mailSender=new JavaMailSenderImpl();
     mailSender.setHost("smtp.gmail.com");
      mailSender.setPort(587);
      mailSender.setUsername("aslon.dinov@gmail.com");
      mailSender.setPassword("keuptauhdbxfdsov");

    Properties properties = mailSender.getJavaMailProperties();
      properties.put("mail.transport.protocol","smtp");
      properties.put("mail.smtp.auth","true");
      properties.put("mail.smtp.starttls.enable","true");
      properties.put("mail.debug","true");
      return mailSender;
    };


      // TIZIMGA KIM KIRGANINI BILISH
  /*
      @Bean
      public UUID getEmployeeById(){
        Employee principal = (Employee) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return  principal.getId();
    // return new HttpEntity<>(principal.getId());
    // return ResponseEntity.ok(principal.getId());
    }
  */


}
