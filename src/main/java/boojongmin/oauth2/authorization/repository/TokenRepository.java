package boojongmin.oauth2.authorization.repository;

import boojongmin.oauth2.authorization.entity.Client;
import boojongmin.oauth2.authorization.entity.Token;
import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<Token, Long> {
}
