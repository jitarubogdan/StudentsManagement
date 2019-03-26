import domain.Tema;
import domain.validator.TemaValidator;
import domain.validator.ValidationException;
import domain.validator.Validator;
import org.junit.jupiter.api.Test;
import repository.CrudRepository;
import repository.InMemoryRepository;
import service.StudentService;
import service.TemaService;

class TemaValidatorTest {

    private Validator<Tema> vt = new TemaValidator();
    private CrudRepository<String, Tema> repoTema = new InMemoryRepository<>(vt);
    TemaService srv = new TemaService(repoTema);
    Tema t1 = new Tema("1g","Evidenta","3","2");
    Tema t2 = new Tema("2","Fisiere*#d","4","2");
    Tema t3 = new Tema("3","Fisiere","15","14");
    Tema t4 = new Tema("4","Fisiere","4","0");
    Tema t5 = new Tema("5","Fisiere","10","17");

    @Test
    void validate() {
        try{
            srv.addTema(t1);
            assert(false);
        } catch (ValidationException e) {
            assert(e.getMessage().equals("Id invalid, trebuie sa fie format doar din cifre!\n"));
        }
        try{
            srv.addTema(t2);
            assert(false);
        } catch (ValidationException e) {
            assert(e.getMessage().equals("Descriere invalida, trebuie sa fie formata doar din litere!\n"));
        }
        try{
            srv.addTema(t3);
            assert(false);
        } catch (ValidationException e) {
            assert(e.getMessage().equals("Deadline invalid, trebuie sa fie un numar cuprins intre 1 si 14\n"));
        }
        try{
            srv.addTema(t4);
            assert(false);
        } catch (ValidationException e) {
            assert(e.getMessage().equals("Receive invalid, trebuie sa fie un numar cuprins intre 1 si 14\nIntre deadline si receive trebuie sa fie maximum doua saptamani\n"));
        }
        try{
            srv.addTema(t5);
            assert(false);
        } catch (ValidationException e) {
            assert(e.getMessage().equals("Receive invalid, trebuie sa fie un numar cuprins intre 1 si 14\nDeadline-ul trebuie sa fie mai mare decat data primirii\n"));
        }
    }
}