package com.sopt.sopkathon.domain.fail.service;

import com.sopt.sopkathon.common.exception.CustomException;
import com.sopt.sopkathon.common.response.message.FailMessage;
import com.sopt.sopkathon.domain.emoji.entity.EmojiEntity;
import com.sopt.sopkathon.domain.emoji.entity.EmojiType;
import com.sopt.sopkathon.domain.emoji.repository.EmojiRepository;
import com.sopt.sopkathon.domain.fail.controller.dto.res.AllFailsRes;
import com.sopt.sopkathon.domain.fail.controller.dto.res.FailsRankList;
import com.sopt.sopkathon.domain.fail.controller.dto.res.MyAllFailsRes;
import com.sopt.sopkathon.domain.fail.entity.FailEntity;
import com.sopt.sopkathon.domain.fail.repository.FailRepository;
import com.sopt.sopkathon.domain.user.entity.UserEntity;
import com.sopt.sopkathon.domain.user.repository.UserRepository;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public AllFailsRes getAllFails(final Long userID) {

        final UserEntity foundUser = userRepository.findById(userID).orElseThrow(
                () -> new CustomException(FailMessage.NOT_FOUND_ENTITY)
        );

        final List<FailEntity> foundFails = failRepository.findAll();
        if (foundFails.isEmpty()) {
            throw new CustomException(FailMessage.NOT_FOUND_ENTITY);
        }

        final List<AllFailsRes.FailInfo> failInfos = foundFails.stream().map(
                failEntity -> {
                    final int goodCount = emojiRepository.countByFailIdAndEmojiType(failEntity.getId(), EmojiType.GOOD);
                    final int talentCount = emojiRepository.countByFailIdAndEmojiType(failEntity.getId(), EmojiType.TALENT);
                    final int pellikeonCount = emojiRepository.countByFailIdAndEmojiType(failEntity.getId(), EmojiType.PELLIKEON);
                    final int drinkCount = emojiRepository.countByFailIdAndEmojiType(failEntity.getId(), EmojiType.DRINK);

                    EmojiType clickedEmojiType;

                    if(emojiRepository.existsByUserIdAndFailId(foundUser.getId(), failEntity.getId())) {
                        final EmojiEntity foundEmoji = emojiRepository.findByUserIdAndFailId(foundUser.getId(), failEntity.getId()).orElseThrow(
                                () -> new CustomException(FailMessage.NOT_FOUND_ENTITY)
                        );
                        clickedEmojiType = foundEmoji.getEmojiType();
                    } else {
                        clickedEmojiType = EmojiType.NOTHING;
                    }

                    return AllFailsRes.FailInfo.of(
                            failEntity.getId(),
                            failEntity.getContent(),
                            failEntity.getBackgroundType(),
                            goodCount,
                            talentCount,
                            pellikeonCount,
                            drinkCount,
                            clickedEmojiType
                    );
                }).toList();
        return AllFailsRes.of(failInfos);
    }

    //내 개인 실패 목록 조회
    public MyAllFailsRes getMyFails(final Long userId) {

        final UserEntity foundUser = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(FailMessage.NOT_FOUND_ENTITY)
        );

        final List<FailEntity> foundMyFails = failRepository.findAllByUserId(userId);

        if (foundMyFails.isEmpty()) {
            throw new CustomException(FailMessage.NOT_FOUND_ENTITY);
        }

        // FailEntity를 이모지 합계 기준으로 정렬
        List<FailEntity> sortedFails = foundMyFails.stream()
                .sorted((fail1, fail2) -> {
                    // 각 실패의 이모지 합계를 계산
                    int emojiSum1 = emojiRepository.countByFailId(fail1.getId());
                    int emojiSum2 = emojiRepository.countByFailId(fail2.getId());
                    return Integer.compare(emojiSum2, emojiSum1); // 내림차순 정렬
                }).toList();

        // 정렬된 실패 데이터를 MyAllFailsRes로 변환
        List<MyAllFailsRes.MyFailInfo> failInfos = sortedFails.stream()
                .map(failEntity -> {
                    int goodCount = emojiRepository.countByFailIdAndEmojiType(failEntity.getId(), EmojiType.GOOD);
                    int talentCount = emojiRepository.countByFailIdAndEmojiType(failEntity.getId(), EmojiType.TALENT);
                    int pellikeonCount = emojiRepository.countByFailIdAndEmojiType(failEntity.getId(), EmojiType.PELLIKEON);
                    int drinkCount = emojiRepository.countByFailIdAndEmojiType(failEntity.getId(), EmojiType.DRINK);

                    EmojiType clickedEmoji;

                    if(emojiRepository.existsByUserIdAndFailId(foundUser.getId(), failEntity.getId())) {
                        final EmojiEntity foundEmoji = emojiRepository.findByUserIdAndFailId(foundUser.getId(), failEntity.getId()).orElseThrow(
                                () -> new CustomException(FailMessage.NOT_FOUND_ENTITY)
                        );
                        clickedEmoji = foundEmoji.getEmojiType();
                    } else {
                        clickedEmoji = EmojiType.NOTHING;
                    }

                    return MyAllFailsRes.MyFailInfo.of(
                            failEntity.getId(),
                            failEntity.getContent(),
                            failEntity.getBackgroundType(),
                            goodCount,
                            talentCount,
                            pellikeonCount,
                            drinkCount,
                            clickedEmoji
                    );
                })
                .collect(Collectors.toList());
        return MyAllFailsRes.of(failInfos);
    }

    //실패 랭크리스트
    public FailsRankList getFailsRankList(final Long userId) {
        final UserEntity foundUser = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(FailMessage.NOT_FOUND_ENTITY)
        );

        final List<FailEntity> foundFails = failRepository.findAll();
        if (foundFails.isEmpty()) {
            throw new CustomException(FailMessage.NOT_FOUND_ENTITY);
        }

        final List<FailsRankList.FailDetailInfo> failInfos = foundFails.stream().map(
                failEntity -> {
                    final UserEntity writerUser = userRepository.findById(failEntity.getUserId()).orElseThrow(
                            () -> new CustomException(FailMessage.NOT_FOUND_ENTITY));

                    final int goodCount = emojiRepository.countByFailIdAndEmojiType(failEntity.getId(), EmojiType.GOOD);
                    final int talentCount = emojiRepository.countByFailIdAndEmojiType(failEntity.getId(), EmojiType.TALENT);
                    final int pellikeonCount = emojiRepository.countByFailIdAndEmojiType(failEntity.getId(), EmojiType.PELLIKEON);
                    final int drinkCount = emojiRepository.countByFailIdAndEmojiType(failEntity.getId(), EmojiType.DRINK);

                    EmojiType clickedEmojiType;

                    if(emojiRepository.existsByUserIdAndFailId(foundUser.getId(), failEntity.getId())) {
                        final EmojiEntity foundEmoji = emojiRepository.findByUserIdAndFailId(foundUser.getId(), failEntity.getId()).orElseThrow(
                                () -> new CustomException(FailMessage.NOT_FOUND_ENTITY)
                        );
                        clickedEmojiType = foundEmoji.getEmojiType();
                    } else {
                        clickedEmojiType = EmojiType.NOTHING;
                    }

                    return FailsRankList.FailDetailInfo.of(
                            failEntity.getId(),
                            failEntity.getContent(),
                            writerUser.getUserName(),
                            failEntity.getBackgroundType(),
                            goodCount,
                            talentCount,
                            pellikeonCount,
                            drinkCount,
                            clickedEmojiType
                    );
                }).toList();
        return FailsRankList.of(failInfos);
    }

}
