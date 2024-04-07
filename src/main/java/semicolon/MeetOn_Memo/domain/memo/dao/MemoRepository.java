package semicolon.MeetOn_Memo.domain.memo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import semicolon.MeetOn_Memo.domain.memo.domain.Memo;

public interface MemoRepository extends JpaRepository<Memo, Long> {
}
