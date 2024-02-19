package com.seonghun.module_user_service.domain.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {

    @NotNull
    private String username;

    @NotNull
    private String password;
}
