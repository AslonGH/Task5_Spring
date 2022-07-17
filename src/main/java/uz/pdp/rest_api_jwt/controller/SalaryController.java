package uz.pdp.rest_api_jwt.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.rest_api_jwt.payload.SalaryDto;
import uz.pdp.rest_api_jwt.repository.SalaryRepository;
import uz.pdp.rest_api_jwt.service.ApiResponse;
import uz.pdp.rest_api_jwt.service.SalaryService;
import java.util.UUID;

@RestController
@RequestMapping("/api/salary")
public class SalaryController {


    @Autowired
    SalaryRepository salaryRepository;

    @Autowired
    SalaryService salaryService;


    @PreAuthorize(value ="hasAnyRole('HRMANAGER','DIRECTOR')")
    @GetMapping
    public HttpEntity<?>getSalary(){
        return ResponseEntity.ok(salaryRepository.findAll());
    }


    @PreAuthorize(value ="hasAnyRole('DIRECTOR','HRMANAGER')")
    @GetMapping("/{id}")
    public HttpEntity<?>getSalary(@PathVariable UUID id){
        ApiResponse apiResponse = salaryService.getSalary(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
        //     return ResponseEntity.status(optionalSalary.isPresent()?200:404).body(optionalSalary.orElse(null));
    }

    @PreAuthorize(value ="hasAnyRole('DIRECTOR','HRMANAGER')")
    @GetMapping("/salaries/{byEmployeeId}")
    public HttpEntity<?>getSalaryByEmployeeId(@PathVariable UUID byEmployeeId){
        ApiResponse apiResponse = salaryService.getSalaryByEmployeeId(byEmployeeId);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
        // return ResponseEntity.status(optionalSalary.isPresent()?200:404).body(optionalSalary.orElse(null));
    }

    // ISHLADI
      @PreAuthorize(value ="hasAnyRole('DIRECTOR','HRMANAGER')")
    @GetMapping("/employeeSalary/{month}")
    public HttpEntity<?>getSalaryByMonth(@PathVariable String month){
        ApiResponse apiResponse = salaryService.getSalaryByMonth(month);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
        //     return ResponseEntity.status(optionalSalary.isPresent()?200:404).body(optionalSalary.orElse(null));
    }


    @PreAuthorize(value ="hasRole('DIRECTOR')")
    @PostMapping
    public HttpEntity<?>addSalary(@RequestBody SalaryDto salaryDto){
        ApiResponse apiResponse = salaryService.addSalary(salaryDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }



    @PreAuthorize(value ="hasRole('DIRECTOR')")
    @PutMapping("/{id}")
    public HttpEntity<?>editSalary(@PathVariable UUID id, @RequestBody SalaryDto salaryDto){
        ApiResponse apiResponse = salaryService.editSalary(id,salaryDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }


    @PreAuthorize(value ="hasRole('DIRECTOR')")
    @DeleteMapping("/{id}")
    public HttpEntity<?>deleteSalary(@PathVariable UUID id){
        ApiResponse apiResponse = salaryService.deleteSalary(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }


}
