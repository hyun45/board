package com.hyun.board.repository;

import com.hyun.board.dto.BoardDTO;
import com.hyun.board.dto.BoardFileDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepository {
    private final SqlSessionTemplate sql;
//    public void save(BoardDTO boardDTO) {
//        sql.insert("Board.save", boardDTO); // 하나의 파라미터만 가능
//    }
    public BoardDTO save(BoardDTO boardDTO){
        sql.insert("Board.save", boardDTO);
        return boardDTO;
    }

    public List<BoardDTO> findAll() {
        return sql.selectList("Board.findAll");
    }

    public void updateHits(Long id) {
        sql.update("Board.updateHits", id);
    }

    public BoardDTO findById(Long id) {
        return sql.selectOne("Board.findById", id);
    }

    public void update(BoardDTO boardDTO) {
        sql.update("Board.update", boardDTO);
    }

    public void delete(Long id) {
        sql.delete("Board.delete", id);
    }

    public void saveFile(BoardFileDTO boardFileDTO) {
        sql.insert("Board.saveFile", boardFileDTO);
    }

    // 단일 파일의 경우
//    public BoardFileDTO findFile(Long id) {
//        return sql.selectOne("Board.findFile", id);
//    }

    public List<BoardFileDTO> findFile(Long id) {
        return sql.selectList("Board.findFile", id);
    }
}
