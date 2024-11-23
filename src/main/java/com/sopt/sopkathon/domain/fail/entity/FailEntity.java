package com.sopt.sopkathon.domain.fail.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "fail")
public class FailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fail_id")
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "background_type")
    @Enumerated(EnumType.STRING)
    private BackgroundType backgroundType;

    public static FailEntity create(final String content, final long userId, final BackgroundType backgroundType) {
        return new FailEntity.Builder().content(content).userId(userId).backgroundType(backgroundType).build();
    }

    protected FailEntity() {
    }

    private FailEntity(Builder builder) {
        this.content = builder.content;
        this.userId = builder.userId;
        this.backgroundType = builder.backgroundType;
    }

    // Builder class
    public static class Builder {
        private String content;
        private long userId;
        private BackgroundType backgroundType;

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder userId(long userId) {
            this.userId = userId;
            return this;
        }

        public Builder backgroundType(BackgroundType backgroundType) {
            this.backgroundType = backgroundType;
            return this;
        }

        public FailEntity build() {
            return new FailEntity(this);
        }
    }
}
