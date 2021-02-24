package com.gabrielduarte.luckyexperiment.controller;

import com.gabrielduarte.luckyexperiment.domain.StudentEntity;
import com.gabrielduarte.luckyexperiment.response.StudentResponse;
import com.gabrielduarte.luckyexperiment.service.StudentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @PostMapping("/create-all")
    public void createStudents() {
        service.create();
    }

    @GetMapping("/meritocracy")
    public List<StudentEntity> getOnlyMeritocracy() {
        return service.getListByMeritocracy();
    }

    @GetMapping("/lucky")
    public List<StudentResponse> getListWithLuck() {
        return service.getListWithLuck();
    }

}
