package zzyzzy.springboot.semiprojectv7.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import zzyzzy.springboot.semiprojectv7.model.Board;
import zzyzzy.springboot.semiprojectv7.repository.BoardRepository;

import javax.transaction.TransactionScoped;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("bddao")
public class BoardDAOImpl implements BoardDAO {

    @Autowired
    BoardRepository boardRepository;

    @Override
    public Map<String, Object> selectBoard(int cpage) {

        Pageable paging = PageRequest.of(cpage, 25, Sort.Direction.DESC, "bno");

        Map<String, Object> bds = new HashMap<>();
        bds.put("bdlist", boardRepository.findAll(paging).getContent());
        bds.put("cntpg", boardRepository.findAll(paging).getTotalPages());

        return bds;
    }

    @Override
    public Map<String, Object> selectBoard(Map<String, Object> params) {

        // like 검색에 대한 query method
        // findByTitleLike      : %검색어% (% 문자제공 필요)
        // findByTitleContains  : %검색어% (% 문자제공 필요x)
        // findByTitleStartsWith  : 검색어%  (% 문자제공 필요x)
        // findByTitleEndsWith    : %검색어  (% 문자제공 필요x)

        String ftype = params.get("ftype").toString();
        String fkey = params.get("fkey").toString();
        int cpage = (int) params.get("stbno");

        Pageable paging = PageRequest.of(cpage, 25, Sort.Direction.DESC, "bno");

        Page<Board> result = null;
        switch (ftype) {
            case "title":// 제목으로 검색
                result = boardRepository.findByTitleContains(paging, fkey); break;

            case "titcont":// 제목+본문으로 검색
                result = boardRepository.findByTitleContainsOrContentContains(paging, fkey, fkey); break;

            case "userid":// 작성자로 검색
                result = boardRepository.findByUserid(paging, fkey); break;

            case "content":// 본문으로 검색
                result = boardRepository.findByContentContains(paging, fkey);
        }

        Map<String, Object> bds = new HashMap<>();
        bds.put("bdlist", result.getContent());
        bds.put("cntpg", result.getTotalPages());

        return bds;
    }

    @Override
    public int insertBoard(Board bd) {
        return Math.toIntExact(boardRepository.save(bd).getBno());
    }

    @Override
    public Board selectOneBoard(int bno) {
        boardRepository.countViewBoard((long) bno);
        return boardRepository.findById((long) bno).get();
    }

}
