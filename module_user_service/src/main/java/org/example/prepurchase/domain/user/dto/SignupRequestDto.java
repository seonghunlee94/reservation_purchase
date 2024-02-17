package org.example.prepurchase.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignupRequestDto {


    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password;

    @NonNull
    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String username;

    @Email
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    private String email;

    @NotBlank(message = "인사말은 필수 입력 값입니다.")
    private String greeting;

    @NotBlank(message = "프로필 이미지는 필수 입력 값입니다.")
    private String profileImage;

    @Builder.Default
    private String adminToken = "";

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getGreeting() {
        return greeting;
    }

    public String getProfileImage() {
        return profileImage;
    }

}
