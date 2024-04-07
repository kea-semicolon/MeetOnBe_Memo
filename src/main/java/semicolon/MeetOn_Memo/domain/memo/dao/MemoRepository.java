package semicolon.MeetOn_Memo.domain.memo.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import semicolon.MeetOn_Memo.domain.memo.domain.Memo;

public interface MemoRepository extends JpaRepository<Memo, Long> {

    Page<Memo> findAllByMemberId(Long memberId, Pageable pageable);
}
