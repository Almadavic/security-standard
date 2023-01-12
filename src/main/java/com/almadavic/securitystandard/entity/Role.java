package com.almadavic.securitystandard.entity;


import com.almadavic.securitystandard.enumerated.RoleName;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity             // Indica que será uma tabela do banco de dados.
@Table(name = "tb_roles")    // nome da tabela do banco de dados, será uma tabela de roles.
@NoArgsConstructor // No caso,não está sendo usado pelo programador mas sim pelo spring de baixo dos panos.
@Builder       // Cria um builder baseado no  construtor da linha de baixo.
@AllArgsConstructor // No caso, só haveria 1 atributo no construtor, o name pq n tem o final.
@Getter              // Cria os getters dos atributos
@Setter               // Cria os setters dos atributos
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // HashCode and equals apenas com os atributos marcados com @Include.
public class Role implements GrantedAuthority { // A classe que representam os perfis, roles, permissões de cada Usuário!

    @Id  // Indica que será a PRIMARY-KEY
    @EqualsAndHashCode.Include // O hashCode irá usar esse atributo para destinguir um objeto de outro.
    @Column(name = "ID", nullable = false, unique = true)
    // name -> nome do campo , nullable -> se pode ser nullo, unique -> campo não pode ser repetido
    private final String id = UUID.randomUUID().toString(); // Representa qual atributo será essa PRIMARY-KEY e a tipagem, no Caso será um UUID devido a segurança..

    @Column(name = "NAME", nullable = false, unique = true)
    // name -> nome do campo , nullable -> se pode ser nullo, unique -> campo não pode ser repetido
    @Getter(AccessLevel.NONE)
    @Enumerated(EnumType.STRING)
    private RoleName name; // Representa o nome da role.

    @ManyToMany(mappedBy = "roles")
    private final List<User> users = new ArrayList<>(); // Um perfil tem uma lista de usuários associados a ele.

    @Override
    public String getAuthority() {
        return name.toString();
    }

    @Override
    public String toString() {
        return "Id: " + id + ", Name: " + getAuthority();
    }

}