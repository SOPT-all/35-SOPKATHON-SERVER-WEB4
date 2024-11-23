package com.sopt.sopkathon.domain.fail.controller.dto.res;

import com.sopt.sopkathon.domain.emoji.entity.EmojiType;
import com.sopt.sopkathon.domain.fail.entity.BackgroundType;

import java.util.List;

public record FailsRankList(
        List<FailDetailInfo> failDetailInfoList
) {
    public static FailsRankList of(final List<FailDetailInfo> failDetailInfoList) {
        return new FailsRankList(failDetailInfoList);
    }

    public record FailDetailInfo(
            Long failId,
            String content,
            String writerName,
            BackgroundType backgroundType,
            int goodCount,
            int talentCount,
            int pellikeonCount,
            int drinkCount,
            EmojiType clickedEmoji
    ) {

        public static FailDetailInfo of(
                Long failId,
                String content,
                String writerName,
                BackgroundType backgroundType,
                int goodCount,
                int talentCount,
                int pellikeonCount,
                int drinkCount,
                EmojiType clickedEmoji
        ) {
            return new Builder()
                    .failId(failId)
                    .content(content)
                    .writerName(writerName)
                    .backgroundType(backgroundType)
                    .goodCount(goodCount)
                    .talentCount(talentCount)
                    .pellikeonCount(pellikeonCount)
                    .drinkCount(drinkCount)
                    .clickedEmoji(clickedEmoji)
                    .build();
        }

        private Builder builder() {
            return new Builder();
        }

        private static class Builder {
            private Long failId;
            private String content;
            private String writerName;
            private BackgroundType backgroundType;
            private int goodCount;
            private int talentCount;
            private int pellikeonCount;
            private int drinkCount;
            private EmojiType clickedEmoji;

            public Builder failId(Long failId) {
                this.failId = failId;
                return this;
            }

            public Builder content(String content) {
                this.content = content;
                return this;
            }

            public Builder writerName(String userName) {
                this.writerName = userName;
                return this;
            }

            public Builder backgroundType(BackgroundType backgroundType) {
                this.backgroundType = backgroundType;
                return this;
            }

            public Builder goodCount(int goodCount) {
                this.goodCount = goodCount;
                return this;
            }

            public Builder talentCount(int talentCount) {
                this.talentCount = talentCount;
                return this;
            }

            public Builder pellikeonCount(int pellikeonCount) {
                this.pellikeonCount = pellikeonCount;
                return this;
            }

            public Builder drinkCount(int drinkCount) {
                this.drinkCount = drinkCount;
                return this;
            }

            public Builder clickedEmoji(EmojiType clickedEmoji) {
                this.clickedEmoji = clickedEmoji;
                return this;
            }

            public FailsRankList.FailDetailInfo build() {
                return new FailsRankList.FailDetailInfo(failId, content, writerName, backgroundType, goodCount, talentCount, pellikeonCount, drinkCount, clickedEmoji);
            }
        }
    }
}
