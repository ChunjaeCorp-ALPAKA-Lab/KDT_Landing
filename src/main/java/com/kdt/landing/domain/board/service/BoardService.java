package com.kdt.landing.domain.board.service;

import com.kdt.landing.domain.board.handler.FileHandler;
import com.kdt.landing.domain.board.entity.Board;
import com.kdt.landing.domain.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final FileHandler fileHandler;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
        this.fileHandler = new FileHandler();
    }

    public Board addBoardFile(
            Board board,
            List<MultipartFile> files
    ) throws Exception {
        // 파일을 저장하고 그 Board에 대한 List를 소유
        List<Board> boardList = fileHandler.parseFileInfo(board.getId(), files);

        if (boardList.isEmpty()) {
            // 파일이 없을 때 로직
            System.out.println("파일이 없습니다.");

        // 파일에 대해 DB에 저장하고 가지고 있을 것
        } else {
            List<Board> pictureBeans = new ArrayList<>();
            for (Board board1 : boardList) {
                pictureBeans.add(boardRepository.save(board1));
            }
        }

        return boardRepository.save(board);
    }

    public List<Board> findBoards() {
        return boardRepository.findAll();
    }

    public Optional<Board> findBoard(Long id) {
        return boardRepository.findById(id);
    }
}
