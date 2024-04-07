package semicolon.MeetOn_Memo.domain.memo.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import semicolon.MeetOn_Memo.domain.memo.application.MemoService;
import static semicolon.MeetOn_Memo.domain.memo.dto.MemoDto.*;

@Slf4j
@RestController
@RequestMapping("/memo")
@RequiredArgsConstructor
public class MemoController {

    private final MemoService memoService;

    /**
     * 메모 작성
     * @param memoSaveRequestDto
     * @param request
     * @return
     */
    @PostMapping
    public ResponseEntity<String> saveMemo(@RequestBody MemoSaveRequestDto memoSaveRequestDto, HttpServletRequest request) {
        Long saveId = memoService.saveMemo(memoSaveRequestDto, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveId + " Created");
    }

    /**
     * 메모 확인
     * @param memoId
     * @return
     */
    @GetMapping("/info")
    public ResponseEntity<MemoDetailResponseDto> memoDetail(@RequestParam Long memoId) {
        return ResponseEntity.ok(memoService.getMemoDetail(memoId));
    }

    /**
     * 메모 페이지 리스트
     * @param pageable
     * @param request
     * @return
     */
    @GetMapping
    public ResponseEntity<Page<MemoPageResponseDto>> memoList(Pageable pageable, HttpServletRequest request) {
        return ResponseEntity.ok(memoService.getMemoPageList(pageable, request));
    }
}
