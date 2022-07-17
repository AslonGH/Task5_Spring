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
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)  // KIM TOMINIDAN BAJARILGANLIGINI BILISH UCHUN
public class TourniquetCard {

    @Id
    @GeneratedValue
    private UUID id;

    private LocalDate  expireDate;

    private Integer    status;

    @OneToOne
    private Employee   employee;

    @ManyToOne
    private Company    company;


    @CreatedBy
    private UUID    createdBy;    // KIM QÖSHGANLIGI

    @LastModifiedBy
    private UUID    updatedBy;     // KIM TAHRIRLAGANLIGI

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;
}

