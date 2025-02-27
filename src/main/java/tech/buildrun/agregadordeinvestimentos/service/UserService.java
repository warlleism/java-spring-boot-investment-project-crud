package tech.buildrun.agregadordeinvestimentos.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import tech.buildrun.agregadordeinvestimentos.entity.User;
import org.springframework.stereotype.Service;
import tech.buildrun.agregadordeinvestimentos.controller.CreateUserDto;
import tech.buildrun.agregadordeinvestimentos.controller.UpdateUserDto;
import tech.buildrun.agregadordeinvestimentos.repository.UserRepository;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UUID createUser(CreateUserDto createUserDto) {
        var entity = new User(UUID.randomUUID(), createUserDto.username(), createUserDto.email(),
                createUserDto.password(), Instant.now(), null);
        var userSaved = userRepository.save(entity);
        return userSaved.getUserId();
    }

    public Optional<User> getUserById(String userId) {
        return userRepository.findById(UUID.fromString(userId));
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public void deleteUserById(String userId) {
        var id = UUID.fromString(userId);
        var useExists = userRepository.existsById(id);
        if (useExists) {
            userRepository.deleteById(id);
        }
    }

    public void updateUserById(String userId, UpdateUserDto updateUserDto) {

        var id = UUID.fromString(userId);
        var useExists = userRepository.findById(id);

        if (useExists.isPresent()) {
            var user = useExists.get();

            if (updateUserDto.username() != null) {
                user.setUsername(updateUserDto.username());
            }

            if (updateUserDto.password() != null) {
                user.setPassword(updateUserDto.password());
            }

            userRepository.save(user);
        }

    }

}
