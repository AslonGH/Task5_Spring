package uz.pdp.rest_api_jwt.payload;

import lombok.Data;
import uz.pdp.rest_api_jwt.entity.Company;
import uz.pdp.rest_api_jwt.entity.Employee;

import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Data
public class TourniquetCardDto {


    private LocalDate expireDate;

    @OneToOne
    private UUID      employeeId;

    @ManyToOne
    private UUID      companyId;

}
