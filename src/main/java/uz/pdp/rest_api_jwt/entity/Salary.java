package uz.pdp.rest_api_jwt.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)   // KIM TOMINIDAN BAJARILGANLIGINI BILISH UCHUN
public class Salary {

    @Id
    @GeneratedValue
    private UUID id;

    private  String  month;

    private  String  verifyingCode;

    //  qachon berildi
    private LocalDate  localDate;

    @ManyToOne
    private Employee  employee;


    @CreatedBy
    private UUID  createdBy;

    @LastModifiedBy
    private UUID  updatedBy;

    @CreationTimestamp
    private Timestamp  createdAt;

    @UpdateTimestamp
    private Timestamp  updatedAt;

}
