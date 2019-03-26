package repository;

import domain.Student;
import domain.validator.Validator;

import java.util.List;

public class InFileStudentRepository extends AbstractFileRepository<String, Student> {

    public InFileStudentRepository(String fileName, Validator<Student> validator) {
        super(fileName, validator);
    }

    @Override
    public Student extractEntity(List<String> attr) {
        String id = attr.get(0).split("=")[1];
        String nume = attr.get(1).split("=")[1];
        String grupa = attr.get(2).split("=")[1];
        String email = attr.get(3).split("=")[1];
        Student s = new Student(id,nume,grupa,email);
        return s;
    }
}
