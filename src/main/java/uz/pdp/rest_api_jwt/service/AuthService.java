package uz.pdp.rest_api_jwt.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.rest_api_jwt.repository.EmployeeRepository;


// BU CLASS NING loadUserByUsername @Override b-ln METHODI UserName(UserDetails) QAYTARADI, SHU SABABLI CLASS HAM UserName QAYTARADI.
@Service
public class AuthService implements UserDetailsService {

    @Autowired
    EmployeeRepository employeeRepository;

    // Login BÖLGANDA USERNI(TAKRORLANMAS EMAIL) DB DAN QIDIRISH
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return employeeRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username+"topilmadi"));
    }
}
