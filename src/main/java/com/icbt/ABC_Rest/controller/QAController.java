package com.icbt.ABC_Rest.controller;

import com.icbt.ABC_Rest.dto.QADto;
import com.icbt.ABC_Rest.service.QAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/qa")
@CrossOrigin(origins = "http://127.0.0.1:5501")

public class QAController {

    @Autowired
    private QAService qaService;

    @PostMapping("/question")
    public ResponseEntity<QADto> createQuestion(@RequestBody QADto qadto) {
        QADto createdQA = qaService.createQuestion(qadto);
        return ResponseEntity.ok(createdQA);
    }

    @PutMapping("/answer/{id}")
    public ResponseEntity<QADto> answerQuestion(@PathVariable Long id, @RequestBody String answer) {
        QADto updatedQA = qaService.answerQuestion(id, answer);
        return ResponseEntity.ok(updatedQA);
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<List<QADto>> getQAByUserEmail(@PathVariable String email) {
        List<QADto> qaList = qaService.getQAByUserEmail(email);
        return ResponseEntity.ok(qaList);
    }
}