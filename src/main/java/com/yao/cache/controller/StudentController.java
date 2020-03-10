package com.yao.cache.controller;

import com.yao.cache.entity.Student;
import com.yao.cache.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stu")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/get/{id}")
    public Student getById(@PathVariable("id") Integer id) {
        Student byId = studentService.getById(id);
        return byId;

    }
    @GetMapping("/get/name/{name}")
    public Student getByName( @PathVariable("name") String name) {
        Student byName = studentService.getByName(name);
        return byName;

    }

    @RequestMapping("/update")
    public Student UpdateById(Student student) {
        Student stu = studentService.updateStu(student);
        return stu;

    }
    @RequestMapping("/del")
    public  String del(Integer id){
        studentService.delStu(id);
        return "success";
    }


}
