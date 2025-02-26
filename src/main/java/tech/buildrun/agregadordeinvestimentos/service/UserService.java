package tech.buildrun.agregadordeinvestimentos.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import tech.buildrun.agregadordeinvestimentos.entity.User;
import org.springframework.stereotype.Service;
import tech.buildrun.agregadordeinvestimentos.controller.CreateUserDto;
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

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUserById(String userId) {
        userRepository.deleteById(UUID.fromString(userId));
    }

    public void updateUser(String userId, CreateUserDto createUserDto) {
        var user = getUserById(userId);
        if (user.isPresent()) {
            user.get().setUsername(createUserDto.username());
            user.get().setEmail(createUserDto.email());
            user.get().setPassword(createUserDto.password());
            user.get().setUpdateTimestamp(Instant.now());
            userRepository.save(user.get());
        }

    }

}
