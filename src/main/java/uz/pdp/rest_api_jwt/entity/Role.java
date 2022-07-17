package uz.pdp.rest_api_jwt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import uz.pdp.rest_api_jwt.entity.enums.RoleName;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Role implements GrantedAuthority {

    // Userlar soni chegaralanmagan, Role lar soni chegaralangan böladi shu sababli Integer qölladik
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // ENUM/NI CHAQIRAMIZ. Enum lar DB ga yozilsa, raqam bölib qoladi.shu sababli :  EnumType.STRING
    @Enumerated(EnumType.STRING)
    private RoleName roleName;

    @Override
    public String getAuthority() {
        return roleName.name();  //  String qaytaradi
    }

}
