package uz.pdp.rest_api_jwt.payload;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import uz.pdp.rest_api_jwt.entity.Company;

import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RegisterDto {


    @NotNull
    @Size(min = 3,max = 50)
    private String firstname;

    @NotNull
    @Length(min = 3,max = 50)
    private String lastname;

    @NotNull
    @Email
    private String email;


    private String password;

    private Company company;

    private  Double  salary;

}
