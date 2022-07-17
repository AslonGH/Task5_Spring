package uz.pdp.rest_api_jwt.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.rest_api_jwt.payload.LoginDto;
import uz.pdp.rest_api_jwt.payload.RegisterDto;
import uz.pdp.rest_api_jwt.service.ApiResponse;
import uz.pdp.rest_api_jwt.service.HRManagerService;

@RestController
@RequestMapping("/api/auth")
public class HRManagerController {

     @Autowired
     HRManagerService hrManagerService;


    @PostMapping("/register/hrManager1")
    public HttpEntity<?>registerHRManager1(@RequestBody RegisterDto registerDto){
        ApiResponse apiResponse = hrManagerService.registerHRManager1(registerDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }


    @PreAuthorize(value ="hasRole('DIRECTOR')")
    @PostMapping("/register/hrManager")
    public HttpEntity<?>registerHRManager(@RequestBody RegisterDto registerDto){
        ApiResponse apiResponse = hrManagerService.registerHRManager(registerDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }


    @PostMapping("/login/hrManager")
    public HttpEntity<?>loginHRManager(@RequestBody LoginDto loginDto){
        ApiResponse apiResponse=hrManagerService.loginHRManager(loginDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:401).body(apiResponse);
    }

    // EMAILDA TASDIQLASH LINK BOSILGADA SHU METHOD ISHLAYDI VA LINK ICHIDAN email va emailCode ni ajratib oladi.
    @PostMapping("/verifyEmail/hrManager")
    public HttpEntity<?>hrManagerVerifyEmail(@RequestParam String emailCode, @RequestParam String email,@RequestParam String password){
        ApiResponse apiResponse=hrManagerService.HRManagerVerifyEmail(emailCode,email,password);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

}







