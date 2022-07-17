package uz.pdp.rest_api_jwt.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import uz.pdp.rest_api_jwt.entity.Employee;
import uz.pdp.rest_api_jwt.payload.TaskDto;
import uz.pdp.rest_api_jwt.repository.ManagerTaskRepository;
import uz.pdp.rest_api_jwt.service.ApiResponse;
import uz.pdp.rest_api_jwt.service.ManagerTaskService;

import java.util.*;


@RestController
@RequestMapping("/api/managerTask")
public class ManagerTaskController {

    @Autowired
    ManagerTaskRepository taskRepository;

    @Autowired
    ManagerTaskService taskService;



    // TIZIMGA KIRIB TURGAN EMPLOYEE UCHUN UNING TASK LARI .   ISHLADI
    @PreAuthorize(value ="hasAnyRole('MANAGER')")
    @GetMapping("/tasks")
    public HttpEntity<?>getTaskById(){
        Set<Integer> set=new HashSet<>(Arrays.asList(1,2));
        return  ResponseEntity.ok(taskRepository.findByEmployeeIdAndStatusIn(getManagerById(),set));
    }


    // TIZIM GA KIRIB TURGAN EMPLOYEE
    public UUID getManagerById(){
        Employee principal = (Employee) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getId();
    }


    @PreAuthorize(value ="hasAnyRole('DIRECTOR')")
    @GetMapping
    public HttpEntity<?>getTask(){
        return ResponseEntity.ok(taskRepository.findAllByEmployee_RolesId(3));   // TASKNING EMPLOYESININING ROLEID SI 3 BÖLSA
    }


    @PreAuthorize(value ="hasRole('DIRECTOR')")                 // TASKNING EMPLOYESININING ROLEID SI 3 BÖLSA
    @GetMapping("/{id}")
    public HttpEntity<?>getTask(@PathVariable UUID id){
        ApiResponse apiResponse = taskService.getTaskById(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }


    @PreAuthorize(value ="hasRole('DIRECTOR')")
    @PostMapping
    public HttpEntity<?>addTask(@RequestBody TaskDto taskDto){
        ApiResponse apiResponse = taskService.addTask(taskDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }


    @PreAuthorize(value ="hasAnyRole('DIRECTOR','MANAGER')")    // TASKNING EMPLOYESININING ROLEID SI 3 BÖLSA
    @PutMapping("/{id}")
    public HttpEntity<?>editTask(@PathVariable UUID id, @RequestBody TaskDto taskDto){
        ApiResponse apiResponse = taskService.editTask(id,taskDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }


    @PreAuthorize(value ="hasRole('DIRECTOR')")                // TASKNING EMPLOYESININING ROLEID SI 3 BÖLSA
    @DeleteMapping("/{id}")
    public HttpEntity<?>deleteTask(@PathVariable UUID id){
        ApiResponse apiResponse = taskService.deleteTask(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }


}

// return ResponseEntity.ok(employeeTaskService.getTaskByEmployeeId(getEmployeeById()));
// return  ResponseEntity.ok(taskRepository.findByEmployeeIdAndStatus(getManagerById(),2));
// XATOLIK BERSA AndStatus ÖRNIDA OR YOKI CONTAINS... Xatolik Bermadi