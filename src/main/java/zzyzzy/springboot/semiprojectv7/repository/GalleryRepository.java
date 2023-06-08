package zzyzzy.springboot.semiprojectv7.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import zzyzzy.springboot.semiprojectv7.model.Gallery;
import zzyzzy.springboot.semiprojectv7.model.GalleryList;
import zzyzzy.springboot.semiprojectv7.model.Pds;

public interface GalleryRepository
                 extends JpaRepository<Gallery, Long> {

    /*@Modifying
    @Transactional
    @Query("update Gallery set views = views + 1 where gno = :gno")
    void countViewById(@Param("gno") long gno);*/

    // JpaRepository를 이용해서 데이터들을 불러와서
    // DTO나 Entity에 결과를 담지 않고
    // 다른 무언가에 담고 싶다면 프로젝션을 이용해야 함!
    // => Gallery와 GalAttach를 조인한 뒤
    // 그 결과들 중 일부만 가져오고 싶다 => 프로젝션 사용
    //@Query(" select g, a from Gallery g " +
    //       " inner join GalAttach a on g.gno = a.gno ")
    //Page<GalleryList> findAllBy(Pageable paging);

}
