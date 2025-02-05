package com.project.arbeit.user;

import com.project.arbeit.store.Store;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Store> stores = new ArrayList<>();

    @Builder
    public User(Long id, String uid, String email, String name) {
        this.id = id;
        this.uid = uid;
        this.email = email;
        this.name = name;
    }
}
