package riccardogulin.u5d9.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import riccardogulin.u5d9.payloads.ErrorsDTO;
import riccardogulin.u5d9.payloads.ErrorsWithListDTO;

import java.time.LocalDateTime;

@RestControllerAdvice
// Con questa annotazione, dichiaro che questa classe sarà responsabile nel gestire le eccezioni di TUTTA l'applicazione
// Ogni volta che in un service/controller/ovunque viene lanciata un'eccezione, questa arriverà ad uno dei metodi di questa
// classe
// Questi metodi dovranno essere annotati con @ExceptionHandler che permetterà loro di gestire uno specifico tipo di eccezione.
// Avremo un metodo per ogni eccezione che dobbiamo gestire
public class ErrorsHandler {

	@ExceptionHandler(BadRequestException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST) // 400
	public ErrorsDTO handleBadRequest(BadRequestException ex) {
		return new ErrorsDTO(ex.getMessage(), LocalDateTime.now());
	}

	@ExceptionHandler(ValidationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST) // 400
	public ErrorsWithListDTO handleValidation(ValidationException ex) {
		return new ErrorsWithListDTO(ex.getMessage(), LocalDateTime.now(), ex.getErrorsList());
	}

	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND) // 404
	public ErrorsDTO handleNotFound(NotFoundException ex) {
		return new ErrorsDTO(ex.getMessage(), LocalDateTime.now());
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500
	public ErrorsDTO handleGenericException(Exception ex) {
		// Nel caso di errori non BadRequest, non NotFound verrà utilizzato questo handler, mandando una risposta 500
		// (ma non svelando i dettagli dell'errore)
		ex.printStackTrace(); // Se non stampiamo lo stack trace dell'errore, la fonte dell'errore viene nascosta quindi
		// diventa difficile risolvere poi il problema
		return new ErrorsDTO("C'è stato un errore lato backend, giuro che lo risolviamo presto", LocalDateTime.now());
	}
}
