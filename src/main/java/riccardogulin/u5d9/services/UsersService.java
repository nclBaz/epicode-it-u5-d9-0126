package riccardogulin.u5d9.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import riccardogulin.u5d9.entities.User;
import riccardogulin.u5d9.exceptions.BadRequestException;
import riccardogulin.u5d9.exceptions.NotFoundException;
import riccardogulin.u5d9.payloads.PasswordChangeDTO;
import riccardogulin.u5d9.payloads.UserDTO;
import riccardogulin.u5d9.payloads.UserUpdateDTO;
import riccardogulin.u5d9.repositories.UsersRepository;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class UsersService {
	private final UsersRepository usersRepository;
	private final Cloudinary fileUploader;

	public UsersService(UsersRepository usersRepository, Cloudinary fileUploader) {

		this.usersRepository = usersRepository;
		this.fileUploader = fileUploader;
	}

	public User save(UserDTO payload) {
		// 1. Verifichiamo che l'email non sia già utilizzata
		if (this.usersRepository.existsByEmail(payload.email()))
			throw new BadRequestException("L'indirizzo email " + payload.email() + " è già utilizzato!");

		// 2. TODO: Ulteriori controlli di validazione
		// 3. Creo il nuovo oggetto User leggendo i valori dal payload
		User newUser = new User(payload.name(), payload.surname(), payload.email(), payload.password(), payload.dateOfBirth());

		// 4. Salvo
		User savedUser = this.usersRepository.save(newUser);

		// 5. Eventuale log
		log.info("Utente " + savedUser.getUserId() + " salvato");

		// 6. Ritorno l'utente salvato
		return savedUser;

	}

	public Page<User> getAll(int page, int size, String orderBy) {
		if (size > 50) size = 50;
		if (size < 0) size = 10;
		if (page < 0) page = 0;
		Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
		return this.usersRepository.findAll(pageable);
	}

	public User findById(UUID userId) {
		return this.usersRepository.findById(userId).orElseThrow(() -> new NotFoundException(userId));
	}

	public User findByIdAndUpdate(UUID userId, UserUpdateDTO payload) {
		// 1. Cerchiamo l'utente tramite id, se non esiste -> 404
		User found = this.findById(userId);

		// 2. Controlli vari, tipo email già in uso (se l'utente non sta cambiando email non faccio il controllo)
		if (!found.getEmail().equals(payload.email()))
			if (this.usersRepository.existsByEmail(payload.email()))
				throw new BadRequestException("L'indirizzo email " + payload.email() + " è già utilizzato!");

		// 3. Modifichiamo l'utente trovato con i setter
		found.setName(payload.name());
		found.setSurname(payload.surname());
		found.setEmail(payload.email());
		found.setDateOfBirth(payload.dateOfBirth());

		// 4. Save
		User updatedUser = this.usersRepository.save(found);

		// 5. Ritorniamo l'utente modificato
		return updatedUser;
	}

	public void findByIdAndDelete(UUID userId) {
		User found = this.findById(userId);
		this.usersRepository.delete(found);
	}

	public void updatePassword(UUID userId, PasswordChangeDTO payload) {
		User found = this.findById(userId);

		if (!found.getPassword().equals(payload.oldPassword())) throw new BadRequestException("Le password non corrispondono!");

		found.setPassword(payload.newPassword());

		this.usersRepository.save(found);
	}

	public void updateAvatar(UUID userId, MultipartFile file) {
		// 1. Controlli vari tipo file non più grande di tot, tipo di file permesso solo GIF
		// 2. Find by id dell'utente

		// 3. Upload del file su Cloudinary
		try {
			Map result = fileUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
			String url = (String) result.get("secure_url");
			System.out.println(url);
			// 4. Se l'upload va a buon fine, Cloudinary ci restituirà l'url dell'immagine.
			// Questo URL deve essere settato nel record dell'utente
			// (setAvatarURL("url")
			// save dell'utente

			// 5. O torno void o torno l'utente modificato
		} catch (IOException e) {
			throw new RuntimeException(e);
		}


	}
}
