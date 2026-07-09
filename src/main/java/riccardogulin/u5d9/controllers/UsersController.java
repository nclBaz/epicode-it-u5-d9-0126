package riccardogulin.u5d9.controllers;


import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import riccardogulin.u5d9.entities.User;
import riccardogulin.u5d9.exceptions.ValidationException;
import riccardogulin.u5d9.payloads.PasswordChangeDTO;
import riccardogulin.u5d9.payloads.UserDTO;
import riccardogulin.u5d9.payloads.UserResponseDTO;
import riccardogulin.u5d9.payloads.UserUpdateDTO;
import riccardogulin.u5d9.services.UsersService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UsersController {
	private final UsersService usersService;

	public UsersController(UsersService usersService) {
		this.usersService = usersService;
	}

	// 1. POST http://locahost:PORT/users (+req.body) --> 201
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED) // 201
	public UserResponseDTO saveUser(@RequestBody @Validated UserDTO body, BindingResult validationResult) {
		if (validationResult.hasErrors()) {
//			String errors = validationResult.getFieldErrors().stream()
//					.map(fieldError -> fieldError.getDefaultMessage())
//					.collect(Collectors.joining(". "));
//
//			throw new ValidationException(errors);

			List<String> errorsList = validationResult.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList();
			throw new ValidationException(errorsList);
		}
		User saved = this.usersService.save(body);
		return new UserResponseDTO(saved.getUserId());
	}

	// 2. GET http://locahost:PORT/users
	@GetMapping
	public Page<User> getUsers(@RequestParam(defaultValue = "0") int page,
	                           @RequestParam(defaultValue = "10") int size,
	                           @RequestParam(defaultValue = "surname") String orderBy) {
		return this.usersService.getAll(page, size, orderBy);
	}

	// 3. GET http://locahost:PORT/users/{userId}
	@GetMapping("/{userId}")
	public User getById(@PathVariable UUID userId) {
		return this.usersService.findById(userId);
	}

	// 4. PUT http://locahost:PORT/users/{userId} (+req.body)
	@PutMapping("/{userId}")
	public User getByIdAndUpdate(@PathVariable UUID userId, @RequestBody UserUpdateDTO body) {
		return this.usersService.findByIdAndUpdate(userId, body);
	}

	// 5. DELETE http://locahost:PORT/users/{userId} 204
	@DeleteMapping("/{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void getByIdAndDelete(@PathVariable UUID userId) {
		this.usersService.findByIdAndDelete(userId);
	}

	// 6. PATCH http://locahost:PORT/users/{userId}/password
	@PatchMapping("/{userId}/password")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updatePassword(@PathVariable UUID userId, @RequestBody PasswordChangeDTO body) {
		this.usersService.updatePassword(userId, body);

	}

}
