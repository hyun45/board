package com.hyun.board.service;

import com.hyun.board.dto.BoardDTO;
import com.hyun.board.dto.BoardFileDTO;
import com.hyun.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public void save(BoardDTO boardDTO) throws IOException {
        if(boardDTO.getBoardFile().isEmpty()){
            // 첨부 파일이 없는 경우
            boardDTO.setFileAttached(0);
            boardRepository.save(boardDTO);
        } else{
            // 첨부 파일이 있는 경우
            boardDTO.setFileAttached(1);
            // 게시글 저장 후 id 값 활용을 위해 리턴 받음
            BoardDTO savedBoard = boardRepository.save(boardDTO);
            // 파일 가져오기
            MultipartFile boardFile = boardDTO.getBoardFile();
            // 파일명 가져오기
            String originalFileName = boardFile.getOriginalFilename();
            System.out.println("originalFileName = " + originalFileName);
            // 저장시 이름 만들기
            System.out.println(System.currentTimeMillis());
            String storedFileName = System.currentTimeMillis() + "-" + originalFileName;
            System.out.println("storedFileName = " + storedFileName);
            // BoardFileDTO 세팅
            BoardFileDTO boardFileDTO = new BoardFileDTO();
            boardFileDTO.setOriginalFileName(originalFileName);
            boardFileDTO.setStoredFileName(storedFileName);
            boardFileDTO.setBoardId(savedBoard.getId());
            // 파일 저장용 폴더에 저장 처리
            String savePath = "C:/Users/hsb97/Desktop/board_git/upload_files/" + storedFileName;
            boardFile.transferTo(new File(savePath));
            // board_file_table 저장 처리
            boardRepository.saveFile(boardFileDTO);
        }
    }

    public List<BoardDTO> findAll() {
        return boardRepository.findAll();
    }

    public void updateHits(Long id) {
        boardRepository.updateHits(id);
    }

    public BoardDTO findById(Long id) {
        return boardRepository.findById(id);
    }

    public void update(BoardDTO boardDTO) {
        boardRepository.update(boardDTO);
    }

    public void delete(Long id) {
        boardRepository.delete(id);
    }

    public BoardFileDTO findFile(Long id) {
        return boardRepository.findFile(id);
    }
}
