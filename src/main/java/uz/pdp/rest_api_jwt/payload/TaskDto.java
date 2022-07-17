package uz.pdp.rest_api_jwt.payload;

import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class TaskDto {

    private  String    taskName;

    private  String    description;

    private LocalDate deadLine;

    // EDIT QILGANDA YOZILADI
    private LocalDate  completedAt;

    private Integer    status;

    private  UUID      employeeId;


    // Jönatishdan oldin true bölishi kerak
    private boolean    enabled;

    private  String    taskCode;

}
