package com.sopt.sopkathon.domain.fail.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "fail")
public class FailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fail_id", nullable = false)
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "background_type", nullable = false)
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

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public long getUserId() {
        return userId;
    }

    public BackgroundType getBackgroundType() {
        return backgroundType;
    }

}
