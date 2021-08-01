package com.hillel.students.controller;

import com.hillel.students.model.Student;
import com.hillel.students.model.StudentRepository;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class StudentController {

  StudentRepository studentRepository;

  public StudentController(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
  }

  @GetMapping("/add")
  public String addStudentForm(Student student) {
    return "add-student";
  }

  @PostMapping("/add")
  public String addStudent(@Valid Student student,
      BindingResult result, Model model) {

    if (result.hasErrors()) {
      return "add-student";
    }

    studentRepository.save(student);
    return "redirect:/";

  }

  @GetMapping("/")
  public String students(Model model) {
    model.addAttribute("students", studentRepository.findAll());
    return "students";
  }

  @GetMapping("/delete/{id}")
  public String delete(@PathVariable("id") long id, Model model) {

    Student student = studentRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("User not found: " + id));

    studentRepository.delete(student);
    return "redirect:/";

  }

}
