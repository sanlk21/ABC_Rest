package com.icbt.ABC_Rest.controller;

import com.icbt.ABC_Rest.dto.QADto;
import com.icbt.ABC_Rest.service.QAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/qa")
@CrossOrigin(origins = "http://127.0.0.1:5501")
public class QAController {

    @Autowired
    private QAService qaService;

    @PostMapping("/question")
    public ResponseEntity<QADto> createQuestion(@RequestBody QADto qadto) {
        try {
            QADto createdQA = qaService.createQuestion(qadto);
            return ResponseEntity.ok(createdQA);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/answer/{id}")
    public ResponseEntity<?> answerQuestion(@PathVariable Long id, @RequestBody String answer) {
        try {
            QADto updatedQA = qaService.answerQuestion(id, answer);
            return ResponseEntity.ok(updatedQA);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Question not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating answer");
        }
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<List<QADto>> getQAByUserEmail(@PathVariable String email) {
        try {
            List<QADto> qaList = qaService.getQAByUserEmail(email);
            return ResponseEntity.ok(qaList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<QADto>> getAllQuestions() {
        try {
            List<QADto> qaList = qaService.getAllQuestions();
            return ResponseEntity.ok(qaList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
