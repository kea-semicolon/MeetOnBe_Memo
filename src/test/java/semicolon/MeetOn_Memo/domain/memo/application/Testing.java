package semicolon.MeetOn_Memo.domain.memo.application;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import semicolon.MeetOn_Memo.domain.memo.domain.Memo;
import semicolon.MeetOn_Memo.domain.memo.dto.MemoDto;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Testing {

    @Test
    void For_VS_Stream() {
        //given
        Long dataSize = 100000L;
        List<Memo> memoList = new ArrayList<>();
        for(int i=0;i<dataSize;i++){
            memoList.add(new Memo(1L, "test" + i, 1L));
        }

        List<MemoDto.MemoDetailResponseDto> forList = new ArrayList<>();
        List<MemoDto.MemoDetailResponseDto> streamList = new ArrayList<>();

        Long start = System.nanoTime();
        for(Memo m : memoList){
            forList.add(MemoDto.MemoDetailResponseDto.memoDetailResponseDto(m));
        }
        Long end = System.nanoTime();
        log.info("For = {}", end-start + "ns");

        start = System.nanoTime();
        streamList = memoList.stream().map(MemoDto.MemoDetailResponseDto::memoDetailResponseDto).toList();
        end = System.nanoTime();
        log.info("Stream = {}", end-start + "ns");
    }
}
