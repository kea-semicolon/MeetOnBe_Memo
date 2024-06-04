package semicolon.MeetOn_Memo.domain.memo.application;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import semicolon.MeetOn_Memo.domain.memo.dao.MemoRepository;
import semicolon.MeetOn_Memo.domain.memo.domain.Memo;
import semicolon.MeetOn_Memo.domain.memo.dto.MemoDto;
import semicolon.MeetOn_Memo.global.exception.BusinessLogicException;
import semicolon.MeetOn_Memo.global.exception.code.ExceptionCode;
import semicolon.MeetOn_Memo.global.util.CookieUtil;

import java.util.List;

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

    public MemoDetailResponseDto getMemoDetail(Long memoId) {
        Memo memo = findMemo(memoId);
        return MemoDetailResponseDto.memoDetailResponseDto(memo);
    }

    public List<MemoPageResponseDto> getMemoPageList(Pageable pageable, HttpServletRequest request) {
        Long memberId = Long.valueOf(cookieUtil.getCookieValue("memberId", request));
        Page<Memo> allByMemberId = memoRepository.findAllByMemberId(memberId, pageable);
        List<MemoPageResponseDto> result = allByMemberId.getContent().stream()
                .map(memo ->
                        MemoPageResponseDto
                                .builder()
                                .memoId(memo.getId()).createdDate(memo.getCreatedAt()).content(memo.getContent())
                                .build()
                ).toList();

        //return new PageImpl<>(result, pageable, allByMemberId.getTotalPages());
        return result;
    }

    @Transactional
    public void updateMemo(Long memoId, MemoUpdateRequestDto memoUpdateRequestDto) {
        Memo memo = findMemo(memoId);
        memo.update(memoUpdateRequestDto);
    }

    @Transactional
    public void deleteMemo(Long memoId) {
        Memo memo = findMemo(memoId);
        memoRepository.delete(memo);
    }

    private Memo findMemo(Long memoId) {
        return memoRepository.findById(memoId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMO_NOT_FOUND));
    }
}
