package com.sopt.sopkathon.domain.emoji.service;

import com.sopt.sopkathon.common.exception.CustomException;
import com.sopt.sopkathon.common.response.message.FailMessage;
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
        final EmojiEntity newEmoji = EmojiEntity.create(userId, failId, emojiType);
        emojiRepository.save(newEmoji);
    }

    @Transactional
    public void deleteEmoji(final Long userId, final Long failId) {
        final EmojiEntity foundEmoji = emojiRepository.findByUserIdAndFailId(userId, failId).orElseThrow(
                () -> new CustomException(FailMessage.NOT_FOUND_ENTITY)
        );
        emojiRepository.deleteById(foundEmoji.getId());
    }
}
