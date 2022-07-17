package uz.pdp.rest_api_jwt.payload;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class SalaryDto {

    private  LocalDate  localDate;
    private  String    month;
    private  UUID      employeeId;
    private  String  verifyingCode;

}
