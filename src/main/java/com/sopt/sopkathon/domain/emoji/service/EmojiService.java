package com.sopt.sopkathon.domain.emoji.service;

import com.sopt.sopkathon.domain.emoji.entity.EmojiEntity;
import com.sopt.sopkathon.domain.emoji.entity.EmojiType;
import com.sopt.sopkathon.domain.emoji.repository.EmojiRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmojiService {

    private final EmojiRepository emojiRepository;

    public EmojiService(EmojiRepository emojiRepository) {
        this.emojiRepository = emojiRepository;
    }

    @Transactional
    public void postEmoji(final Long userId, final Long failId, final EmojiType emojiType) {
        EmojiEntity newEmoji = EmojiEntity.create(userId, failId, emojiType);
        emojiRepository.save(newEmoji);
    }
}
