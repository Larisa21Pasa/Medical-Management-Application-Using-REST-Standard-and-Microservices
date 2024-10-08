package com.projectmedicine.auth.UserComponent.model.repository;

import com.projectmedicine.auth.UserComponent.model.database.idm.TokenTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<TokenTable, Integer> {

    @Query(value = """
      select t from Token t inner join User u\s
      on t.userTable.userId = u.userId\s
      where u.userId = :id and (t.expired = false or t.revoked = false)\s
      """)
    List<TokenTable> findAllValidTokenByUser(Integer id);

    Optional<TokenTable> findByToken(String token);
}