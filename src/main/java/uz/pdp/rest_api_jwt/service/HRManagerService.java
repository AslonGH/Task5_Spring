package uz.pdp.rest_api_jwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring5.SpringTemplateEngine;
import uz.pdp.rest_api_jwt.entity.Employee;
import uz.pdp.rest_api_jwt.entity.enums.RoleName;
import uz.pdp.rest_api_jwt.payload.LoginDto;
import uz.pdp.rest_api_jwt.payload.RegisterDto;
import uz.pdp.rest_api_jwt.repository.*;
import uz.pdp.rest_api_jwt.security.JwtProvider;
import javax.mail.internet.MimeMessage;
import org.thymeleaf.context.Context;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
public class HRManagerService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    public ApiResponse registerHRManager(RegisterDto registerDto) {

       // BUNAQA EMAIL BASADA BÖLMASLIGI KERAK
        boolean existsByEmail = employeeRepository.existsByEmail(registerDto.getEmail());
        if (existsByEmail) {
            return new ApiResponse("Bunday Email Allqachon mavjud", false);
        }

         Employee hrManager = new Employee();
        hrManager.setFirstname(registerDto.getFirstname());
        hrManager.setLastname(registerDto.getLastname());
        hrManager.setEmail(registerDto.getEmail());
        hrManager.setCompany(registerDto.getCompany());
        hrManager.setRoles(Collections.singleton(roleRepository.findByRoleName(RoleName.HR_MANAGER)));
        hrManager.setEmailCode(UUID.randomUUID().toString());
        employeeRepository.save(hrManager);
        try {
            // EMAILGA YUBORISH METHODINI CHAQIRYAPMIZ
            sendEMail(hrManager.getEmail(), hrManager.getEmailCode());
            return new ApiResponse("Muvaffaqiyatli röyxatdan digitalizing Accountning aktivlashtirilishi uchun emailingizni tasdiqlang", true);
        }catch (Exception ignored) {
            return new ApiResponse(" röyxatdan ötmdiz", false);
        }
    }


    public ApiResponse registerHRManager1(RegisterDto registerDto) {

         Employee hrManager = new Employee();
        hrManager.setFirstname(registerDto.getFirstname());
        hrManager.setLastname(registerDto.getLastname());
        hrManager.setEmail(registerDto.getEmail());
        hrManager.setEmailCode(UUID.randomUUID().toString());
        employeeRepository.save(hrManager);
        try {
            // EMAILGA YUBORISH METHODINI CHAQIRYAPMIZ
            sendEMail(hrManager.getEmail(), hrManager.getEmailCode());
            return new ApiResponse("emailingizni tasdiqlang", true);
        }catch (Exception ignored) {
            return new ApiResponse(" röyxatdan ötmdiz", false);
        }
    }


    // SHU EMAIL GA QÖSHIB HTML NI JÖNATISH NI BAJARISH KERAK !!!
    public void sendEMail(String sendingEmail, String emailCode) {

        String link = "http://localhost:8080/api/auth/verifyEmail/hrManager?emailCode=" + emailCode + "&email=" + sendingEmail;
        String body = "<form action=" + link + " method=\"post\">\n" +  // methoddan oldin joy tasha:  " method
                "<label>Create password for your cabinet</label>" +
                "<br/><input type=\"text\" name=\"password\" placeholder=\"password\">\n" +
                "<br/><button> Submit </button>\n" +
                "</form>";
        try {
             MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper=new MimeMessageHelper(message,MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            helper.setFrom("Teat@gmail.com");
            helper.setTo(sendingEmail);
            helper.setText(body,true);
            mailSender.send(message);
        } catch (Exception ignore) {
        }
    }


    public ApiResponse HRManagerVerifyEmail(String emailCode, String email,String password){
    Optional<Employee> optionalHRManager = employeeRepository.findByEmailCodeAndEmail(emailCode,email);
    if (optionalHRManager.isPresent()){
        Employee hrManager = optionalHRManager.get();
        hrManager.setEnabled(true);
        hrManager.setEmailCode(null);
        hrManager.setPassword(passwordEncoder.encode(password));
        employeeRepository.save(hrManager);
        return new ApiResponse("Account tasdiqlandi",true);
    }
    return new ApiResponse("Account allaqachon tasdiqlangan",false);
}


    public ApiResponse loginHRManager(LoginDto loginDto) {
    try {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
        loginDto.getUsername(), loginDto.getPassword()));

        Employee hrManager = (Employee) authentication.getPrincipal();

        // USERNAME NI ROLE B-N BIRGA TOKEN QILIB QAYTARAMIZ;KEYINGI SAFAR User SHU TOKEN BILAN LOGIN QILADI:
        String token = jwtProvider.generateToken(loginDto.getUsername(), hrManager.getRoles());
        return new ApiResponse("Token",true,token);

    }catch (BadCredentialsException  badCredentialsException){
        return new ApiResponse("Parol yoki login xato",false);
    }}

}



