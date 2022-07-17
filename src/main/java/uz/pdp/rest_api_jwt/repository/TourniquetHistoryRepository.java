package uz.pdp.rest_api_jwt.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.rest_api_jwt.entity.TourniquetHistory;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface TourniquetHistoryRepository extends JpaRepository<TourniquetHistory, UUID> {


    // BERILGAN VAQT ORALIGIDA ISHGA KELIB KETISHI


    List<TourniquetHistory> findAllByCard_EmployeeIdAndGetOutTimeBetween(UUID card_employee_id, Timestamp getOutTime, Timestamp getOutTime2);

    List<TourniquetHistory> findAllByCard_EmployeeIdAndGetInTimeBetween(UUID card_employee_id, Timestamp getInTime, Timestamp getInTime2);

}
