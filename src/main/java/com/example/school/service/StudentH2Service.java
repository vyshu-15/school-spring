package com.example.school.service;

import com.example.school.model.Student;
import com.example.school.model.StudentRowMapper;
import com.example.school.repository.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

@Service
public class StudentH2Service implements StudentRepository {

  private static final String studentData = null;
  @Autowired
  private JdbcTemplate db;

  @Override
  public ArrayList<Student> getStudent() {
    List<Student> students = db.query("select * from student", new StudentRowMapper());

    ArrayList<Student> student = new ArrayList<>(studentData);
    return student;

  }

  @Override
  public Student getStudentById(int studentId) {
    try {
      Student student = db.queryForObject("select * from student where studentId = ", new StudentRowMapper(),
          studentId);
      return student;
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
  }

  @Override
  public Student addStudent(Student student) {
    db.update("insert into student(studentName , Gender, Standard) value (?,?,?)", student.getStudentName(),
        student.getGenderd(), student.getStandard());
    Student.savedStudent = db.queryForObject("select * fom student where studentName = ? and Gender= ? and Standard =?",
        new StudentRowMapper(),
        student.getStudentName(), student.getGenderd(), student.getStandard());
    return savedStudent;
  }

  @Override
      public String addMultipleStudents(ArrayList<Student> studentsList){

        for (Student eachStudent : studentsList){
         
         db.update("insert into studnt(studentName, gender,standard) values (?,?,?)" , eachStudent.getStudentName(),
          eachStudent.getGenderd(),eachStudent.getStandard());

        }
        String responseMessage = String.format("successfully added %d students" , studentsList.size();
        return responseMessage;
      }

  @Override
  public void deleteStudent(int studentId) {
    db.update("delete from student where studentId =  ?", studentId);
  }

  @Override
  public Student updateStudent(int studentId, Student student) {
    if (student.getStudentName() != null) {
      db.update("update student set studentName = ? where studentId = ?", student.getStudentName(), studentId);

    }
    if (student.getGenderd() != null) {
      db.update("update student set Gender = ? where studentId = ?", student.getGenderd(), studentId);

    }
    if (student.getStandard() != 0) {
      db.update("update student set Standard = ? where studentId = ?", student.getStandard(), studentId);

    }
  }
}

}

}
