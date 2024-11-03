package org.sopt.diary.api;

import org.sopt.diary.dto.SignUpRequest;
import org.sopt.diary.dto.SignInRequest;
import org.sopt.diary.dto.UserResponse;
import org.sopt.diary.repository.UserEntity;
import org.sopt.diary.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/signup")
    public ResponseEntity<UserResponse> signUp(@RequestBody SignUpRequest signUpRequest) {
        UserEntity userEntity = userService.signUp(signUpRequest);
        UserResponse userResponse = new UserResponse(userEntity.getId(), userEntity.getUsername(), userEntity.getNickname());
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping("/user/signin")
    public ResponseEntity<UserResponse> signIn(@RequestBody SignInRequest signInRequest) {
        UserEntity userEntity = userService.signIn(signInRequest);
        UserResponse userResponse = new UserResponse(userEntity.getId(), userEntity.getUsername(), userEntity.getNickname());
        return ResponseEntity.ok(userResponse);
    }
}
