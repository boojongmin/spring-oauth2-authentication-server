package boojongmin.oauth2.authorization.repository;

import boojongmin.oauth2.authorization.entity.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, Long> {
    Member findByEmail(String email);
}
