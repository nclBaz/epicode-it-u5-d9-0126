package riccardogulin.u5d9.old_payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class UserPayload {
	private String name;
	private String surname;
	private String email;
	private String password;
	private LocalDate dateOfBirth;
}
