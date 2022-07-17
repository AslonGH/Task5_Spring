package uz.pdp.rest_api_jwt.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.rest_api_jwt.entity.Employee;
import uz.pdp.rest_api_jwt.entity.enums.RoleName;
import uz.pdp.rest_api_jwt.payload.LoginDto;
import uz.pdp.rest_api_jwt.payload.RegisterDto;
import uz.pdp.rest_api_jwt.repository.*;
import uz.pdp.rest_api_jwt.security.JwtProvider;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
public class DirectorService {


    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;


    public ApiResponse registerDirector(RegisterDto registerDto){

        boolean existsByEmail = employeeRepository.existsByEmail(registerDto.getEmail());
        if (existsByEmail){
            return new ApiResponse("Bunday Email Allqachon mavjud",false);
        }

          Employee director=new Employee();
        director.setFirstname(registerDto.getFirstname());
        director.setLastname(registerDto.getLastname());
        director.setEmail(registerDto.getEmail());
        director.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        director.setCompany(registerDto.getCompany());
        director.setRoles(Collections.singleton(roleRepository.findByRoleName(RoleName.DIRECTOR)));
        director.setEmailCode(UUID.randomUUID().toString());
        employeeRepository.save(director);

        // EMAILGA YUBORISH METHODINI CHAQIRYAPMIZ
        sendEMail(director.getEmailCode(),director.getEmail());
        return new ApiResponse("Muvaffaqiyatli röyxatdan ötdingiz,Accountning aktivlashtirilishi uchun emailingizni tasdiqlang",true);
    }

    // SimpleMailMessage Classi orqali Userning Emailiga tasdialash Linkini jönatamiz
    public void sendEMail(String emailCode, String sendingEmail){
      try {
     SimpleMailMessage mailMessage = new SimpleMailMessage();
    mailMessage.setFrom("test@gmail.com"); // JÖNATILADIGAN EMAIL(IXTIYORIY EMAILNI YOZSA BÖLADI)
    mailMessage.setTo(sendingEmail);
    mailMessage.setSubject("Accountni tasdiqlash");
    mailMessage.setText("<a href='http://localhost:8080/api/auth/verifyEmail/director?emailCode="+emailCode+"&email="+sendingEmail+"'>Tasdiqlang</a>");
    javaMailSender.send(mailMessage);
    }catch (Exception ignored){

   }
}

    // User ÖZ EMAILINI OCHIB, TASDIQLASH LINKINI BOSGANDA SHU METHOD ISHLAYDI.Bunda LINK ICHIDAN email va emailCode ni ajratib oladi.
    public ApiResponse directorVerifyEmail(String emailCode,String email){

        Optional<Employee> optionalUser = employeeRepository.findByEmailCodeAndEmail(emailCode,email);
        if (optionalUser.isPresent()){
               Employee director = optionalUser.get();
               director.setEnabled(true);
               director.setEmailCode(null);
               employeeRepository.save(director);
               return new ApiResponse("Account tasdiqlandi",true);
           }
               return new ApiResponse("Account allaqachon tasdiqlangan",false);
    }

    // BU METHOD USERNAME VA PASSWORD NI DB B-N SOLISHTIRADI VA USER ENTITYDAGI 4 BOOLEN FIELD/GA NISBATAN FALSE EMASLIGINI TEKSHIRADI.
    public ApiResponse loginDirector(LoginDto loginDto) {
    try {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
        loginDto.getUsername(),loginDto.getPassword()));

        Employee director = (Employee) authentication.getPrincipal();

        // USERNAME NI ROLE B-N BIRGA TOKEN QILIB QAYTARAMIZ; KEYINGI SAFAR User SHU TOKEN BILAN LOGIN QILADI:
        String token = jwtProvider.generateToken(loginDto.getUsername(),director.getRoles());
        return new ApiResponse("Token",true, token);

    }catch (BadCredentialsException  badCredentialsException){
        return new ApiResponse("Parol yoki login xato",false);
    }
  }

}


