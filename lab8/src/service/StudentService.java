package service;

import domain.Student;
import domain.Tema;
import domain.validator.StudentValidator;
import domain.validator.TemaValidator;
import domain.validator.Validator;
import repository.CrudRepository;
import repository.InMemoryRepository;
import utils.ChangeEventType;
import utils.Observable;
import utils.Observer;
import utils.StudentChangeEvent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class StudentService implements Observable<StudentChangeEvent> {

    private CrudRepository <String, Student> repoStudent;

    public StudentService( CrudRepository<String, Student> repoStudent) {
        this.repoStudent = repoStudent;
    }


    /**
     *
     * @param id - id of object I am looking for
     * @return the object with Id = parameter id
     */
    public Student findStudent(String id){
        return repoStudent.findOne(id);
    }

    public Student findStudentByName(String name){
        List<Student> l = allStudents();
        for(Student s:l){
            if(name.equals(s.getNume()))
                return s;
        }
        return null;
    }

    /**
     *
     * @param s
     * @return null if s is added to list or s otherwise
     */
    public Student add(Student s){
        Student student = repoStudent.save(s);
        if(student == null){
            notifyObservers(new StudentChangeEvent(ChangeEventType.ADD,student));
        }
        return student;
    }

    /**
     *
     * @param s
     * @return removed object or null if it is not in list
     */

    public Student remove(Student s){
        Student student = repoStudent.delete(s.getID());
        if(student != null){
            notifyObservers(new StudentChangeEvent(ChangeEventType.DELETE,student));
        }
        return student;
    }

    /**
     *
     * @param s
     * @return null if object is updated or object otherwise
     */

    public Student update(Student s){
        Student student = repoStudent.update(s);
        if(student == null){
            notifyObservers(new StudentChangeEvent(ChangeEventType.UPDATE,student));
        }
        return student;
    }

    /**
     *
     * @return list of students
     */
    public ArrayList<Student> allStudents(){
        //return repoStudent.findAll();
        Iterable <Student> i=repoStudent.findAll();
        ArrayList<Student> l = new ArrayList<>();
        i.forEach(s->l.add(s));
        return l;
    }

    private List<Observer<StudentChangeEvent>> observers = new ArrayList<>();

    @Override
    public void addObserver(Observer<StudentChangeEvent> e){
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<StudentChangeEvent> e){

    }

    @Override
    public void notifyObservers(StudentChangeEvent s){
        observers.stream().forEach(x->x.update(s));
    }

}
