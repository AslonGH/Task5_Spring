

package uz.pdp.rest_api_jwt.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.rest_api_jwt.entity.Employee;
import uz.pdp.rest_api_jwt.entity.Task;
import uz.pdp.rest_api_jwt.entity.TourniquetCard;
import uz.pdp.rest_api_jwt.entity.TourniquetHistory;
import uz.pdp.rest_api_jwt.entity.enums.RoleName;
import uz.pdp.rest_api_jwt.payload.LoginDto;
import uz.pdp.rest_api_jwt.payload.RegisterDto;
import uz.pdp.rest_api_jwt.repository.*;
import uz.pdp.rest_api_jwt.security.JwtProvider;

import javax.mail.internet.MimeMessage;
import javax.persistence.OneToOne;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class EmployeeService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeTaskRepository employeeTaskRepository;

    @Autowired
    TourniquetHistoryRepository tourniquetHistoryRepository;

    @Autowired
    TourniquetCardRepository tourniquetCardRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;


    public ApiResponse registerEmployee(RegisterDto registerDto) {


        // BUNAQA EMAIL BASADA BÖLMASLIGI KERAK
        boolean existsByEmail = employeeRepository.existsByEmail(registerDto.getEmail());
        if (existsByEmail) {
            return new ApiResponse("Bunday Email Allqachon mavjud", false);
        }

        Employee employee = new Employee();
        employee.setFirstname(registerDto.getFirstname());
        employee.setLastname(registerDto.getLastname());
        employee.setEmail(registerDto.getEmail());
        employee.setRoles(Collections.singleton(roleRepository.findByRoleName(RoleName.EMPLOYEE)));
        employee.setEmailCode(UUID.randomUUID().toString());
        employeeRepository.save(employee);
        sendEMail(employee.getEmailCode(), employee.getEmail());
        return new ApiResponse("Muvaffaqiyatli röyxatdan digitalizing Accountning aktivlashtirilishi uchun emailingizni tasdiqlang", true);
    }

    public ApiResponse employeeVerifyEmail(String emailCode, String email, String password) {

        Optional<Employee> optionalEmployee = employeeRepository.findByEmailCodeAndEmail(emailCode, email);

        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            employee.setEnabled(true);
            employee.setEmailCode(null);
            employee.setPassword(passwordEncoder.encode(password));
            employeeRepository.save(employee);
            return new ApiResponse("Account tasdiqlandi", true);
        }
        return new ApiResponse("Account allaqachon tasdiqlangan", false);
    }

    public ApiResponse loginEmployee(LoginDto loginDto) {

        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getUsername(), loginDto.getPassword()));

            // UserDetails dagi User ni beradi...
            Employee employee = (Employee) authentication.getPrincipal();
            // USERNAME NI ROLE B-N BIRGA TOKEN QILIB QAYTARAMIZ;KEYINGI SAFAR User SHU TOKEN BILAN LOGIN QILADI:
            String token = jwtProvider.generateToken(loginDto.getUsername(), employee.getRoles());
            return new ApiResponse("Token", true, token);

        } catch (BadCredentialsException badCredentialsException) {
            return new ApiResponse("Parol yoki login xato", false);
        }
    }

    public void sendEMail(String emailCode, String sendingEmail) {

        String link = "http://localhost:8080/api/auth/verifyEmail/employee?emailCode=" + emailCode + "&email=" + sendingEmail;
        String body = "<form action=" + link + " method=\"post\">\n" +
                "<label>Create password for your cabinet</label>" +
                "<br/><input type=\"text\" name=\"password\" placeholder=\"password\">\n" +
                "<br/> <button>Submit</button>\n" +
                "</form>";
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            helper.setFrom("Teat@gmail.com");
            helper.setTo(sendingEmail);
            helper.setText(body, true);
            javaMailSender.send(message);
        } catch (Exception ignored) {
        }
    }

    // ISHLADI
    public ApiResponse getEmployeeByIdTimeStatus(UUID uuid, String date1, String date2) {

        Optional<Employee> optionalEmployee = employeeRepository.findById(uuid);
        if (!optionalEmployee.isPresent()) {
            return new ApiResponse("Bunday Employee mavjud emas", false);
        }

        Date dateA;
        Date dateB;

        DateFormat dateFormat2 = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
        try {
            dateA = dateFormat2.parse(date1);
            java.sql.Timestamp sql_Timestamp1 = new java.sql.Timestamp(dateA.getTime());

            dateB = dateFormat2.parse(date2);
            java.sql.Timestamp sql_Timestamp2 = new java.sql.Timestamp(dateB.getTime());
            List<TourniquetHistory> getInTimeBetween = tourniquetHistoryRepository.findAllByCard_EmployeeIdAndGetInTimeBetween(uuid, sql_Timestamp1, sql_Timestamp2);
            List<TourniquetHistory> getOutTimeBetween = tourniquetHistoryRepository.findAllByCard_EmployeeIdAndGetOutTimeBetween(uuid, sql_Timestamp1, sql_Timestamp2);
          //  return new ApiResponse(" Employee ", true, Collections.singletonList(getInTimeBetween));
            return new ApiResponse(" Employee ", true, Collections.singletonList(getOutTimeBetween), Collections.singletonList(getInTimeBetween));
        } catch (ParseException e) {
            return null;
        }
        // return new ApiResponse(" Employee ", true, taskList, Collections.singletonList(timeBetween));

    }


    public ApiResponse getEmployeeByIdTimeStatus1(UUID uuid, String date1, String date2) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(uuid);
        if (!optionalEmployee.isPresent()) {
            return new ApiResponse("Bunday Employee mavjud emas", false);
        }

        try {
            LocalDate ld1 = LocalDate.parse(date1, DateTimeFormatter.ofPattern("yyy-MM-dd"));
            LocalDate ld2 = LocalDate.parse(date2, DateTimeFormatter.ofPattern("yyy-MM-dd"));
            List<Task> taskList = employeeTaskRepository.findAllByCompletedAtBetweenAndEmployeeIdAndStatus(ld1, ld2, uuid, 3);
            return new ApiResponse(" Employee ", true, taskList);

        } catch (Exception e) {
            return new ApiResponse(" Not found ", false, null);
        }

    }

}


