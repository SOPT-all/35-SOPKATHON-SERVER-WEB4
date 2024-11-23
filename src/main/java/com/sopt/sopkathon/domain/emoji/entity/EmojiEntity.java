package com.sopt.sopkathon.domain.emoji.entity;

import jakarta.persistence.*;

@Entity
@Table(
        name = "emoji",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "fail_id"})
        }
)public class EmojiEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emoji_id", nullable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "fail_id", nullable = false)
    private long failId;

    @Column(name = "emoji_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private EmojiType emojiType;

    protected EmojiEntity() { }

    public static EmojiEntity create(final long userId, final long failId, final EmojiType emojiType) {
        return new EmojiEntity.Builder().emojiType(emojiType).userId(userId).failId(failId).build();
    }

    private EmojiEntity(Builder builder) {
        this.userId = builder.userId;
        this.failId = builder.failId;
        this.emojiType = builder.emojiType;
    }

    private static class Builder {
        private long userId;
        private long failId;
        private EmojiType emojiType;

        private Builder userId(long userId) {
            this.userId = userId;
            return this;
        }

        private Builder failId(long failId) {
            this.failId = failId;
            return this;
        }

        private Builder emojiType(EmojiType emojiType) {
            this.emojiType = emojiType;
            return this;
        }

        private EmojiEntity build() {
            return new EmojiEntity(this);
        }
    }

    public Long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public long getFailId() {
        return failId;
    }

    public EmojiType getEmojiType() {
        return emojiType;
    }
}


