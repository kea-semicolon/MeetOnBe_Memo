package semicolon.MeetOn_Memo.domain.memo.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import semicolon.MeetOn_Memo.domain.memo.dao.MemoRepository;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemoKafkaService {

    private final MemoRepository memoRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final static String MEMBER_DELETED_TOPIC = "member_deleted_topic";

    @Transactional
    @KafkaListener(topics = MEMBER_DELETED_TOPIC, groupId = "member_group")
    public void deleteByMemberDeleted(String memberIdStr) {
        log.info("Member 삭제 memberId={}", memberIdStr);
        Long memberId = Long.valueOf(memberIdStr);
        int c = memoRepository.deleteMemosByMemberId(memberId);
        log.info("메모 {}개 삭제 완료", c);
    }
}
