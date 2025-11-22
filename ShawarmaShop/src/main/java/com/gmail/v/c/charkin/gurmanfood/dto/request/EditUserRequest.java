package com.gmail.v.c.charkin.gurmanfood.dto.request;

import com.gmail.v.c.charkin.gurmanfood.constants.ErrorMessage;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class EditUserRequest {

    @NotBlank(message = ErrorMessage.EMPTY_FIRST_NAME)
    @Size(min = 1, max = 255, message = "First name must be between 1 and 255 characters")
    private String firstName;

    @NotBlank(message = ErrorMessage.EMPTY_LAST_NAME)
    @Size(min = 1, max = 255, message = "Last name must be between 1 and 255 characters")
    private String lastName;

    @Size(max = 255, message = "City must not exceed 255 characters")
    private String city;

    @Size(max = 500, message = "Address must not exceed 500 characters")
    private String address;

    @Pattern(regexp = "^[+]?[(]?[0-9]{1,4}[)]?[-\\s.]?[(]?[0-9]{1,4}[)]?[-\\s.]?[0-9]{1,9}$", 
            message = "Phone number format is invalid")
    @Size(max = 20, message = "Phone number must not exceed 20 characters")
    private String phoneNumber;

    private String postIndex;

    @Email(message = ErrorMessage.INCORRECT_EMAIL)
    @Size(max = 255, message = "Email must not exceed 255 characters")
    private String email;
}
