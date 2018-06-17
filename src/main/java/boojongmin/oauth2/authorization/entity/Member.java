package boojongmin.oauth2.authorization.entity;

import boojongmin.oauth2.authorization.entity.enums.MemberStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;
    private MemberStatus status = MemberStatus.INACTIVE;
    private String roles;
    @OneToMany
    private List<Client> allowClients;
}
