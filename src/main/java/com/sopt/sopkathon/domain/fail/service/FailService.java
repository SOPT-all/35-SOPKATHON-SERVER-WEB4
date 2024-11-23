package com.sopt.sopkathon.domain.fail.service;

import com.sopt.sopkathon.common.exception.CustomException;
import com.sopt.sopkathon.common.response.message.FailMessage;
import com.sopt.sopkathon.domain.emoji.entity.EmojiEntity;
import com.sopt.sopkathon.domain.emoji.entity.EmojiType;
import com.sopt.sopkathon.domain.emoji.repository.EmojiRepository;
import com.sopt.sopkathon.domain.fail.controller.dto.req.FailCreateReq;
import com.sopt.sopkathon.domain.fail.controller.dto.res.AllFailsRes;
import com.sopt.sopkathon.domain.fail.controller.dto.res.DetailFailInfo;
import com.sopt.sopkathon.domain.fail.controller.dto.res.FailRankList;
import com.sopt.sopkathon.domain.fail.controller.dto.res.MyAllFailsRes;
import com.sopt.sopkathon.domain.fail.entity.BackgroundType;
import com.sopt.sopkathon.domain.fail.entity.FailEntity;
import com.sopt.sopkathon.domain.fail.repository.FailRepository;
import com.sopt.sopkathon.domain.user.entity.UserEntity;
import com.sopt.sopkathon.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        final UserEntity foundUser = findUserById(userID);

        final List<FailEntity> foundFails = failRepository.findAll();
        if (foundFails.isEmpty()) {
            throw new CustomException(FailMessage.NOT_FOUND_ENTITY);
        }

        final List<AllFailsRes.FailInfo> failInfos = foundFails.stream().map(
                failEntity -> {

                    final int goodCount = countByFailIdAndEmojiType(failEntity.getId(), EmojiType.GOOD);
                    final int talentCount = countByFailIdAndEmojiType(failEntity.getId(), EmojiType.TALENT);
                    final int pellikeonCount = countByFailIdAndEmojiType(failEntity.getId(), EmojiType.PELLIKEON);
                    final int drinkCount = countByFailIdAndEmojiType(failEntity.getId(), EmojiType.DRINK);

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
        final UserEntity foundUser = findUserById(userId);

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
                    final int goodCount = countByFailIdAndEmojiType(failEntity.getId(), EmojiType.GOOD);
                    final int talentCount = countByFailIdAndEmojiType(failEntity.getId(), EmojiType.TALENT);
                    final int pellikeonCount = countByFailIdAndEmojiType(failEntity.getId(), EmojiType.PELLIKEON);
                    final int drinkCount = countByFailIdAndEmojiType(failEntity.getId(), EmojiType.DRINK);

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

    // 실패 랭크리스트 (이모지 합계 상위 5개)
    public FailRankList getFailsRankList(final Long userId) {
        final UserEntity foundUser = findUserById(userId);

        final List<FailEntity> foundFails = failRepository.findAll();
        if (foundFails.isEmpty()) {
            throw new CustomException(FailMessage.NOT_FOUND_ENTITY);
        }

        final List<FailRankList.FailDetailInfo> failInfos = foundFails.stream()
                // 각 실패에 대해 이모지 합계를 계산하고 정렬
                .sorted((fail1, fail2) -> {
                    int emojiSum1 = emojiRepository.countByFailId(fail1.getId());
                    int emojiSum2 = emojiRepository.countByFailId(fail2.getId());
                    return Integer.compare(emojiSum2, emojiSum1); // 내림차순 정렬
                })
                // 상위 5개만 선택
                .limit(5)
                .map(failEntity -> {
                    final UserEntity writerUser = userRepository.findById(failEntity.getUserId()).orElseThrow(
                            () -> new CustomException(FailMessage.NOT_FOUND_ENTITY));

                    final int goodCount = countByFailIdAndEmojiType(failEntity.getId(), EmojiType.GOOD);
                    final int talentCount = countByFailIdAndEmojiType(failEntity.getId(), EmojiType.TALENT);
                    final int pellikeonCount = countByFailIdAndEmojiType(failEntity.getId(), EmojiType.PELLIKEON);
                    final int drinkCount = countByFailIdAndEmojiType(failEntity.getId(), EmojiType.DRINK);

                    EmojiType clickedEmojiType;

                    if (emojiRepository.existsByUserIdAndFailId(foundUser.getId(), failEntity.getId())) {
                        final EmojiEntity foundEmoji = emojiRepository.findByUserIdAndFailId(foundUser.getId(), failEntity.getId()).orElseThrow(
                                () -> new CustomException(FailMessage.NOT_FOUND_ENTITY)
                        );
                        clickedEmojiType = foundEmoji.getEmojiType();
                    } else {
                        clickedEmojiType = EmojiType.NOTHING;
                    }

                    return FailRankList.FailDetailInfo.of(
                            failEntity.getId(),
                            failEntity.getContent(),
                            failEntity.getBackgroundType(),
                            goodCount,
                            talentCount,
                            pellikeonCount,
                            drinkCount,
                            clickedEmojiType
                    );
                })
                .toList();

        return FailRankList.of(failInfos);
    }

    //실패 상세 조회
    public DetailFailInfo getDetailFailInfo(final Long userId, final Long failId) {
        final UserEntity foundUser = findUserById(userId);

        final FailEntity foundFail = failRepository.findById(failId).orElseThrow(
                () -> new CustomException(FailMessage.NOT_FOUND_ENTITY)
        );

        final UserEntity writerUser = userRepository.findById(foundFail.getUserId()).orElseThrow(
                () -> new CustomException(FailMessage.NOT_FOUND_ENTITY)
        );

        final int goodCount = countByFailIdAndEmojiType(foundFail.getId(), EmojiType.GOOD);
        final int talentCount = countByFailIdAndEmojiType(foundFail.getId(), EmojiType.TALENT);
        final int pellikeonCount = countByFailIdAndEmojiType(foundFail.getId(), EmojiType.PELLIKEON);
        final int drinkCount = countByFailIdAndEmojiType(foundFail.getId(), EmojiType.DRINK);

        EmojiType clickedEmojiType;

        if (emojiRepository.existsByUserIdAndFailId(foundUser.getId(), foundFail.getId())) {
            final EmojiEntity foundEmoji = emojiRepository.findByUserIdAndFailId(foundUser.getId(), foundFail.getId()).orElseThrow(
                    () -> new CustomException(FailMessage.NOT_FOUND_ENTITY)
            );
            clickedEmojiType = foundEmoji.getEmojiType();
        } else {
            clickedEmojiType = EmojiType.NOTHING;
        }

        return DetailFailInfo.of(
                foundFail.getId(),
                foundFail.getContent(),
                writerUser.getUserName(),
                foundFail.getBackgroundType(),
                goodCount,
                talentCount,
                pellikeonCount,
                drinkCount,
                clickedEmojiType);
    }

    // fail 생성
    @Transactional
    public void createFail(final Long userId, final String content, final BackgroundType backgroundType) {
        final UserEntity foundUser = findUserById(userId);
        final FailEntity newFail = FailEntity.create(content, userId, backgroundType);
        failRepository.save(newFail);
    }

    //유저찾기
    private UserEntity findUserById(final Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new CustomException(FailMessage.NOT_FOUND_ENTITY)
        );
    }

    private int countByFailIdAndEmojiType(final Long failId, final EmojiType emojiType) {
        return emojiRepository.countByFailIdAndEmojiType(failId, emojiType);
    }

}
