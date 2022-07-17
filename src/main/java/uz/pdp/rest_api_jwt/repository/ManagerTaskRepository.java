package uz.pdp.rest_api_jwt.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.rest_api_jwt.entity.Task;

import java.time.LocalDate;
import java.util.*;

@Repository
public interface ManagerTaskRepository extends JpaRepository<Task, UUID> {


    // TASKNI YANGILARI, jarayondagi  CHIQISHI KERAK
    List<Task> findByEmployeeIdAndStatusIn(UUID employee_id, Set<Integer> status);

    // TASKNING EMPLOYESININING ROLEID SI 3 BÖLSA
    List<Task>findAllByEmployee_RolesId(Integer employee_roles_id);

    // TASKNING EMPLOYESININING ROLE ID SI 3 BÖLGAN  VA Task NING ID SI ORQALI
    Optional<Task> findByIdAndEmployeeRolesId(UUID id, Integer employee_roles_id);

}
