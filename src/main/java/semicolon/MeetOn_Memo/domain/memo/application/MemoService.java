package semicolon.MeetOn_Memo.domain.memo.application;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import semicolon.MeetOn_Memo.domain.memo.dao.MemoRepository;
import semicolon.MeetOn_Memo.domain.memo.domain.Memo;
import semicolon.MeetOn_Memo.domain.memo.dto.MemoDto;
import semicolon.MeetOn_Memo.global.exception.BusinessLogicException;
import semicolon.MeetOn_Memo.global.exception.code.ExceptionCode;
import semicolon.MeetOn_Memo.global.util.CookieUtil;

import static semicolon.MeetOn_Memo.domain.memo.dto.MemoDto.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemoService {

    private final MemoRepository memoRepository;
    private final CookieUtil cookieUtil;
    private final MemoMemberService memoMemberService;

    @Transactional
    public Long saveMemo(MemoSaveRequestDto memoSaveRequestDto, HttpServletRequest request) {
        Long memberId = Long.valueOf(cookieUtil.getCookieValue("memberId", request));
        if(!memoMemberService.memberExist(memberId, request.getHeader("Authorization"))){
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
        }

        Memo memo = Memo.builder().content(memoSaveRequestDto.getContent()).memberId(memberId).build();
        Memo save = memoRepository.save(memo);
        return save.getId();
    }
}
