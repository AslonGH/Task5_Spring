package uz.pdp.rest_api_jwt.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.web.servlet.view.RedirectView;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
// KIM TOMINIDAN BAJARILGANLIGINI BILISH UCHUN. Token b-n login qilsa chiqadi
@EntityListeners(AuditingEntityListener.class)
public class Task {
    @Id
    @GeneratedValue
    private UUID        id;

    private String      taskName;

    private String      description;

    private  LocalDate  deadLine;

    private  LocalDate  completedAt;

    private  Integer    status;

    private boolean     enabled;

    @ManyToOne
    private Employee    employee;


    private  String    taskCode;

    @CreatedBy
    private UUID      createdBy;

    @LastModifiedBy
    private UUID      updatedBy;

    @CreationTimestamp
    private Timestamp  createdAt;

    @UpdateTimestamp
    private Timestamp  updatedAt;
}
