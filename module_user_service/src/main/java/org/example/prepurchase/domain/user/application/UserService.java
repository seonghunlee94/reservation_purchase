package org.example.prepurchase.domain.user.application;

import jakarta.servlet.http.HttpServletResponse;
import org.example.prepurchase.domain.user.domain.Users;
import org.example.prepurchase.domain.user.dao.UserRepository;
import org.example.prepurchase.domain.user.dto.LoginRequestDto;
import org.example.prepurchase.domain.user.dto.SignupRequestDto;
import org.example.prepurchase.domain.user.dto.UpdateImformationRequestDto;
import org.example.prepurchase.domain.user.exception.DuplicateException;
import org.example.prepurchase.global.auth.UserRoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public Users signUpUser(SignupRequestDto newUserDto) {
        // 이메일 중복 체크
        Users existingEmail = userRepository.findByEmail(newUserDto.getEmail());
        if (existingEmail != null) {
            throw new DuplicateException("Duplicate email address");
        }

        Users existingUser = userRepository.findByUsername(newUserDto.getUsername());
        if (existingUser != null) {
            throw new DuplicateException("Duplicate username");
        }


        // 회원가입
        Users newUser = new Users(
                newUserDto.getUsername(),
                newUserDto.getEmail(),
                newUserDto.getPassword(),
                newUserDto.getGreeting(),
                newUserDto.getProfileImage(),
                UserRoleEnum.USER
        );

        return userRepository.save(newUser);
    }

    public void login(LoginRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        // 사용자 확인
        Users user = userRepository.findByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("등록된 사용자가 없습니다.");
        }

        // 비밀번호 확인
        if (!password.equals(user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

    }

    public void patchInformation(String username, UpdateImformationRequestDto updateUser) {
        Users user = userRepository.findByUsername(username);

        if (user == null) {
            throw new IllegalArgumentException("없는 사용자입니다.");
        }

        // 변경이 없을 시. flag = False
        Boolean flag = false;

        if (!user.getGreeting().equals(updateUser.getGreeting())) {
            user.setGreeting(updateUser.getGreeting());
            flag = true;
        }

        if (!user.getProfileImage().equals(updateUser.getProfileImage())) {
            user.setProfileImage(updateUser.getProfileImage());
            flag = true;
        }

        if (!flag) {
            throw new IllegalArgumentException("변경된 정보가 없습니다.");
        }
        userRepository.save(user);

    }

    public void patchPassword(String username, UpdateImformationRequestDto updateUser) {

        Users user = userRepository.findByUsername(username);
        String password = updateUser.getPassword();
        String updatePassowrd = updateUser.getUpdatePassword();


        if (user == null) {
            throw new IllegalArgumentException("없는 사용자입니다.");
        }

        // 비밀번호 확인
        if(password.equals(user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        user.setPassword(updatePassowrd);

        userRepository.save(user);

    }

}
