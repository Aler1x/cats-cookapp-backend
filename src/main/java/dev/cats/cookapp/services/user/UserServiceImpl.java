package dev.cats.cookapp.services.user;

import dev.cats.cookapp.dto.request.UserRequest;
import dev.cats.cookapp.dto.response.UserResponse;
import dev.cats.cookapp.mappers.UserMapper;
import dev.cats.cookapp.models.User;
import dev.cats.cookapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<UserResponse> getUser(Long id) {
        return userRepository.findById(id).map(userMapper::toDto);
    }

    @Override
    public Optional<UserResponse> getUser(String email) {
        return userRepository.findByEmail(email).map(userMapper::toDto);
    }

    @Override
    public Optional<User> getUserModel(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        userRequest.setId(null);
        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        return userMapper.toDto(userRepository.save(userMapper.toEntity(userRequest)));
    }

    @Override
    public UserResponse updateUser(UserRequest userRequest) {
        return userMapper.toDto(userRepository.save(userMapper.toEntity(userRequest)));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
