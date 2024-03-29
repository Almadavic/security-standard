package com.almadavic.securitystandard.service.serviceAction;


import com.almadavic.securitystandard.dto.request.RegisterUserDTO;
import com.almadavic.securitystandard.dto.response.UserMonitoringDTO;
import com.almadavic.securitystandard.entity.Role;
import com.almadavic.securitystandard.entity.User;
import com.almadavic.securitystandard.enumerated.RoleName;
import com.almadavic.securitystandard.repository.RoleRepository;
import com.almadavic.securitystandard.repository.UserRepository;
import com.almadavic.securitystandard.service.businessRule.findUsersByParameter.*;
import com.almadavic.securitystandard.service.businessRule.registerUser.RegisterUserArgs;
import com.almadavic.securitystandard.service.businessRule.registerUser.RegisterUserVerification;
import com.almadavic.securitystandard.service.customException.ResourceNotFoundException;
import com.almadavic.securitystandard.util.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service // Indica que é uma camada de serviço , o spring vai gerenciar automaticamente.
@RequiredArgsConstructor
// Faz com que quando a classe for instanciada, os atributos vão ser passados no construtor automaticamente.
@Primary // Essa vai ser a implementação a ser carregada caso tenha mais de 1.
public class UserServiceImpl extends ServiceParent implements UserService { // Serviço relacionado ao User como uma especie de CRUD.

    private final UserRepository userRepository; // Injeção de dependencia do UserRepository

    private final RoleRepository roleRepository; // injeção de dependencia de RoleRepository -> buscar uma role do banco de dados.

    private final List<RegisterUserVerification> registerUserVerifications; // Lista com regras de négocio (verificação) relacinadas ao registro de Usuário.

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // Método que mostra para o spring security como será feita a autenticação.

        return userRepository.findByEmail(username) // A autenticação será feita através do e-mail.
                .orElseThrow(() -> new ResourceNotFoundException("User not found with this e-mail : " + username));

    }

    @Override
    @CacheEvict(value = "usersList", allEntries = true)
    public UserMonitoringDTO register(RegisterUserDTO registerData) { // Método para registrar um usuário no banco.

        registerUserVerifications.forEach(v -> v.verification(new RegisterUserArgs(registerData, userRepository))); // verificações se os dados informados estão válidos para o cadastro.

        User user = UserMapper.toUserEntity(registerData, encoder);
        user.addRole(returnRole(RoleName.ROLE_USER));

        return UserMapper.toUserMonitoringDTO(userRepository.save(user));  // retorna o DTO desse usuário salvo para o client.
    }

    @Override
    @Cacheable(value = "usersList")
    public Page<UserMonitoringDTO> findAll(Pageable pageable, String roleName) { // Método que retorna uma página de usuários cadastrados no sistema.
        return UserMapper.toUserMonitoringDTO(verifyParametersToReturnCorrectPage(pageable, roleName)); // Método retorna uma página de UsersDTO (mais informações na declaração do método) e a lista é retornada pro controller.
    }

    @Override
    public UserMonitoringDTO findById(String id) { // Método retorna um usuário do banco de dados pelo ID
        return UserMapper.toUserMonitoringDTO(returnUser(id)); // retorna o DTO desse user para o controller.
    }

    private Role returnRole(RoleName name) {  // Método retorna uma role do banco de dados passando o nome dessa role como parametro
        return roleRepository.findByName(name).orElseThrow(() ->
                new ResourceNotFoundException("The role : " + name + " wasn't found in database")); // Método retorna essa role, caso a role não exista, lança exception.
    }

    private Page<User> verifyParametersToReturnCorrectPage(Pageable pageable, String roleName) { // Método usa o design patterns : Chain of Responsibility
        // Método vérifica qual página retornar baseada no parametro roleName.
        FindUsersByRoleNameVerification verification = new NoRoleName( // Verifica se não tem parametro
                new ValidRoleName( // Verifica se o parametro é válido
                        new InvalidRoleName())); // Se nada do que for acima for correto, o roleName é inválido.

        return verification.verification(new FindUsersArgs(userRepository, pageable, roleName)); // Retorna a página correta.

    }

    private User returnUser(String id) { // Método retorna um usuário do banco de dados pelo id.
        return userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("The user id: " + id + " wasn't found on database")); // Se esse usuário não existir, vai ser lançada uma exception.
    }

}
