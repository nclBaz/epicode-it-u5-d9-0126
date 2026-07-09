package riccardogulin.u5d9.payloads;

import java.time.LocalDate;

public record UserDTO(String name,
                      String surname,
                      String email,
                      String password,
                      LocalDate dateOfBirth) {
}
