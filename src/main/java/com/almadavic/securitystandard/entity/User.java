package com.almadavic.securitystandard.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.userdetails.UserDetails;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity             // Indica que será uma tabela do banco de dados.
@Table(name = "tb_users")    // nome da tabela do banco de dados, será uma tabela de usuários.
@NoArgsConstructor // No caso,não está sendo usado pelo programador mas sim pelo spring de baixo dos panos.
@Getter              // Cria os getters dos atributos
@Setter               // Cria os setters dos atributos
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // HashCode and equals apenas com os atributos marcados com @Include.
public class User implements UserDetails {    // Classe  do banco - > User | Representa os usuários do sistema.

    @Id      // Indica que será a PRIMARY-KEY
    @EqualsAndHashCode.Include // O hashCode irá usar esse atributo para destinguir um objeto de outro.
    @Column(name = "ID", nullable = false, unique = true)
    // name -> nome do campo , nullable -> se pode ser nullo, unique -> campo não pode ser repetido
    private final String id = UUID.randomUUID().toString();
    // Representa qual atributo será essa PRIMARY-KEY e a tipagem , no Caso será um UUID devido a segurança.

    @Column(name = "USERNAME", nullable = false, unique = true)
    // name -> nome do campo , nullable -> se pode ser nullo, unique -> campo não pode ser repetido
    private String nickname; // Representa o nome do usuário.
    // pois já temos o metodo get padrão do atributo por causa do implements UserDetails.

    @Column(name = "EMAIL", nullable = false, unique = true)
    // name -> nome do campo , nullable -> se pode ser nullo, unique -> campo não pode ser repetido
    @Getter(AccessLevel.NONE)
    // Colocando o AccessLevel.NONE para dizer que não precisa criar o getter automaticamente do Lombok, pois já temos o metodo get padrão do atributo por conta do implements UserDetails.
    private String email;     // Representa o e-mail do usuário.

    @Column(name = "PASSWORD", nullable = false) // name -> nome do campo , nullable -> se pode ser nullo
    @Getter(AccessLevel.NONE)
    // Mesmo caso do email, devido a interface DefaultUser... q implementa o UserDetails, ja temos um get que retorna a senha.
    private String password; // Representa a senha do usuário

    @Column(name = "REGISTRATION_MOMENT", nullable = false) // name -> nome do campo , nullable -> se pode ser nullo
    @CreationTimestamp
    @Setter(AccessLevel.NONE)
    private LocalDateTime registrationMoment;

    @ManyToMany(fetch = FetchType.EAGER) // Relacionamento muito para muitos.
    @JoinTable(name = "tb_users_roles", // Nome da tabela que será criada
            joinColumns = @JoinColumn(name = "user_id"), // nome do campo do registro do id do user (PK)
            inverseJoinColumns = @JoinColumn(name = "role_id")) // nome do campo do registro do id da role (PK)
    @Getter(AccessLevel.NONE) // Mesmo caso do password e email.
    private final List<Role> roles = new ArrayList<>();     // Muitos usuários tem muitas permissões, o final foi usado pois não é interessante alterar a lista, e sim adicionar e remover os atributos

    @Builder // Indica um Builder para construir um user baseado nos parametros abaixo.
    public User(String nickname, String email, String password) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

    public void addRole(Role role) { // Método adiciona a role em um usuário
        roles.add(role);
    }


    // -------------------------------------------- MÉTODOS DEFAULT-----------------------

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public List<Role> getAuthorities() {
        return this.roles;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    // MÉTODO toString

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("Id: " + id + ", Name: " + getNickname() + ", Email: " + email + "\n");
        sb.append("---Roles---\n");
        for (Role role : roles) {
            sb.append(role + "\n");
        }
        sb.append("---Roles---\n");
        sb.append("\n");
        return sb.toString();

    }

}
