package riccardogulin.u5d9.old_payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PasswordChangePayload {
	private String oldPassword;
	private String newPassword;

}
