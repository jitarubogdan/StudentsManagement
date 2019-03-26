import domain.Student;
import domain.validator.StudentValidator;
import domain.validator.ValidationException;
import domain.validator.Validator;
import org.junit.Test;
import repository.CrudRepository;
import repository.InMemoryRepository;
import service.StudentService;

class StudentValidatorTest {


    private Validator<Student> vs = new StudentValidator();

    private CrudRepository<String, Student> repoStudent = new InMemoryRepository<>(vs);
    StudentService srv = new StudentService(repoStudent);
    Student s1 = new Student("1p","Jitaru Bogdan","224","jbir2330@scs.ubbcluj.ro");
    Student s2 = new Student("2","Teisanu Ciprian*2","227","tcir2409@scs.ubbcluj.ro");
    Student s3 = new Student("1","Cretu Mihai","228","cmir3647@scs.ubbcluj.ro");
    Student s4 = new Student("4","Popescu Ion","221","piir2g54@sc.ubbcluj.ro");
    Student s5 = new Student("","Pop Dan","222","ghir3463@scs.ubbcluj.ro");

    @Test
    void validate() {
        try{
            srv.add(s1);
            assert(false);
        } catch (ValidationException e) {
            assert(e.getMessage().equals("Id invalid, trebuie sa fie format doar din cifre!\n"));
        }
        try{
            srv.add(s2);
            assert(false);
        } catch (ValidationException e) {
            assert(e.getMessage().equals("Nume invalid, trebuie sa fie format doar din litere!\n"));
        }
        try{
            srv.add(s3);
            assert(false);
        } catch (ValidationException e) {
            assert(e.getMessage().equals("Grupa invalida, trebuie sa fie un numar cuprins intre 221 si 227\n"));
        }
        try{
            srv.add(s4);
            assert(false);
        } catch (ValidationException e) {
            assert(e.getMessage().equals("Email invalid, trebuie sa apartina de scs.ubbcluj.ro\n"));
        }
        try{
            srv.add(s5);
            assert(false);
        } catch (ValidationException e) {
            assert(e.getMessage().equals("Id invalid, trebuie sa fie format doar din cifre!\n"));
        }
    }
}