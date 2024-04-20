package semicolon.MeetOn_Memo.domain.memo.api;

import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "메모 작성", description = "메모 작성, MemoSaveRequestDto")
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
    @Operation(summary = "메모 확인", description = "메모 확인")
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
    @Operation(summary = "메모 리스트", description = "메모 리스트(페이징)")
    @GetMapping
    public ResponseEntity<Page<MemoPageResponseDto>> memoList(Pageable pageable, HttpServletRequest request) {
        return ResponseEntity.ok(memoService.getMemoPageList(pageable, request));
    }

    /**
     * 메모 수정
     * @param memoId
     * @param memoUpdateRequestDto
     * @return
     */
    @Operation(summary = "메모 수정", description = "메모 수정 + MemoUpdateRequestDto")
    @PutMapping
    public ResponseEntity<String> memoUpdate(@RequestParam Long memoId, @RequestBody MemoUpdateRequestDto memoUpdateRequestDto) {
        memoService.updateMemo(memoId, memoUpdateRequestDto);
        return ResponseEntity.ok("success");
    }

    @Operation(summary = "메모 삭제", description = "메모 삭제")
    @DeleteMapping
    public ResponseEntity<String> memoDelete(@RequestParam Long memoId) {
        memoService.deleteMemo(memoId);
        return ResponseEntity.ok("success");
    }
}
