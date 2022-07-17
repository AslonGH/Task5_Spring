
/*
package uz.pdp.rest_api_jwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.rest_api_jwt.entity.Employee;
import uz.pdp.rest_api_jwt.entity.Task;
import uz.pdp.rest_api_jwt.entity.User;
import uz.pdp.rest_api_jwt.payload.TaskDto;
import uz.pdp.rest_api_jwt.repository.*;
import uz.pdp.rest_api_jwt.security.JwtProvider;

import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    SalaryRepository salaryRepository;

    @Autowired
    ManagerService managerService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Autowired
    JavaMailSender javaMailSender;


    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;


    public ApiResponse addTask(TaskDto taskDto){

         Task task=new Task();

        task.setName(taskDto.getName());
        task.setDescription(taskDto.getDescription());
        task.setDeadLine(taskDto.getDeadLine());
        task.setStatus(task.getStatus());
        task.setTaskCode(taskDto.getTaskCode());

        if (taskDto.getEmployeeId()!=null) {
            Optional<Employee> optionalEmployee = employeeRepository.findById(taskDto.getEmployeeId());
            if (!optionalEmployee.isPresent())
                return new ApiResponse("Employee topilmadi", false);
            Employee employee = optionalEmployee.get();
            task.setEmployee(employee);
            sendEMail(employee.getEmail());

       //   Set<Task> tasks = employee.getTask();
       //   tasks.add(task);
        }
*/
/*
         if (taskDto.getManagerId()!=null){
             Optional<Manager> optionalManager = managerRepository.findById(taskDto.getManagerId());
         if (optionalManager.isPresent())
             return new ApiResponse("Manager topilmadi", false);
             Manager manager = optionalManager.get();
             task.setManager(manager);
             sendEMail(manager.getEmail());
             Set<Task> tasks = manager.getTask();
             tasks.add(task);
        }
*/
/*

        taskRepository.save(task);
        return new ApiResponse("Muvaffaqiyatli saqlandi",true);
    }

    public ApiResponse editTask(UUID id, TaskDto taskDto) {

        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent())
            return new ApiResponse("Task topilmadi", false);
        Task task = optionalTask.get();
        task.setName(taskDto.getName());
        task.setDescription(taskDto.getDescription());
        task.setDeadLine(taskDto.getDeadLine());
        task.setTaskCode(taskDto.getTaskCode());

        taskRepository.save(task);

        if (taskDto.getEmployeeId() != null) {
            Optional<Employee> optionalEmployee = employeeRepository.findById(taskDto.getEmployeeId());
            if (!optionalEmployee.isPresent())
                return new ApiResponse("Employee topilmadi", false);

            Employee employee = optionalEmployee.get();
            task.setEmployee(employee);
            sendEMail(employee.getEmail());
        }
        if (taskDto.getEmployeeId() != null) {
            Optional<User> optionalUser = userRepository.findById(taskDto.getEmployeeId());
            if (!optionalUser.isPresent())
                return new ApiResponse("Employee topilmadi", false);

            User user = optionalUser.get();
          //  task.setEmployee(optionalUser.get());
            sendEMail(user.getEmail());
        }

     */
/*    Set<Task> tasks = employee.getTask();
           tasks.add(task);
    }
      if (taskDto.getManagerId()!=null){
            Optional<Manager> optionalManager = managerRepository.findById(taskDto.getManagerId());
            if (optionalManager.isPresent())
                return new ApiResponse("Manager topilmadi", false);
            Manager manager = optionalManager.get();
            task.setManager(manager);
            sendEMail(manager.getEmail());
            Set<Task> tasks = manager.getTask();
            tasks.add(task);
        }
        if (taskDto.isCompleted()){
            UUID updatedBy = task.getUpdatedBy();

            Optional<Manager> optionalManager = managerRepository.findById(updatedBy);
            if (optionalManager.isPresent()){
                Manager manager = optionalManager.get();
                sendEMail1(manager.getEmail());
            }
            Optional<Director> optionalDirector = directorRepository.findById(updatedBy);
            if (optionalDirector.isPresent()){
                Director director = optionalDirector.get();
                sendEMail1(director.getEmail());
            }
        }*/
