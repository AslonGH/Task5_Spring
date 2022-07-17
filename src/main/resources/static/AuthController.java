/*
package uz.pdp.rest_api_jwt.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.rest_api_jwt.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

     @Autowired
     AuthService authService;

*/
/*
     @PostMapping(value = "/register")
     public HttpEntity<?>registerUser(@RequestBody RegisterDto registerDto){
        ApiResponse apiResponse = authService.registerUser(registerDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
     }

     // EMAILDA TASDIQLASH LINK BOSILGADA SHU METHOD ISHLAYDI VA LINK ICHIDAN email va emailCode ni ajratib oladi.
     @GetMapping( value = "/verifyEmail")
     public HttpEntity<?>verifyEmail(@RequestParam String emailCode, @RequestParam String email){
         ApiResponse apiResponse=authService.verifyEmail(emailCode,email);
         return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
     }

     @PostMapping(value = "/login")
     public HttpEntity<?>login(@RequestBody LoginDto loginDto){
         ApiResponse apiResponse=authService.login(loginDto);
         return ResponseEntity.status(apiResponse.isSuccess()?200:401).body(apiResponse);
     }
*//*


}
*/
