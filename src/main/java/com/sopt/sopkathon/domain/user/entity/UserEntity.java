package com.sopt.sopkathon.domain.user.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column(name = "user_name", nullable = false)
    private String userName;

    public static UserEntity create(final String userName) {
        return new UserEntity(userName);
    }

    public UserEntity(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public Long getId() {
        return id;
    }

    protected UserEntity() {}
}

