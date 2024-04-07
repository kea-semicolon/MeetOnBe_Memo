package semicolon.MeetOn_Memo.domain.memo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import semicolon.MeetOn_Memo.domain.BaseTimeEntity;
import semicolon.MeetOn_Memo.domain.memo.dto.MemoDto;

import static semicolon.MeetOn_Memo.domain.memo.dto.MemoDto.*;

@Getter
@Entity
@NoArgsConstructor
public class Memo extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private Long memberId;

    @Builder
    public Memo(Long id, String content, Long memberId) {
        this.id = id;
        this.content = content;
        this.memberId = memberId;
    }

    public void update(MemoUpdateRequestDto updateRequestDto) {
        this.content = updateRequestDto.getContent();
    }
}
