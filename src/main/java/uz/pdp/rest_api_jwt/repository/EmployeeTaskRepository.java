package uz.pdp.rest_api_jwt.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.rest_api_jwt.entity.Role;
import uz.pdp.rest_api_jwt.entity.Task;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface EmployeeTaskRepository extends JpaRepository<Task, UUID> {


    List<Task>findAllByEmployee_RolesId(Integer employee_roles_id);

    Optional<Task> findByIdAndEmployeeRolesId(UUID id, Integer employee_roles_id);

    // TASKNI YANGILARI, jarayondagi  CHIQISHI KERAK
    List<Task> findByEmployeeIdAndStatusIn(UUID employee_id, Set<Integer> status);

    // IKKITA VAQT ORALIGIDA BAJARILGAN TASK/
    List<Task>findAllByCompletedAtBetweenAndEmployeeIdAndStatus
    (LocalDate completedAt, LocalDate completedAt2, UUID employee_id,Integer status);



  //  TASKNI berilgan id ga tegishli YANGILARI  jarayondagi  CHIQISHI KERAK
  //  List<Task> findByEmployeeIdAndStatusAndStatusAndStatus
  //  (UUID employee_id, Integer status, Integer status2, Integer status3);

}
