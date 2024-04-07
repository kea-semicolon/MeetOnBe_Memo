package semicolon.MeetOn_Memo.domain.memo.application;

import jakarta.servlet.http.Cookie;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockCookie;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.transaction.annotation.Transactional;
import semicolon.MeetOn_Memo.domain.memo.dao.MemoRepository;
import semicolon.MeetOn_Memo.domain.memo.domain.Memo;
import semicolon.MeetOn_Memo.domain.memo.dto.MemoDto;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static semicolon.MeetOn_Memo.domain.memo.dto.MemoDto.*;

@Slf4j
@SpringBootTest
@Transactional
public class MemoServiceTest {

    @MockBean
    MemoRepository memoRepository;

    @Autowired
    MemoService memoService;

    @MockBean
    MemoMemberService memoMemberService;

    MockHttpServletRequest request;
    MockHttpServletResponse response;

    @BeforeEach
    void 세팅() {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        request.addHeader("Authorization", "Bearer test-token");
        createSetCookie("memberId", String.valueOf(1L));
    }

    @Test
    void 메모_작성() {
        //given
        MemoSaveRequestDto memoSaveRequestDto = new MemoSaveRequestDto("testContent");
        Memo memo = Memo.builder().id(1L).content(memoSaveRequestDto.getContent()).build();

        //when
        when(memoMemberService.memberExist(1L, request.getHeader("Authorization"))).thenReturn(true);
        when(memoRepository.save(any(Memo.class))).thenReturn(memo);
        Long result = memoService.saveMemo(memoSaveRequestDto, request);

        //then
        assertThat(result).isEqualTo(memo.getId());
    }

    @Test
    void 메모_확인() {
        //given
        Memo memo = Memo.builder().id(1L).content("testContent").memberId(1L).build();

        //when
        when(memoRepository.findById(memo.getId())).thenReturn(Optional.of(memo));
        MemoDetailResponseDto memoDetail = memoService.getMemoDetail(memo.getId());

        //then
        assertThat(memoDetail.getContent()).isEqualTo(memo.getContent());
    }

    @Test
    void 메모_목록() {
        //given
        Pageable pageable = PageRequest.of(0, 10);
        Long memberId = 1L;
        List<Memo> memos = List.of(
                new Memo(1L, "test1", 1L),
                new Memo(2L, "test2", 1L)
        );
        List<MemoPageResponseDto> memoPageResponseDtos = List.of(
                new MemoPageResponseDto(1L, null),
                new MemoPageResponseDto(2L, null)
        );
        Page<Memo> memoPage = new PageImpl<>(memos, pageable, memos.size());

        //when
        when(memoRepository.findAllByMemberId(memberId, pageable)).thenReturn(memoPage);
        Page<MemoPageResponseDto> memoPageList = memoService.getMemoPageList(pageable, request);

        assertThat(memoPageList.getContent().size()).isEqualTo(memoPageResponseDtos.size());
    }

    @Test
    void 메모_수정() {
        //given
        Memo memo = new Memo(1L, "test", 1L);
        MemoUpdateRequestDto updateRequestDto = new MemoUpdateRequestDto("update-test");

        //when
        when(memoRepository.findById(1L)).thenReturn(Optional.of(memo));
        memoService.updateMemo(1L, updateRequestDto);

        assertThat(memo.getContent()).isEqualTo(updateRequestDto.getContent());
    }

    private void createSetCookie(String name, String value) {
        MockCookie mockCookie = new MockCookie(name, value);
        mockCookie.setPath("/");
        mockCookie.setHttpOnly(true);
        response.addCookie(mockCookie);
        Cookie[] cookies = response.getCookies();
        request.setCookies(cookies);
    }
}
