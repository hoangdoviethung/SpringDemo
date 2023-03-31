package com.hung.service;

import com.hung.domain.Student;
import com.hung.dto.StudentDTO;
import com.hung.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService {
    //private final StudentRepository studentRepository;

    public String save(StudentDTO studentDTO){
        try {
            Student student = new Student();
            BeanUtils.copyProperties(studentDTO, student);
           // studentRepository.save(student);
            int a = 0;
            int b = 1;
            int c = b/a;
        }catch (Exception e){

        }
       return null;
    }
}
