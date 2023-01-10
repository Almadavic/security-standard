package com.almadavic.securitystandard.config.extraConfig;

import com.almadavic.securitystandard.entity.Role;
import com.almadavic.securitystandard.entity.User;
import com.almadavic.securitystandard.enumerated.RoleName;
import com.almadavic.securitystandard.repository.RoleRepository;
import com.almadavic.securitystandard.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;


@RequiredArgsConstructor
// Faz com que quando a classe for instanciada, os atributos vão ser passados no construtor automaticamente.
@Configuration // Indica que é uma classe de configuração
@Profile(value = {"test"}) // Define em quais ambientes (profiles) essa classe será "chamada"
public class StartProjectConfigurations implements CommandLineRunner { // Essa classe é uma clase separada de configuração, Ela serve para popular o banco de dados (NO CASO)!

    private final UserRepository userRepository; // Repositório da entidade Usuário

    private final RoleRepository roleRepository; // Repositório da entidade Role

    private final PasswordEncoder encoder;  // Encoder para codificar a senha

    @Override
    public void run(String... args) { // Esse método fala que toda vez que o programa iniciar(aplicação for pro ar) em ambiente de DEV E TEST,
        // essa classe será chamada, consequentemente esse método.

        // Objetivo método -> Ele serve para popular o banco de dados e testar se os relacionamentos estão corretos.

        User u1 = User.builder()
                .nickname("admin")
                .email("admin@hotmail.com")
                .password(encoder.encode("123456"))
                .build();

        User u2 = User.builder()
                .nickname("user1")
                .email("user1@hotmail.com")
                .password(encoder.encode("123456"))
                .build();

        User u3 = User.builder()
                .nickname("user2")
                .email("user2@hotmail.com")
                .password(encoder.encode("123456"))
                .build();

        List<User> users = userRepository.saveAll(Arrays.asList(u1, u2, u3));

        Role r1 = Role.builder()
                .name(RoleName.ROLE_ADMIN)
                .build();

        Role r2 = Role.builder()
                .name(RoleName.ROLE_USER)
                .build();

        roleRepository.saveAll(Arrays.asList(r1, r2));

        users.get(0).addRole(r1);
        users.get(1).addRole(r2);
        users.get(2).addRole(r2);

        users = userRepository.saveAll(users);

        System.out.println(users);

    }

    /*
         ------ EXPLICAÇÃO CLASSE --------
         É criado um usuário, uma role e são associados entre sí no banco (ManyToMany), O método popula o banco com esses dados para facilitar os testes pelo
         postman, para não ter que ficar criando os dados de cadastro de forma obrigatória toda vez quando não desejado. Além disso, nos permite verificar se o
         relacionamento entre as classes está correto.
     */
}

