package com.kdt.landing.domain.board.handler;

import com.kdt.landing.domain.board.entity.Board;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class FileHandler {

    public List<Board> parseFileInfo(
            Long boardID,
            List<MultipartFile> multipartFiles
    ) throws Exception {

        // 반환할 파일 리스트
        List<Board> fileList = new ArrayList<>();

        // 빈 파일이 들어오면 빈 것을 반환
        if (multipartFiles.isEmpty()) {
            return fileList;
        }

        // 파일 이름을 업로드 한 날짜로 변경해서 저장하는 로직
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String current_date = simpleDateFormat.format(new Date());

        // 빈 파일이 들어오면 빈 것을 반환
        if(multipartFiles.isEmpty()) {
            return fileList;
        }

        // 프로젝트 폴더에 저장하기 위해 절대경로를 설정(Window 의 Tomcat 은 Temp 파일을 이용한다)
        String absolutePath = new File("").getAbsolutePath() + "\\";

        // 저장 경로 지정
        String path = "images/" + current_date;
        File file = new File(path);

        // 저장할 위치의 디렉토리가 존재하지 않을 경우
        if(!file.exists()) {
            // 상위 디렉토리를 생성
            file.mkdirs();
        }

        for (MultipartFile multipartFile : multipartFiles) {
            // 파일이 비어 있지 않을 때 작업을 시작해야 함 - 오류 방지
            if (!multipartFile.isEmpty()) {

                // jpeg, png, gif 파일들만 받아서 처리할 예정
                String contentType = multipartFile.getContentType();
                String originFileExtension;

                // 확장자 명이 없을 경우 오류 발생
                if (ObjectUtils.isEmpty(contentType)) {
                    break;
                } else {
                    if (contentType.contains("image/jpeg")) {
                        originFileExtension = ".jpg";
                    } else if (contentType.contains("image/png")) {
                        originFileExtension = ".png";
                    } else if (contentType.contains("image/gif")) {
                        originFileExtension = ".gif";
                    // 다른 파일 명일 시 조용....
                    } else {
                        break;
                    }
                }

                // 각 파일 이름이 겹치지 않도록 나노초까지 동원해서 지정
                String new_file_name = System.nanoTime() + originFileExtension;

                // builder 패턴으로 생성 후 리스트에 추가
                Board board = Board.builder()
                        .boardIdx(boardID)
                        .originFileName(multipartFile.getOriginalFilename())
                        .storedFileName(path + "/" + new_file_name)
                        .fileSize(multipartFile.getSize())
                        .build();
                fileList.add(board);

                // 저장된 파일로 변경하여 보여줌
                file = new File(absolutePath + path + "/" + new_file_name);
                multipartFile.transferTo(file);
            }
        }
        return fileList;
    }
}
