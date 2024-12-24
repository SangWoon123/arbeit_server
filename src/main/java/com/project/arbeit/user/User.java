package com.project.arbeit.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uid", unique = true)
    private String uid;

    @Column(name = "email", unique = true)
    private String email;

    private String name;

    @Builder
    public User(Long id, String uid, String email, String name) {
        this.id = id;
        this.uid = uid;
        this.email = email;
        this.name = name;
    }
}
