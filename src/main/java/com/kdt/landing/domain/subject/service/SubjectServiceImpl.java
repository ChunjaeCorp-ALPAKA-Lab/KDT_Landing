package com.kdt.landing.domain.subject.service;

import com.kdt.landing.domain.subject.dto.SubjectDTO;
import com.kdt.landing.domain.subject.entity.Subject;
import com.kdt.landing.domain.subject.repository.SubjectRepository;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectRepository subFullStackRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<SubjectDTO> findAll() throws Exception {
        List<Subject> subFullStackList = subFullStackRepository.findAll();
        List<SubjectDTO> subFullStackDTOList = subFullStackList.stream().map(
                        subFullStack -> modelMapper.map(subFullStack, SubjectDTO.class))
                .collect(Collectors.toList());
        return subFullStackDTOList;
    }

    @Override
    public SubjectDTO findById(Long no) throws Exception {
        Optional<Subject> subFullStack = subFullStackRepository.findById(no);
        SubjectDTO subFullStackDTO = modelMapper.map(subFullStack, SubjectDTO.class);
        return subFullStackDTO;
    }

    @Override
    public void register(SubjectDTO subFullStackDTO) throws Exception {
        String emailEn = subFullStackDTO.getEmail();
        String telEn = subFullStackDTO.getTel();
        subFullStackDTO.setEmail(emailEn);
        subFullStackDTO.setTel(telEn);
        Subject subFullStack = modelMapper.map(subFullStackDTO, Subject.class);
        subFullStackRepository.save(subFullStack);
    }

    @Override
    public void modify(SubjectDTO subFullStackDTO) throws Exception {
        Subject subFullStack = modelMapper.map(subFullStackDTO, Subject.class);
        subFullStackRepository.save(subFullStack);
    }

    @Override
    public boolean emailCheck(String email) throws Exception {
        return false;
    }

    // 각 게시판 과정 마다 정렬 버튼 생성용
    @Override
    public List<SubjectDTO> getFullStackSubjects() {
        List<Subject> subFullStackSortList = subFullStackRepository.findByFullStackSort("FULLSTACK");
        return subFullStackSortList.stream()
                .map(this::convertFullStackToDTO)
                .collect(Collectors.toList());
    }

    private SubjectDTO convertFullStackToDTO(Subject subject) {
        return modelMapper.map(subject, SubjectDTO.class);
    }

    @Override
    public List<SubjectDTO> getPMSubjects() {
        List<Subject> subPMSortList = subFullStackRepository.findByPMSort("PM");
        return subPMSortList.stream()
                .map(this::convertToPMDTO)
                .collect(Collectors.toList());
    }

    private SubjectDTO convertToPMDTO(Subject subject) {
        return modelMapper.map(subject, SubjectDTO.class);
    }

    @Override
    public List<SubjectDTO> getBigDataSubjects() {
        List<Subject> subBigDataSortList = subFullStackRepository.findByBigDataSort("BIGDATA");
        return subBigDataSortList.stream()
                .map(this::convertToBigDataDTO)
                .collect(Collectors.toList());
    }

    private SubjectDTO convertToBigDataDTO(Subject subject) {
        return modelMapper.map(subject, SubjectDTO.class);
    }

}
