package org.sopt.diary.service;

import org.sopt.diary.dto.SignInRequest;
import org.sopt.diary.dto.SignUpRequest;
import org.sopt.diary.repository.UserEntity;
import org.sopt.diary.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity signUp(SignUpRequest signUpRequest) {
        UserEntity userEntity = new UserEntity(
                signUpRequest.getUsername(),
                signUpRequest.getPassword(),
                signUpRequest.getNickname()
        );
        return userRepository.save(userEntity); // UserEntity 반환
    }

    public UserEntity signIn(SignInRequest signInRequest) {
        return userRepository.findByUsernameAndPassword(
                signInRequest.getUsername(),
                signInRequest.getPassword()
        ).orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));
    }
}
