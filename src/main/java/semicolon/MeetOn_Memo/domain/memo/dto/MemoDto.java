package semicolon.MeetOn_Memo.domain.memo.dto;

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
}
