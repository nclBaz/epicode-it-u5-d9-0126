package riccardogulin.u5d9.payloads;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record UserDTO(
		@NotBlank(message = "Il nome proprio è obbligatorio, non può neanche essere una stringa vuota")
		@Size(min = 2, max = 40, message = "Il nome proprio deve avere un numero di caratteri compreso tra 2 e 40")
		String name,
		@NotBlank(message = "Il cognome è obbligatorio, non può neanche essere una stringa vuota")
		@Size(min = 2, max = 40, message = "Il cognome deve avere un numero di caratteri compreso tra 2 e 40")
		String surname,
		@NotBlank(message = "L'email è obbligatoria, non può neanche essere una stringa vuota")
		@Email(message = "L'email deve essere nel formato corretto")
		String email,
		@NotBlank(message = "La password è obbligatoria, non può neanche essere una stringa vuota")
		@Size(min = 8, message = "La password deve avere almeno 8 caratteri")
		@Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$", message = "La password deve contenere almeno 1 maiuscola, 1 minuscola, ...")
		String password,
		@Past(message = "La data di nascita deve essere nel passato")
		LocalDate dateOfBirth) {
}
