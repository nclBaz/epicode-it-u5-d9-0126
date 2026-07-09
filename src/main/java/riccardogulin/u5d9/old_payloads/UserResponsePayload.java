package riccardogulin.u5d9.old_payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@AllArgsConstructor
@Getter // OBBLIGATORI!
@ToString
public class UserResponsePayload {
	private UUID userId;
}
