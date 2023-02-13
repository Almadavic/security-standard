package com.almadavic.securitystandard.service.serviceAction;


import com.almadavic.securitystandard.dto.request.ChangePassword;
import com.almadavic.securitystandard.dto.response.UserDTO;
import com.almadavic.securitystandard.entity.User;
import com.almadavic.securitystandard.repository.UserRepository;
import com.almadavic.securitystandard.service.businessRule.changePassword.ChangePasswordArgs;
import com.almadavic.securitystandard.service.businessRule.changePassword.ChangePasswordVerification;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service // Indica que é uma camada de serviço , o spring vai gerenciar automaticamente.
@RequiredArgsConstructor
// Faz com que quando a classe for instanciada, os atributos vão ser passados no construtor automaticamente.
@Primary // Essa vai ser a implementação a ser carregada caso tenha mais de 1.
public class UserAreaServiceImpl extends ServiceParent implements UserAreaService { // Serviço relacionado a area do usuário no sistema.

    private final UserRepository userRepository; // injeção de dependencia de UserRepository -> salvar, atualizar e buscar um usuário do banco de dados.

    private final List<ChangePasswordVerification> changePasswordVerifications; // Lista com regras de négocio (verificacão) relacionadas a troca de senha.

    @Override
    public UserDTO myProfile(User userLogged) { // Método recebe o usuário logado.
        return userMapper.toUserDTO(userLogged); // retorna o DTO desse usuário para o client.
    }

    @Override
    public String changePassword(ChangePassword cpDTO, User userLogged) { // Método altera a senha da conta de um usuário.

        changePasswordVerifications.forEach(v -> v.verification(new ChangePasswordArgs(cpDTO, userLogged, encoder)));  // verificações se os dados informados estão válidos para alteração de senha.

        updatePassword(cpDTO, userLogged); // método atualiza a senha do usuário (mais informações na declaração do método).

        userRepository.save(userLogged); // salva o usuário com A SENHA ALTERADA no banco de dados.

        return "Password changed successfully!"; // retorna uma mensagem que a alteração de senha foi feita com sucesso

    }

    private void updatePassword(ChangePassword cpDTO, User userLogged) { // Método obtem a senha (nova) passada pelo usuário e altera a senha da conta com essa nova senha passada.

        String newPassword = encoder.encode(cpDTO.getNewPassword()); // Codifica a senha.

        userLogged.setPassword(newPassword); // Seta a senha codificada no usuário.

    }

}
