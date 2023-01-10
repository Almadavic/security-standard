package com.almadavic.securitystandard.repository;

import com.almadavic.securitystandard.entity.Role;
import com.almadavic.securitystandard.enumerated.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // Repositorio de Role ( No geral)
public interface RoleRepository extends JpaRepository<Role, String> {

    Optional<Role> findByName(RoleName name); // Método encontra uma role pelo nome

}
