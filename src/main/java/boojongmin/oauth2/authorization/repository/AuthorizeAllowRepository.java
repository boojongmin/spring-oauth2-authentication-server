package boojongmin.oauth2.authorization.repository;

import boojongmin.oauth2.authorization.entity.AuthorizeAllow;
import boojongmin.oauth2.authorization.entity.Client;
import boojongmin.oauth2.authorization.entity.Member;
import org.springframework.data.repository.CrudRepository;

public interface AuthorizeAllowRepository extends CrudRepository<AuthorizeAllow, Long> {
    AuthorizeAllow findByMemberAndClient(Member member, Client client);
}
