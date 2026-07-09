package riccardogulin.u5d9.payloads;

import java.time.LocalDate;

public record UserUpdateDTO(String name, String surname, String email, LocalDate dateOfBirth) {
}
