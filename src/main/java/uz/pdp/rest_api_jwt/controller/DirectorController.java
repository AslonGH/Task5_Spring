package uz.pdp.rest_api_jwt.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.rest_api_jwt.payload.LoginDto;
import uz.pdp.rest_api_jwt.payload.RegisterDto;
import uz.pdp.rest_api_jwt.service.ApiResponse;
import uz.pdp.rest_api_jwt.service.DirectorService;

@RestController
@RequestMapping("/api/auth")
public class DirectorController {

     @Autowired
     DirectorService directorService;


     @PostMapping("/register/director")
     public HttpEntity<?>registerDirector(@RequestBody RegisterDto registerDto){
        ApiResponse apiResponse = directorService.registerDirector(registerDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
     }


     @PostMapping("/login/director")
     public HttpEntity<?>loginDirector(@RequestBody LoginDto loginDto){
        ApiResponse apiResponse=directorService.loginDirector(loginDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:401).body(apiResponse);
     }


     // EMAILDA TASDIQLASH LINK BOSILGADA SHU METHOD ISHLAYDI VA LINK ICHIDAN email va emailCode ni ajratib oladi.
     @GetMapping("/verifyEmail/director")
     public HttpEntity<?>employeeVerifyEmail(@RequestParam String emailCode, @RequestParam String email){
         ApiResponse apiResponse=directorService.directorVerifyEmail(emailCode,email);
         return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
     }

}










