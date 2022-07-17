
    /*
package uz.pdp.rest_api_jwt.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, length = 50)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false, unique = true)
    private String email;

   // @Column(nullable = false)
    private String password;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;


    @UpdateTimestamp
    private Timestamp updateAt;


    private boolean accountNonExpired=true;

    private boolean accountNonLocked=true;

    private boolean credentialsNonExpired=true;

    private boolean enabled;

    private String  emailCode;


    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    // USERNING UserName ini qaytaruvchi Method
    @Override
    public String getUsername() {
        return this.email;  // email  properties ni bog'ladik
    }

    // ACCOUNTNING AMAL QILISH MUDDATI NI QAYTARADI
    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    // ACCOUNT BLOKLANGANLIGI HOLATINI QAYTARADI
    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    // ACCOUNTNING ISHONCHLILIK HOLATINI QAYTARADI
    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    // ACCOUNT NG YONIQ YOKI ÖCHIQLILIGI QAYTARADI
    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

}
*/
    /*private String address;

    @OneToOne
    private Director director;

    @OneToMany
    private Set<Manager> manager;

    @OneToOne
    private HRManager hrManager;

    @OneToMany
    private Set<Employee> employee;*/
    /*
    private boolean    isGetIn;
    private boolean    isGetOut;
    private boolean status;

    @ManyToOne
    private Company company;

    @OneToOne
    private Employee employee;

    private Date  expireDate;

    @CreatedBy
    private UUID    createdBy;  // KIM QÖSHGANLIGI

    @LastModifiedBy
    private UUID   updatedBy;  // KIM TAHRIRLAGANLIGI

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;
    */


/*    private boolean    isGetIn;

      private Timestamp  getInTime;

      private boolean    isGetOut;

      private Timestamp  getOutTime;*/