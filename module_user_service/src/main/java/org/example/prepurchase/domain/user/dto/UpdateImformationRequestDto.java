package org.example.prepurchase.domain.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateImformationRequestDto {

    @NotNull
    private String username;

    private String password;

    private String updatePassword;

    private String greeting;

    private String profileImage;


}
