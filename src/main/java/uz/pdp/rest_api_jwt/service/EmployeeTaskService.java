package uz.pdp.rest_api_jwt.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import uz.pdp.rest_api_jwt.entity.Employee;
import uz.pdp.rest_api_jwt.entity.Role;
import uz.pdp.rest_api_jwt.entity.Task;
import uz.pdp.rest_api_jwt.payload.TaskDto;
import uz.pdp.rest_api_jwt.repository.*;

import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class EmployeeTaskService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeTaskRepository employeeTaskRepository;

    @Autowired
    JavaMailSender javaMailSender;


    public static final  Integer NEW =1;
    public static final  Integer AT_PROCESS =2;
    public static final  Integer DONE_ON_TIME=3;
    public static final  Integer LATE_DONE=4;


    public ApiResponse addTask(TaskDto taskDto){

         Task task=new Task();
        task.setTaskName(taskDto.getTaskName());
        task.setDescription(taskDto.getDescription());
        task.setTaskCode(taskDto.getTaskCode());
        task.setDeadLine(taskDto.getDeadLine());
        task.setStatus(NEW);

        //task.setTaskCode(taskDto.getTaskCode());
        if (taskDto.getEmployeeId()!=null)
        {
            Optional<Employee> optionalEmployee = employeeRepository.findById(taskDto.getEmployeeId());
            if (!optionalEmployee.isPresent())
            return new ApiResponse("Employee topilmadi", false);

            Employee employee = optionalEmployee.get();
            Set<Role> roles = employee.getRoles();
            for (Role role : roles) {
                if (!role.getRoleName().toString().equals("EMPLOYEE")){
                    return new ApiResponse("Boshqa Employee ID kiriting", false);
                }
            }
            task.setEmployee(employee);
            sendEMail(employee.getEmail());
            task.setEnabled(true);
            task.setStatus(AT_PROCESS);
        }
        employeeTaskRepository.save(task);
        return new ApiResponse("Muvaffaqiyatli saqlandi",true);
    }

    // "Muvaffaqiyatli özgartirildi"
    public ApiResponse editTask(UUID id, TaskDto taskDto){

        Optional<Task> optionalTask = employeeTaskRepository.findByIdAndEmployeeRolesId(id,4);
        if (!optionalTask.isPresent())
            return new ApiResponse("Task topilmadi", false);
         Task task = optionalTask.get();
        task.setTaskName(taskDto.getTaskName());
        task.setDescription(taskDto.getDescription());
        task.setTaskCode(taskDto.getTaskCode());
        task.setDeadLine(taskDto.getDeadLine());
        task.setCompletedAt(taskDto.getCompletedAt());

        if (task.getCompletedAt()!=null) {

            if (Period.between(task.getDeadLine(), task.getCompletedAt()).getDays() > 0) {
                task.setStatus(LATE_DONE);

                UUID createdBy = task.getCreatedBy();
                Optional<Employee> employeeOp = employeeRepository.findById(createdBy);
                if (employeeOp.isPresent()) {
                    Employee manager = employeeOp.get();
                    sendEMail1(manager.getEmail());
                }
            } else if (Period.between(task.getDeadLine(), task.getCompletedAt()).getDays() <= 0) {
                task.setStatus(DONE_ON_TIME);
                UUID createdBy = task.getCreatedBy();
                Optional<Employee> employeeOp = employeeRepository.findById(createdBy);
                if (employeeOp.isPresent()) {
                    Employee manager = employeeOp.get();
                    sendEMail1(manager.getEmail());
                }
            }
        }

        if (taskDto.getEmployeeId()!=null) {
            Optional<Employee> optionalEmployee = employeeRepository.findById(taskDto.getEmployeeId());
            if (!optionalEmployee.isPresent())
                return new ApiResponse("Employee topilmadi", false);

            Employee employee = optionalEmployee.get();
            task.setEmployee(employee);
            sendEMail(employee.getEmail());
        }
        employeeTaskRepository.save(task);
        return new ApiResponse("Muvaffaqiyatli özgartirildi",true);

    }

    public ApiResponse deleteTask(UUID id) {
        Optional<Task> optionalTask = employeeTaskRepository.findByIdAndEmployeeRolesId(id,4);
        if (optionalTask.isPresent()){
            try {
                employeeTaskRepository.deleteById(id);
                return new ApiResponse("Task öchirildi", true);
            } catch (Exception e) {
                return new ApiResponse("Task öchirilmadi", false);
            }}
        return new ApiResponse("Task topilmadi", false);
    }

    public Boolean sendEMail(String sendingEmail){
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("aslon.dinov@gmail.com");
            mailMessage.setTo(sendingEmail);
            mailMessage.setSubject("new task !!!");
            mailMessage.setText("you have new task");
            javaMailSender.send(mailMessage);
            return true;

        }catch (Exception e){
            return false;
        }
    }

    public Boolean sendEMail1(String sendingEmail){
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("aslon.dinov@gmail.com"); // JÖNATILADIGAN EMAIL(IXTIYORIY EMAILNI YOZSA BÖLADI)
            mailMessage.setTo(sendingEmail);
            mailMessage.setSubject("TASK");
            mailMessage.setText("Task bajarildi");
            javaMailSender.send(mailMessage);
            return true;

        }catch (Exception e){
            return false;
        }
    }

}


/*
 public ApiResponse getTaskByEmployeeId(UUID id){
 List<Task> tasks = employeeTaskRepository.findByEmployeeId(id);
 return new ApiResponse("Tasks", true, tasks);
 }
*/
// task.setTaskCode(taskDto.getTaskCode());
// task.setStatus(taskDto.getStatus());
// task.setBerilganVaqt(taskDto.getBerilganVaqt());
// if (task.getStatus()==3)
/*
        if (taskDto.getStatus().equals(DONE)) {
            UUID createdBy = task.getCreatedBy();
            Optional<Employee> employeeOp = employeeRepository.findById(createdBy);
            if (employeeOp.isPresent()) {
                Employee manager = employeeOp.get();
                sendEMail1(manager.getEmail());
            }
        }
*/