package boojongmin.oauth2.authorization.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorizeAllow {
    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @ManyToOne
    private Member member;
    @ManyToOne
    private Client client;
    private String scope;

}

