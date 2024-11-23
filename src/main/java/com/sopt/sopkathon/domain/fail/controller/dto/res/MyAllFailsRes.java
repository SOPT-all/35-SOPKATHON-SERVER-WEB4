package com.sopt.sopkathon.domain.fail.controller.dto.res;

import com.sopt.sopkathon.domain.fail.entity.BackgroundType;

import java.util.List;

public record MyAllFailsRes(
        List<MyFailInfo> failInfos

) {
    public static MyAllFailsRes of(final List<MyFailInfo> failInfo) {
        return new MyAllFailsRes(failInfo);
    }

    public record MyFailInfo(
            Long failId,
            String content,
            BackgroundType backgroundType,
            int goodCount,
            int talentCount,
            int pellikeonCount,
            int drinkCount
    ) {


        public static MyFailInfo of(Long failId,
                                    String content,
                                    BackgroundType backgroundType,
                                    int goodCount,
                                    int talentCount,
                                    int pellikeonCount,
                                    int drinkCount) {
            return new Builder()
                    .failId(failId)
                    .content(content)
                    .backgroundType(backgroundType)
                    .goodCount(goodCount)
                    .talentCount(talentCount)
                    .pellikeonCount(pellikeonCount)
                    .drinkCount(drinkCount)
                    .build();
        }

        public static Builder builder() {
            return new Builder();
        }

        public static class Builder {
            private Long failId;
            private String content;
            private BackgroundType backgroundType;
            private int goodCount;
            private int talentCount;
            private int pellikeonCount;
            private int drinkCount;

            public Builder failId(Long failId) {
                this.failId = failId;
                return this;
            }

            public Builder content(String content) {
                this.content = content;
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

            public MyFailInfo build() {
                return new MyFailInfo(failId, content, backgroundType, goodCount, talentCount, pellikeonCount, drinkCount);
            }
        }
    }
}