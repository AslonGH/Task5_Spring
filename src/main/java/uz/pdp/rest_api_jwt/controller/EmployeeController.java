
package uz.pdp.rest_api_jwt.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.rest_api_jwt.payload.LoginDto;
import uz.pdp.rest_api_jwt.payload.RegisterDto;
import uz.pdp.rest_api_jwt.repository.EmployeeRepository;
import uz.pdp.rest_api_jwt.repository.RoleRepository;
import uz.pdp.rest_api_jwt.service.ApiResponse;
import uz.pdp.rest_api_jwt.service.EmployeeService;
import java.util.UUID;


@RestController
@RequestMapping("/api/auth")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    RoleRepository roleRepository;

    @PreAuthorize(value = "hasRole('HR_MANAGER')")
    @PostMapping("/register/employee")
    public HttpEntity<?> registerEmployee(@RequestBody RegisterDto registerDto) {
        ApiResponse apiResponse = employeeService.registerEmployee(registerDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @PostMapping(value = "/login/employee")
    public HttpEntity<?> loginEmployee(@RequestBody LoginDto loginDto) {
        ApiResponse apiResponse = employeeService.loginEmployee(loginDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 401).body(apiResponse);
    }

    // EMAILDA TASDIQLASH LINK BOSILGADA SHU METHOD ISHLAYDI VA LINK ICHIDAN email va emailCode ni ajratib oladi.
    @PostMapping(value = "/verifyEmail/employee")
    public HttpEntity<?> employeeVerifyEmail(@RequestParam String emailCode, @RequestParam String email, @RequestParam String password) {
        ApiResponse apiResponse = employeeService.employeeVerifyEmail(emailCode, email, password);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    // XODIMLAR RÖYXATI KÖRINIB TURISHI KERAK.
    @PreAuthorize(value = "hasAnyRole('DIRECTOR','HRMANAGER')")
    @GetMapping("/getEmployees")
    public HttpEntity<?> getEmployees() {
        return ResponseEntity.ok(employeeRepository.findAll());
    }


    // Xodimning berilgan vaqt böyicha ishga kelib ketishi ;       ISHLADI
    // @PreAuthorize(value = "hasAnyRole('DIRECTOR','HRMANAGER')")
    @GetMapping("/{id}/{date1}/{date2}")
    public HttpEntity<?> getEmployeeById(@PathVariable UUID id, @PathVariable String date1, @PathVariable String date2) { // Yoki yana 2 ta localdate
        ApiResponse apiResponse = employeeService.getEmployeeByIdTimeStatus(id, date1, date2);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }



    //  Xodimning berilgan vaqt böyicha bajargan task/i             ISHLADI
    //  @PreAuthorize(value = "hasAnyRole('DIRECTOR','HRMANAGER')")
    @GetMapping("/{id}/{localDate1}/{localDate2}")
    public HttpEntity<?> getEmployeeById1(@PathVariable UUID id, @PathVariable String localDate1, @PathVariable String localDate2) { // Yoki yana 2 ta localdate
        ApiResponse apiResponse = employeeService.getEmployeeByIdTimeStatus1(id, localDate1, localDate2);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

}





/*      Set<Role> roleSet=new HashSet<>();
        roleSet.add(roleRepository.findByRoleName(EMPLOYEE));
        roleSet.add(roleRepository.findByRoleName(MANAGER));
        return ResponseEntity.ok(employeeRepository.findByRoles(roleSet));
        ApiResponse apiResponse1 = employeeService.getEmployees();
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
*/
