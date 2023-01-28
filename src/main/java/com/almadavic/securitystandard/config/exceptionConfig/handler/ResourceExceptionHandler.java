package com.almadavic.securitystandard.config.exceptionConfig.handler;


import com.almadavic.securitystandard.config.exceptionConfig.standardError.commonStandardError.StandardError;
import com.almadavic.securitystandard.config.exceptionConfig.standardError.validationArgsStandardError.StandardErrorArgsNotValid;
import com.almadavic.securitystandard.config.exceptionConfig.standardError.validationArgsStandardError.ValidationErrorCollection;
import com.almadavic.securitystandard.service.customException.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;


@ControllerAdvice
// Se ocorrer alguma das execções abaixo durante o programa, o Spring vai cair nessa classe e vai retornar o erro de uma forma mais agradável para o cliente.
@RequiredArgsConstructor
// Faz com que quando a classe for instanciada, os atributos com FINAL vão ser passados no construtor automaticamente.
public class ResourceExceptionHandler {

    private final MessageSource messageSource; // pega a mensagem do erro do validation.

    private static final Logger logger = LoggerFactory.getLogger(ResourceExceptionHandler.class); // Para mostrar log no console.

    @ExceptionHandler(value = MethodArgumentNotValidException.class) // Pega os erros de validação, como o @NotBlank!
    public ResponseEntity<ValidationErrorCollection> handleValidations(MethodArgumentNotValidException exception, HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        String path = request.getRequestURI();
        String error = "There is one or more parameters invalids";

        ValidationErrorCollection validationErrs = new ValidationErrorCollection(status.value(), path, error);

        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        fieldErrors.forEach(e -> {
            String message = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            String field = e.getField();
            validationErrs.addStandardErrorArgsNotValid(new StandardErrorArgsNotValid(field, message));
        });
        log(exception);
        return ResponseEntity.status(status).body(validationErrs);

    }

    @ExceptionHandler({EntityNotFoundException.class, NoHandlerFoundException.class})
    // Quando há a tentativa de acessar algum recurso inválido quando o usuário está logado.
    public ResponseEntity<StandardError> mappingNotFound(HttpServletRequest request) {
        return handlingException(new ResourceNotFoundException("The resource isn't mapped"),request,"Not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = PropertyReferenceException.class)
    // Quando passa uma referencia de propriedade incorreta, pelo client!
    public ResponseEntity<StandardError> propertyReference(PropertyReferenceException exception, HttpServletRequest request) {
        return handlingException(exception, request, "Property Reference error", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = EmailAlreadyRegisteredException.class)
    // Quando tenta cadastrar um usuário com um email q já exista no banco!
    public ResponseEntity<StandardError> emailAlreadyRegistered(EmailAlreadyRegisteredException exception, HttpServletRequest request) {
        return handlingException(exception, request, "Email error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = NicknameAlreadyRegisteredException.class)
    // Quando tenta cadastrar um usuário com um nickname q já existe no banco!
    public ResponseEntity<StandardError> nicknameAlreadyRegistered(NicknameAlreadyRegisteredException exception, HttpServletRequest request) {
        return handlingException(exception, request, "Username error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = DatabaseException.class)  // Algum problema com o banco de dados!
    public ResponseEntity<StandardError> dataBase(DatabaseException exception, HttpServletRequest request) {
        return handlingException(exception, request, "Database error", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = PasswordDoesntMatchRegisterUserException.class)
    // Quando o usuário passa 2 senhas no cadastro que não se correspondem!
    public ResponseEntity<StandardError> passwordsDontMatchException(PasswordDoesntMatchRegisterUserException exception, HttpServletRequest request) {
        return handlingException(exception, request, "Passwords error", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)  // Quando o recurso não é encontrado no banco de dados!
    public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException exception, HttpServletRequest request) {
        return handlingException(exception, request, "Resource not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = SamePasswordException.class)
    // Quando o usuário tenta mudar a senha para igual a passada!
    public ResponseEntity<StandardError> samePassword(SamePasswordException exception, HttpServletRequest request) {
        return handlingException(exception, request, "Same password", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidParamException.class) // Quando o usuário passa algum parametro errado!
    public ResponseEntity<StandardError> invalidParam(InvalidParamException exception, HttpServletRequest request) {
        return handlingException(exception, request, "Invalid Param", HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<StandardError> handlingException(Exception exception, HttpServletRequest request, String error, HttpStatus status) { // Método que será reutilizado várias vezes.
        StandardError err = new StandardError(status.value(), error, exception.getMessage(), request.getRequestURI());
        log(exception);
        return ResponseEntity.status(status).body(err);
    }

    private void log(Throwable exception) { // Método para aparecer o log do erro no console
        logger.error("error message {}. Details:", exception.getMessage(), exception);
    }

}
