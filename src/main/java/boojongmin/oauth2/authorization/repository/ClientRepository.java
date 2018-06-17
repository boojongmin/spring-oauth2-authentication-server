package boojongmin.oauth2.authorization.repository;

import boojongmin.oauth2.authorization.entity.Client;
import boojongmin.oauth2.authorization.entity.Member;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Long> {
    Client findByClientId(String clientId);
}