/*


        return new ApiResponse("Muvaffaqiyatli saqlandi",true);
    }

    public ApiResponse getTask(UUID id) {

        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent()){
        return new ApiResponse("Task topilmadi",false);}
        return new ApiResponse("Task topildi", true, optionalTask.get());
    }

    public ApiResponse deleteTask(UUID id) {

        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent())
            try {
                taskRepository.deleteById(id);
                return new ApiResponse("Task öchirildi", true);
            } catch (Exception e) {
                return new ApiResponse("Task topilmadi", false);
            }
        return new ApiResponse("Task öchirilmadi", false);
    }


    public Boolean sendEMail(String sendingEmail){
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("aslon.dinov@gmail.com");
            mailMessage.setTo(sendingEmail);
            mailMessage.setSubject("NEW TASK");
            mailMessage.setText("Sizga yangi Task berildi");
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
*/
/*        // BU YOKI TEPADAGI QAYSI QOLADI.Employe qosa yaxshi
        if (taskDto.getEmployeeId() != null) {
            Optional<Employee> optionalUser = employeeRepository.findById(taskDto.getEmployeeId());
            if (!optionalUser.isPresent())
                return new ApiResponse("Employee topilmadi", false);

            Employee user = optionalUser.get();
         //   task.setEmployee(optionalUser.get());
            sendEMail(user.getEmail());
        }
        */
/*
        // BU YOKI TEPADAGI QAYSI QOLADI.
        if (taskDto.getEmployeeId() != null) {
            Optional<Employee> optionalUser = employeeRepository.findById(taskDto.getEmployeeId());
            if (!optionalUser.isPresent())
                return new ApiResponse("Employee topilmadi", false);
            Employee user = optionalUser.get();
          //  task.setEmployee(optionalUser.get());
            sendEMail(user.getEmail());
        }
*/
/*
        // BU YOKI TEPADAGI QAYSI QOLADI. Employe qosa yaxshi
        if (taskDto.getEmployeeId() != null) {
            Optional<User> optionalUser = userRepository.findById(taskDto.getEmployeeId());
            if (!optionalUser.isPresent())
                return new ApiResponse("Employee topilmadi", false);

            User user = optionalUser.get();
            //  task.setEmployee(optionalUser.get());
            sendEMail(user.getEmail());
        }*/
/*
           }
      if (taskDto.getManagerId()!=null){
            Optional<Manager> optionalManager = managerRepository.findById(taskDto.getManagerId());
            if (optionalManager.isPresent())
                return new ApiResponse("Manager topilmadi", false);
            Manager manager = optionalManager.get();
            task.setManager(manager);
            sendEMail(manager.getEmail());
            Set<Task> tasks = manager.getTask();
            tasks.add(task);
        }
        if (taskDto.isCompleted()){
            UUID updatedBy = task.getUpdatedBy();

            Optional<Manager> optionalManager = managerRepository.findById(updatedBy);
            if (optionalManager.isPresent()){
                Manager manager = optionalManager.get();
                sendEMail1(manager.getEmail());
            }
            Optional<Director> optionalDirector = directorRepository.findById(updatedBy);
            if (optionalDirector.isPresent()){
                Director director = optionalDirector.get();
                sendEMail1(director.getEmail());
            }
        }*/


/*
    private boolean isTaskMade;

    //vazifa bajarilganligi haqida vazifa qoygan manager yoki direktorning emailiga xabar boradi.
    @OneToOne
    private TurnKet turnKet;

    @OneToOne
    private Salary salary;


    @ManyToOne
    private Manager managerId;      //????  Vazifa biriktirilgan xodimning email manziliga xabar jo’natiladi.

    @ManyToOne
    private Director directorId;    //????  Vazifa biriktirilgan xodimning email manziliga xabar jo’natiladi.*/