/*       Set<Task> tasks = employee.getTask();
         Optional<TourniquetCard> tourniquetCardByEmployeeId = tourniquetCardRepository.findByEmployeeId(uuid);
         if (!tourniquetCardByEmployeeId.isPresent()){
             return new ApiResponse("Bunday tourniquetCard mavjud emas", false);
         }
        TourniquetCard tourniquetCard = tourniquetCardByEmployeeId.get();
        UUID id = tourniquetCard.getId();
        Optional<TourniquetHistory> byCardId = tourniquetHistoryRepository.findByCardId(id);
        return new ApiResponse("Task topildi", true, tasks, Collections.singletonList(byCardId));



         // ISHLAMIYAPTI CHUNKI 3 - EMPLOYEE(MANAGER) DA MUAMMO BOR

        public ApiResponse getEmployees() {
        List<Employee> all = employeeRepository.findAll();
        return new ApiResponse(Collections.singletonList(all),true,"");
      }
*/
/*      LocalDate ld=LocalDate.parse(date1, DateTimeFormatter.ofPattern("dd.MM.yyy hh.mm.ss "));  // SHU YERDA XATO
        LocalDate ld1=LocalDate.parse(date2, DateTimeFormatter.ofPattern("dd.MM.yyy hh.mm.ss "));
        List<Task> taskList = employeeTaskRepository.findAllByCompletedAtBetweenAndEmployeeIdAndStatus(ld, ld1, uuid, 3);
        Timestamp timestamp1=Timestamp.valueOf(date1);
        Timestamp timestamp2=Timestamp.valueOf(date2);
*/


