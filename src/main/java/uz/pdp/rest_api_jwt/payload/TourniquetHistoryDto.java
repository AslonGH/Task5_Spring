package uz.pdp.rest_api_jwt.payload;
import lombok.Data;
import uz.pdp.rest_api_jwt.entity.TourniquetCard;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Data
public class TourniquetHistoryDto {

    private  UUID       cardId;

    private  String  getInTime;

    private  String  getOutTime;

}
