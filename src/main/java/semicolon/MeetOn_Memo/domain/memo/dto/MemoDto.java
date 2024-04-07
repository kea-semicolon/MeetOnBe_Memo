package semicolon.MeetOn_Memo.domain.memo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import semicolon.MeetOn_Memo.domain.memo.domain.Memo;

import java.time.LocalDateTime;

public class MemoDto {

    @Getter
    @NoArgsConstructor
    public static class MemoSaveRequestDto {
        private String content;

        @Builder
        public MemoSaveRequestDto(String content) {
            this.content = content;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class MemoDetailResponseDto {
        private String content;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createdDate;

        @Builder
        public MemoDetailResponseDto(String content, LocalDateTime createdDate) {
            this.content = content;
            this.createdDate = createdDate;
        }

        public static MemoDetailResponseDto memoDetailResponseDto(Memo memo) {
            return MemoDetailResponseDto
                    .builder()
                    .content(memo.getContent())
                    .createdDate(memo.getCreatedAt())
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class MemoPageResponseDto {
        private Long memoId;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createdDate;

        @Builder
        public MemoPageResponseDto(Long memoId, LocalDateTime createdDate) {
            this.memoId = memoId;
            this.createdDate = createdDate;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class MemoResponseDto<T> {
        private T memoList;

        @Builder
        public MemoResponseDto(T memoList) {
            this.memoList = memoList;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class MemoUpdateRequestDto {
        private String content;

        @Builder
        public MemoUpdateRequestDto(String content) {
            this.content = content;
        }
    }
}
