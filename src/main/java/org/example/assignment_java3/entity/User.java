package org.example.assignment_java3.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String id;
    private String password;
    private String fullName;
    private Date birthday;
    private boolean gender;
    private String mobile;
    private String email;
    private boolean role;
}
