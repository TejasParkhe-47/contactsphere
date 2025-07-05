package com.ContactSphere.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserForm {


    @NotBlank(message = "username is required")
    @Size(min = 3,message = "min 3 chars are required")
    private String name;

    @Email(message = "invalid emain address")
    @NotBlank(message="email required")
    private String email;

    @NotBlank(message = "password is required")
    @Size(min = 6 ,message="min 6 charectors required")
    private String password;

    @NotBlank(message = "should nto black")
    private String about;

    @Size(min=8,max=12 ,message = "invalid phone number")
    private String phoneNumber;

}
