package com.almadavic.securitystandard.repository;

import com.almadavic.securitystandard.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    Optional<User> findByEmail(String email); // Método que retorna um User passando o email. // Método usado para autenticação.

    Optional<User> findByNickname(String nickname); // Método retorna um usuario pelo nickname.

    @Query(value = "SELECT u.id, u.username, u.email, u.password, u.registration_moment FROM tb_users U " +
            "INNER JOIN tb_users_roles UR ON U.id = UR.user_id " +
            "INNER JOIN tb_roles R ON UR.role_id = R.id WHERE r.name = :roleName", nativeQuery = true)
    Page<User> findByRole(Pageable pageable, String roleName); // Query criada para retornar a página com usuários que tenham o nome da role como informado.

}
