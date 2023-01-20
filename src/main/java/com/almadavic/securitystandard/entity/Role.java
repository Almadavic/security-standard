package com.almadavic.securitystandard.entity;


import com.almadavic.securitystandard.enumerated.RoleName;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity  // Indica que será uma tabela do banco de dados.
@Table(name = "tb_roles")    // nome da tabela do banco de dados, será uma tabela de roles.
@NoArgsConstructor // No caso, não está sendo usado pelo programador mas sim pelo JPA debaixo dos panos.
@Builder  // Cria um builder baseado no  construtor da linha de baixo.
@AllArgsConstructor // No caso, só haveria 1 atributo no construtor, o name, pois não tem o final antes do atributo.
@Getter   // Cria os getters dos atributos
@Setter     // Cria os setters dos atributos
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // HashCode and equals apenas com os atributos marcados com @Include.
public class Role implements GrantedAuthority { // A classe que representam os perfis, roles, permissões de cada Usuário!

    @Id  // Indica que será a PRIMARY-KEY
    @EqualsAndHashCode.Include // O hashCode irá usar esse atributo para destinguir um objeto de outro.
    @Column(name = "ID", nullable = false, unique = true)
    // name -> nome da coluna, nullable -> se pode ser nullo, unique -> o valor do campo não pode ser repetido
    private final String id = UUID.randomUUID().toString(); // Representa qual atributo será essa PRIMARY-KEY e a tipagem, no Caso será um UUID devido a segurança...

    @Column(name = "NAME", nullable = false, unique = true)
    @Getter(AccessLevel.NONE) // Não é necessário criar um getter para esse atributo, pois por padrão, já temos, devido a INTERFACE GRANTEDAUTHORITY.
    @Enumerated(EnumType.STRING) // Transforma um enum em uma String.
    private RoleName name; // Representa o nome da role.

    @ManyToMany(mappedBy = "roles") // Como está mapeado na outra classe.
    private final List<User> users = new ArrayList<>(); // Um perfil tem uma lista de usuários associados a ele.
    //O final foi usado pois não é interessante alterar a lista, e sim adicionar e remover os atributos.

    @Override
    public String getAuthority() {
        return name.toString();
    }

    @Override
    public String toString() {
        return "Id: " + id + ", Name: " + getAuthority();
    }

}