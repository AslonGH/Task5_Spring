package uz.pdp.rest_api_jwt.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.rest_api_jwt.entity.Company;
import uz.pdp.rest_api_jwt.service.ApiResponse;
import uz.pdp.rest_api_jwt.service.CompanyService;
import java.util.UUID;

//  return ResponseEntity.ok(salaryRepository.findAll());
//  return ResponseEntity.status(optionalSalary.isPresent()?200:404).body(optionalSalary.orElse(null));

@RestController
@RequestMapping("/api/company")
public class CompanyController {


    @Autowired
    CompanyService companyService;


    @GetMapping
    public HttpEntity<?>getCompany(){
        ApiResponse apiResponse = companyService.getCompany();
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

   /*    //  @PreAuthorize(value ="hasAnyRole('DIRECTOR','HRMANAGER')")
        @GetMapping("/{id}")
        public HttpEntity<?>getSalary(@PathVariable UUID id){
        ApiResponse apiResponse = companyService.getCompanyById(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
      }
   */

    @PreAuthorize(value ="hasRole('DIRECTOR')")
    @PostMapping
    public HttpEntity<?>addCompany(@RequestBody Company company){
        ApiResponse apiResponse = companyService.addCompany(company);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }


    @PreAuthorize(value ="hasRole('DIRECTOR')")
    @PutMapping("/{id}")
    public HttpEntity<?>editSalary(@PathVariable UUID id, @RequestBody Company company){
        ApiResponse apiResponse = companyService.editCompany(id,company);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }


    @PreAuthorize(value ="hasRole('DIRECTOR')")
    @DeleteMapping("/{id}")
    public HttpEntity<?>deleteSalary(@PathVariable UUID id){
        ApiResponse apiResponse = companyService.deleteCompany(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

}
