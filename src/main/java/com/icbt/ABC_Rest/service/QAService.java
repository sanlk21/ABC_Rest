package com.icbt.ABC_Rest.service;

import com.icbt.ABC_Rest.dto.QADto;
import com.icbt.ABC_Rest.entity.QA;
import com.icbt.ABC_Rest.entity.User;
import com.icbt.ABC_Rest.repo.QARepo;
import com.icbt.ABC_Rest.repo.UserRepo;
import com.icbt.ABC_Rest.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QAService {

    private final QARepo qaRepo;
    private final UserRepo userRepo;

    @Autowired
    public QAService(QARepo qaRepo, UserRepo userRepo) {
        this.qaRepo = qaRepo;
        this.userRepo = userRepo;
    }

    @Transactional
    public QADto createQuestion(QADto qadto) {
        User user = userRepo.findById(qadto.getUserEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + qadto.getUserEmail()));

        QA qa = new QA();
        qa.setQuestion(qadto.getQuestion());
        qa.setCreatedAt(LocalDateTime.now());
        qa.setUser(user);

        QA savedQA = qaRepo.save(qa);
        return convertToDTO(savedQA);
    }

    @Transactional
    public QADto answerQuestion(Long id, String answer) {
        QA qa = qaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + id));

        qa.setAnswer(answer);
        qa.setAnsweredAt(LocalDateTime.now());

        QA savedQA = qaRepo.save(qa);
        return convertToDTO(savedQA);
    }

    @Transactional(readOnly = true)
    public List<QADto> getQAByUserEmail(String email) {
        List<QA> qaList = qaRepo.findByUserEmail(email);
        return qaList.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<QADto> getAllQuestions() {
        List<QA> qaList = qaRepo.findAll();
        return qaList.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Transactional
    public void deleteQuestion(Long id) {
        QA qa = qaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + id));
        qaRepo.delete(qa);
    }

    private QADto convertToDTO(QA qa) {
        QADto dto = new QADto();
        dto.setId(qa.getId());
        dto.setQuestion(qa.getQuestion());
        dto.setAnswer(qa.getAnswer());
        dto.setCreatedAt(qa.getCreatedAt());
        dto.setAnsweredAt(qa.getAnsweredAt());
        dto.setUserEmail(qa.getUser().getEmail());
        return dto;
    }
}
