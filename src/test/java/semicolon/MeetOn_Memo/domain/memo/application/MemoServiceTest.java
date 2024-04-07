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
import org.springframework.mock.web.MockCookie;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.transaction.annotation.Transactional;
import semicolon.MeetOn_Memo.domain.memo.dao.MemoRepository;
import semicolon.MeetOn_Memo.domain.memo.domain.Memo;
import semicolon.MeetOn_Memo.domain.memo.dto.MemoDto;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        MemoDto.MemoSaveRequestDto memoSaveRequestDto = new MemoDto.MemoSaveRequestDto("testContent");
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
        MemoDto.MemoDetailResponseDto memoDetail = memoService.getMemoDetail(memo.getId());

        //then
        assertThat(memoDetail.getContent()).isEqualTo(memo.getContent());
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
