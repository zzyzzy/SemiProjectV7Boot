package zzyzzy.springboot.semiprojectv7.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import zzyzzy.springboot.semiprojectv7.model.Board;

import java.util.List;

//public interface BoardRepository extends JpaRepository<Board, Long> {
public interface BoardRepository
        extends PagingAndSortingRepository<Board, Long> {

    // JpaRepository 에서는 DML은 지원 X
    // 단, Modifying, Transactional등을 추가하면 사용가능
    @Modifying
    @Transactional
    @Query("update Board set views = views + 1 where bno = :bno")
    int countViewBoard(@Param("bno") long bno);

    //@Query("select ceil(count(bno)/25) from Board")
    //int countBoardBy();

    Page<Board> findByTitleContains(Pageable paging, String fkey);
    Page<Board> findByTitleContainsOrContentContains(Pageable paging, String fkey1, String fkey2);
    Page<Board> findByUserid(Pageable paging, String fkey);
    Page<Board> findByContentContains(Pageable paging, String fkey);

    //int countByTitleContains(String fkey);
    //int countByTitleContainsOrContentContains(String fkey1, String fkey2);
    //int countByUserid(String fkey);
    //int countByContentContains(String fkey);

    //@Query("select bno,title,userid,regdate,thumbs,views from board where :ftype = :fkey", nativeQuery = true)
    //List<Board> findBoardBy(Pageable paging, String ftype, String fkey);
}
