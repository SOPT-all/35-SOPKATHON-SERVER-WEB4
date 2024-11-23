package com.sopt.sopkathon.domain.fail.service;

import com.sopt.sopkathon.common.exception.CustomException;
import com.sopt.sopkathon.common.response.message.FailMessage;
import com.sopt.sopkathon.domain.emoji.entity.EmojiType;
import com.sopt.sopkathon.domain.emoji.repository.EmojiRepository;
import com.sopt.sopkathon.domain.fail.controller.dto.res.AllFailsRes;
import com.sopt.sopkathon.domain.fail.entity.FailEntity;
import com.sopt.sopkathon.domain.fail.repository.FailRepository;
import com.sopt.sopkathon.domain.user.entity.UserEntity;
import com.sopt.sopkathon.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponse;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class FailService {

    private final FailRepository failRepository;
    private final UserRepository userRepository;
    private final EmojiRepository emojiRepository;

    public FailService(FailRepository failRepository, UserRepository userRepository, EmojiRepository emojiRepository) {
        this.failRepository = failRepository;
        this.userRepository = userRepository;
        this.emojiRepository = emojiRepository;
    }

    //실패 전체 목록 조회
    public AllFailsRes getAllFails() {
        final List<FailEntity> foundFails = failRepository.findAll();
        if (foundFails.isEmpty()) {
            throw new CustomException(FailMessage.NOT_FOUND_ENTITY);
        }

        final List<AllFailsRes.FailInfo> failInfos = foundFails.stream().map(
                failEntity -> {
                    final UserEntity foundUser = userRepository.findById(failEntity.getUserId()).orElseThrow(
                            () -> new CustomException(FailMessage.NOT_FOUND_ENTITY));

                    final int goodCount = emojiRepository.countByFailIdAndEmojiType(failEntity.getId(), EmojiType.GOOD);
                    final int talentCount = emojiRepository.countByFailIdAndEmojiType(failEntity.getId(), EmojiType.TALENT);
                    final int pellikeonCount = emojiRepository.countByFailIdAndEmojiType(failEntity.getId(), EmojiType.PELLIKEON);
                    final int drinkCount = emojiRepository.countByFailIdAndEmojiType(failEntity.getId(), EmojiType.DRINK);

                    return AllFailsRes.FailInfo.of(
                            failEntity.getId(),
                            failEntity.getContent(),
                            foundUser.getUserName(),
                            failEntity.getBackgroundType(),
                            goodCount,
                            talentCount,
                            pellikeonCount,
                            drinkCount
                    );
                }).toList();
        return AllFailsRes.of(failInfos);
    }
}
