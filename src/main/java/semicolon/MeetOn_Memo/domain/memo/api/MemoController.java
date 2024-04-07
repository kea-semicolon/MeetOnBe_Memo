package semicolon.MeetOn_Memo.domain.memo.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import semicolon.MeetOn_Memo.domain.memo.application.MemoService;
import semicolon.MeetOn_Memo.domain.memo.dto.MemoDto;

import static semicolon.MeetOn_Memo.domain.memo.dto.MemoDto.*;

@Slf4j
@RestController
@RequestMapping("/memo")
@RequiredArgsConstructor
public class MemoController {

    private final MemoService memoService;

    @PostMapping
    public ResponseEntity<String> saveMemo(@RequestBody MemoSaveRequestDto memoSaveRequestDto, HttpServletRequest request) {
        Long saveId = memoService.saveMemo(memoSaveRequestDto, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveId + " Created");
    }
}
