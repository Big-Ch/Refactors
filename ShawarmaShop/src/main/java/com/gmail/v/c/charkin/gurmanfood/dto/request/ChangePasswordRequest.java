package com.gmail.v.c.charkin.gurmanfood.dto.request;

import com.gmail.v.c.charkin.gurmanfood.constants.ErrorMessage;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ChangePasswordRequest {

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, max = 16, message = ErrorMessage.PASSWORD_CHARACTER_LENGTH)
    private String password;

    @NotBlank(message = "Password confirmation cannot be empty")
    @Size(min = 6, max = 16, message = ErrorMessage.PASSWORD2_CHARACTER_LENGTH)
    private String password2;
}
