package com.ecommerce.app.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SignupDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
