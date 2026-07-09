package riccardogulin.u5d9.old_payloads;

import java.time.LocalDateTime;

public class ErrorsPayload {
	private String message;
	private LocalDateTime timestamp;

	public ErrorsPayload(String message, LocalDateTime timestamp) {
		this.message = message;
		this.timestamp = timestamp;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}
}
