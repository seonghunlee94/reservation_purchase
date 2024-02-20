package com.seonghun.module_user_service.domain.user.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import com.seonghun.module_user_service.domain.user.application.UserService;
import com.seonghun.module_user_service.domain.user.domain.Users;
import com.seonghun.module_user_service.domain.user.dto.LoginRequestDto;
import com.seonghun.module_user_service.domain.user.dto.SignupRequestDto;
import com.seonghun.module_user_service.domain.user.dto.UpdateImformationRequestDto;
import com.seonghun.module_user_service.domain.user.exception.DuplicateException;
import com.seonghun.module_user_service.global.error.ErrorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/signup")
    public ResponseEntity<String> signUpUser(@Valid @RequestBody SignupRequestDto newUser) {
        try {
            Users signedUpUser = userService.signUpUser(newUser);
            signedUpUser.setPassword(null);
            return ResponseEntity.ok("회원가입되었습니다.");
        } catch (DuplicateException e) {
            ErrorDto errorDto = new ErrorDto(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorDto.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequestDto loginRequestDto) {
        try {
            userService.login(loginRequestDto);
            return ResponseEntity.ok("로그인 성공");
        } catch (IllegalArgumentException e) {
            ErrorDto errorDto = new ErrorDto(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDto.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        return ResponseEntity.ok().body("로그아웃 성공!");
    }

    @PatchMapping("/information")
    public ResponseEntity<String> patchInformation(HttpServletRequest request, @RequestBody UpdateImformationRequestDto updateUser) {

        String username = request.getHeader("username");

        // 로그인된 상태인지 확인 -> 토큰 유효성 검사로 추후 변경.
        if (username != null) {
            try {
                userService.patchInformation(username, updateUser);
                return ResponseEntity.ok().body("개인 정보가 수정되었습니다.");
            } catch (IllegalArgumentException e) {
                ErrorDto errorDto = new ErrorDto(e.getMessage());
                return ResponseEntity.ok().body(errorDto.getMessage());
            }
        } else {
            return ResponseEntity.ok().body("로그인 후 진행해주세요.");
        }

    }

    @PatchMapping("/password")
    public ResponseEntity<String> patchPassword(HttpServletRequest request, @RequestBody UpdateImformationRequestDto updateUser) {

        String username = request.getHeader("username");


        // 로그인된 상태인지 확인 -> 토큰 유효성 검사로 추후 변경.
        if (username != null) {
            try {
                userService.patchPassword(username, updateUser);
                return ResponseEntity.ok().body("비밀번호가 수정되었습니다.");
            } catch (IllegalArgumentException e) {
                ErrorDto errorDto = new ErrorDto(e.getMessage());
                return ResponseEntity.ok().body(errorDto.getMessage());
            }
        } else {
            return ResponseEntity.ok().body("로그인 후 진행해주세요.");
        }
    }

}
