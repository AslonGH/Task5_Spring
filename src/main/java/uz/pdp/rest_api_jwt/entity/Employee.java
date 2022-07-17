package uz.pdp.rest_api_jwt.entity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

    @Entity
    @EntityListeners(AuditingEntityListener.class)  // KIM TOMINIDAN BAJARILGANLIGINI BILISH UCHUN
    public class Employee implements UserDetails {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, length = 50)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

   // @Column(nullable = false, unique = true)
    private String email;

   // @Column(nullable = false)
    private String password;


    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;


    @OneToOne
    private Company company;


    // OYLIK BELGILANADI
    private  Double  salary;


    @CreatedBy
    private UUID createdBy;

    @LastModifiedBy
    private UUID updatedBy;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;


        private boolean accountNonExpired=true;

        private boolean accountNonLocked=true;

        private boolean credentialsNonExpired=true;

        private boolean enabled;

        private String  emailCode;



        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return this.roles;
        }

        @Override
        public String getUsername() {
            return this.email;
        }

        @Override
        public boolean isAccountNonExpired() {
            return this.accountNonExpired;
        }

        @Override
        public boolean isAccountNonLocked() {
            return this.accountNonLocked;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return this.credentialsNonExpired;
        }

        @Override
        public boolean isEnabled() {
            return this.enabled;
        }


}
