/*
package uz.pdp.rest_api_jwt.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.rest_api_jwt.payload.TaskDto;
import uz.pdp.rest_api_jwt.repository.TaskRepository;
import uz.pdp.rest_api_jwt.service.ApiResponse;
import uz.pdp.rest_api_jwt.service.TaskService;
import java.util.UUID;


@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TaskService taskService;

    // MANAGER,DIRECTOR
    @PreAuthorize(value ="hasAnyRole('MANAGER','DIRECTOR')")
    @GetMapping
    public HttpEntity<?>getProduct(){
        return ResponseEntity.ok(taskRepository.findAll());
    }


    // DIRECTOR
    @PreAuthorize(value ="hasAnyRole('MANAGER','DIRECTOR')")
    @PostMapping
    public HttpEntity<?>addTask(@RequestBody TaskDto taskDto){
        ApiResponse apiResponse = taskService.addTask(taskDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }


    // DIRECTOR
    @PreAuthorize(value ="hasAnyRole('MANAGER','DIRECTOR')")
    @PutMapping("/{id}")
    public HttpEntity<?>editTask(@PathVariable UUID id, @RequestBody TaskDto taskDto){
        ApiResponse apiResponse = taskService.editTask(id,taskDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }


    // DIRECTOR
    @PreAuthorize(value ="hasAnyRole('MANAGER','DIRECTOR')")
    @DeleteMapping("/{id}")
    public HttpEntity<?>deleteTask(@PathVariable UUID id){
        ApiResponse apiResponse = taskService.deleteTask(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }


    // MANAGER,DIRECTOR,USER
    @PreAuthorize(value ="hasAnyRole('DIRECTOR','MANAGER','USER')")
    @GetMapping("/{id}")
    public HttpEntity<?>getTask(@PathVariable UUID id){
        ApiResponse apiResponse = taskService.getTask(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }



}
*/
/*        Optional<Employee> optionalEmployee = employeeRepository.findById(taskDto.getEmployeeId());
        if (!optionalEmployee.isPresent())
            return new ApiResponse("Employee topilmadi", false);
        Employee manager = optionalEmployee.get();

        // QÖSHILMAYAPTI  ???
        Set<Task> tasks = new HashSet<>(manager.getTask());
        tasks.add(task);
        sendEMail(manager.getEmail());*/

/*
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    JavaMailSender javaMailSender;

    // SecurityConfig Class idagi AuthenticationManager qaytaruvchi Methodni Autoweired qilamiz.
    // USER VA PASSWORDNI AVTOMATIK AUTHENTICATE QILADIGAN CLASS
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;*/
/*
    public ApiResponse registerUser(RegisterDto registerDto){

        // BUNAQA EMAIL BASADA BÖLMASLIGI KERAK
        boolean existsByEmail = userRepository.existsByEmail(registerDto.getEmail());
        if (existsByEmail) {
            return new ApiResponse("Bunday Email Allqachon mavjud", false);
        }
          User user = new User();
        user.setFirstname(registerDto.getFirstname());
        user.setLastname(registerDto.getLastname());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        // Collections.singleton- bitta object ni Collection qilib beradi.
        // Set<> Collectiondan voris olgan, shu u-n  Set örnida Collection bersak böladi

        user.setRoles(Collections.singleton(roleRepository.findByRoleName(RoleName.USER)));
        user.setEmailCode(UUID.randomUUID().toString());
        userRepository.save(user);
        // EMAILGA YUBORISH METHODINI CHAQIRYAPMIZ
        sendEMail(user.getEmail(), user.getEmailCode());
        return new ApiResponse("User saqlandi. Accountning aktivlashtirilishi uchun emailingizni tasdiqlang", true);
    }


    // SimpleMailMessage Classi orqali Userning Emailiga tasdialash Linkini jönatamiz
    public Boolean sendEMail(String sendingEmail, String emailCode){
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("aslon.dinov@gmail.com"); //qaysi emaildan jönatilishi(IXTIYORIY EMAILNI YOZSA BÖLADI)
            mailMessage.setTo(sendingEmail);
            mailMessage.setSubject("Accountni tasdiqlash");
            mailMessage.setText("<a href='http://localhost:8080/api/auth/verifyEmail?emailCode=" + emailCode + "&email=" + sendingEmail + "'>Tasdiqlang</a>");
            javaMailSender.send(mailMessage);
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    // User ÖZ EMAILINI OCHIB, TASDIQLASH LINKINI BOSGANDA SHU METHOD ISHLAYDI.Bunda LINK ICHIDAN email va emailCode ni ajratib oladi.
    public ApiResponse verifyEmail(String emailCode, String email) {

        Optional<User> optionalUser = userRepository.findByEmailAndEmailCode(email, emailCode);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            // EMAIL AKTIVLANDI. Chunki U Emailiga borgan linkini tasdiqladi
            // Userdagi setEnabled Method false edi, endi shuni true qilamiz(YOQAMIZ)
            user.setEnabled(true);
            // EmailCode ni null qilamiz, Chunki Mobodo 2-marta linkini bossa, else(!optionalUser.isPresent()) ga tushib
            // Link b-n kelgan EmailCode DB dan topmasdan,"Account allaqachon tasdiqlangan"  xabarini qaytarsin.
            user.setEmailCode(null);
            userRepository.save(user);
            return new ApiResponse("Account tasdiqlandi", true);
        }
        return new ApiResponse("Account allaqachon tasdiqlangan", false);
    }


    public ApiResponse login(LoginDto loginDto) {
    try {
    // authenticationManager.authenticate: PASTDAGI loadUserByUsername METHODI YORDAMIDA USERNAMENI AuthService Classi uchun DB DAN TOPIB,
    // BERADI,VA BU CLASS UserDetails QAYTARADI.VA BU UserDetails ni SecurityConfig CLASSIDAGI configure Methodi ENCODLAB BASA BILAB SOLISHTIRADI.
    // AGAR TÖGRI BÖLSA USER ENTITY DAGI 4 BOOLEN FIELD/GA NISBATAN FALSE EMASLIGINI TEKSHIRADI.AGAR BIRORTASI FALSE BÖSA USER SYSTEMAGA KIROLMAYDI
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
        loginDto.getUsername(), loginDto.getPassword()));

    // HAMMASI YAXSHI BÖLSA,SHU YERGA TUSHADI,VA TOKEN GENERATION QILAMIZ
    // UserDetails dagi User ni beradi...Shu User ning Role sini olamiz.
        User user =(User)authentication.getPrincipal();

    // USERNAME NI ROLE B-N BIRGA TOKEN QILIB QAYTARAMIZ;KEYINGI SAFAR User SHU TOKEN BILAN LOGIN QILADI YOKI YOPIQ YÖL/GA MUROJAAT QILADI:
        String token = jwtProvider.generateToken(loginDto.getUsername(), user.getRoles());
        return new ApiResponse("Token",true, token);

    }catch (BadCredentialsException  badCredentialsException){
        return new ApiResponse("Parol yoki login xato",false);
    }
 }*/
