package com.sopt.sopkathon.domain.emoji.repository;

import com.sopt.sopkathon.domain.emoji.entity.EmojiEntity;
import com.sopt.sopkathon.domain.emoji.entity.EmojiType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmojiRepository extends JpaRepository<EmojiEntity, Long> {
    int countByFailIdAndEmojiType(Long failId, EmojiType emojiType);
    int countByFailId(Long failId);
    Optional<EmojiEntity> findByUserIdAndFailId(Long userId, Long failId);

}
