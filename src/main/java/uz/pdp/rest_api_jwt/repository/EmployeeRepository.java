package uz.pdp.rest_api_jwt.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.rest_api_jwt.entity.Employee;
import uz.pdp.rest_api_jwt.entity.Role;
import uz.pdp.rest_api_jwt.entity.enums.RoleName;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    boolean existsByEmail(String email);

    // OPTIONAL ISHLATAMIZ CHUNKI BIZGA User TIPIDAGI OBJECT QAYTARSIN. Parametr/ni örni muhim
    Optional<Employee>findByEmailCodeAndEmail(String emailCode, String email);

    Optional<Employee> findByEmail(String email);

    Optional<Employee> findByEmailNot(String email);

  //  List<Employee> findByRoles(Set<Role> roles);

}
