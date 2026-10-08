package br.com.igormanel.clinica.shared.exception;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * Converte exceções em respostas no formato Problem Details (RFC 9457), sem
 * expor detalhes internos do servidor nem os valores enviados na requisição.
 */
@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(ApiExceptionHandler.class);

	@ExceptionHandler(ResourceNotFoundException.class)
	ProblemDetail handleNotFound(ResourceNotFoundException ex) {
		return problem(HttpStatus.NOT_FOUND, "Recurso não encontrado", ex.getMessage());
	}

	@ExceptionHandler(UnauthorizedException.class)
	ResponseEntity<ProblemDetail> handleUnauthorized(UnauthorizedException ex) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.header(HttpHeaders.WWW_AUTHENTICATE, "Bearer")
				.body(problem(HttpStatus.UNAUTHORIZED, "Não autorizado", ex.getMessage()));
	}

	@ExceptionHandler(Exception.class)
	ProblemDetail handleUnexpected(Exception ex) {
		log.error("Erro inesperado ao processar a requisição", ex);
		return problem(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno",
				"Não foi possível processar a requisição. Tente novamente mais tarde.");
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		List<Map<String, String>> errors = ex.getBindingResult().getFieldErrors().stream()
				.map(error -> Map.of(
						"field", error.getField(),
						"message", String.valueOf(error.getDefaultMessage())))
				.toList();

		ProblemDetail body = problem(HttpStatus.BAD_REQUEST, "Dados inválidos",
				"Um ou mais campos não passaram na validação.");
		body.setProperty("errors", errors);
		return handleExceptionInternal(ex, body, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		ProblemDetail body = problem(HttpStatus.BAD_REQUEST, "Requisição inválida",
				"O corpo da requisição está ausente ou não é um JSON válido.");
		return handleExceptionInternal(ex, body, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		ProblemDetail body = problem(HttpStatus.NOT_FOUND, "Recurso não encontrado",
				"O recurso solicitado não existe.");
		return handleExceptionInternal(ex, body, headers, status, request);
	}

	private static ProblemDetail problem(HttpStatus status, String title, String detail) {
		ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, detail);
		problem.setTitle(title);
		return problem;
	}

}
