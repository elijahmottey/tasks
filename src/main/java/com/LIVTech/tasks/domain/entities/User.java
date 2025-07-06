package com.LIVTech.tasks.domain.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table (name = "users")
public class User   {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long userId;


    private String firstName;
    private String lastName;

    @Column(name = "email",unique = true)
    @Size(min = 5, max = 100)
    private String email;

    private String password;

//    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
//    private List<TaskList> taskList;
}
