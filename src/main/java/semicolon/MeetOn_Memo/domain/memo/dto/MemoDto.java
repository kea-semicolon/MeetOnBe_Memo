package semicolon.MeetOn_Memo.domain.memo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
