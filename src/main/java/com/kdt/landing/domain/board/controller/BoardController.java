package com.kdt.landing.domain.board.controller;

import com.kdt.landing.domain.board.entity.Board;
import com.kdt.landing.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/boardfile")
    public ResponseEntity<?> createBoard(@Validated @RequestParam("files") List<MultipartFile> multipartFiles) throws Exception {
        boardService.addBoardFile(Board.builder()
                .build(), multipartFiles);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/boardfile")
    public String getBoard(@RequestParam long id) {
        Board board = boardService.findBoard(id).orElseThrow(RuntimeException::new);
        String imgPath = board.getStoredFileName();
        log.info(imgPath);
        return "<img src=" + imgPath + ">";
    }


}
