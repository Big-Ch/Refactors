package com.gmail.v.c.charkin.gurmanfood.dto.request;

import com.gmail.v.c.charkin.gurmanfood.constants.ErrorMessage;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class OrderRequest {
    private Long id;
    private Double totalPrice;
    private LocalDateTime date = LocalDateTime.now();

    @NotBlank(message = ErrorMessage.FILL_IN_THE_INPUT_FIELD)
    @Size(min = 1, max = 255, message = "First name must be between 1 and 255 characters")
    private String firstName;

    @NotBlank(message = ErrorMessage.FILL_IN_THE_INPUT_FIELD)
    @Size(min = 1, max = 255, message = "Last name must be between 1 and 255 characters")
    private String lastName;

    @NotBlank(message = ErrorMessage.FILL_IN_THE_INPUT_FIELD)
    @Size(min = 1, max = 255, message = "City must be between 1 and 255 characters")
    private String city;

    @NotBlank(message = ErrorMessage.FILL_IN_THE_INPUT_FIELD)
    @Size(min = 1, max = 500, message = "Address must be between 1 and 500 characters")
    private String address;

    @Email(message = ErrorMessage.INCORRECT_EMAIL)
    @NotBlank(message = ErrorMessage.EMAIL_CANNOT_BE_EMPTY)
    @Size(max = 255, message = "Email must not exceed 255 characters")
    private String email;

    @NotBlank(message = ErrorMessage.EMPTY_PHONE_NUMBER)
    @Pattern(regexp = "^[+]?[(]?[0-9]{1,4}[)]?[-\\s.]?[(]?[0-9]{1,4}[)]?[-\\s.]?[0-9]{1,9}$", 
            message = "Phone number format is invalid")
    @Size(min = 5, max = 20, message = "Phone number must be between 5 and 20 characters")
    private String phoneNumber;
}
