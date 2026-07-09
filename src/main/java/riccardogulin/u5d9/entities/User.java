package riccardogulin.u5d9.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "users")
@NoArgsConstructor
@ToString
@Getter
@Setter
public class User {

	@Id
	@GeneratedValue
	private UUID userId;

	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String surname;
	@Column(nullable = false, unique = true)
	private String email;
	@Column(nullable = false)
	private String password;
	@Column(name = "avatar_url", nullable = false)
	private String avatarURL;
	@Column(name = "date_of_birth", nullable = false)
	private LocalDate dateOfBirth;

	public User(String name, String surname, String email, String password, LocalDate dateOfBirth) {
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.dateOfBirth = dateOfBirth;
		this.avatarURL = "https://ui-avatars.com/api/?name=" + name + "+" + surname;
	}
}
