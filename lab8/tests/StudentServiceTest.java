import domain.Nota;
import domain.Student;
import domain.Tema;
import domain.validator.*;
import org.junit.jupiter.api.Test;
import repository.CrudRepository;
import repository.InFileNotaRepository;
import repository.InFileStudentRepository;
import repository.InMemoryRepository;
import service.NotaService;
import service.StudentService;
import service.TemaService;

import java.util.ArrayList;

class StudentServiceTest {

    static Validator<Student> vs = new StudentValidator();
    static Validator<Tema> vt = new TemaValidator();
    static Validator<Nota> vn = new NotaValidator();

    private CrudRepository <String, Student> repoStudent = new InMemoryRepository<>(vs);
    private CrudRepository <String, Tema> repoTema = new InMemoryRepository<>(vt);
    private CrudRepository <String, Nota> repoNota = new InMemoryRepository<>(vn);

    StudentService srvS =new StudentService(repoStudent);
    TemaService srvT = new TemaService(repoTema);
    NotaService srvN = new NotaService(repoNota);

    Student s1 = new Student("1","Jitaru Bogdan","224","jbir2330@scs.ubbcluj.ro");
    Student s2 = new Student("2","Teisanu Ciprian","227","tcir2409@scs.ubbcluj.ro");
    Student s3 = new Student("1","Cretu Mihai","223","cmir3647@scs.ubbcluj.ro");
    Student s4 = new Student("4","Popescu Ion","221","piir2854@scs.ubbcluj.ro");
    Tema t1 = new Tema("1","Evidenta","3","2");
    Tema t2 = new Tema("2","Fisiere","5","3");
    Nota n1 = new Nota("1","1",(float)10,3);
    Nota n2 = new Nota("1","2",(float) 9,6);
    Nota n3 = new Nota("2","2",(float)10,8);

    @Test
    void add() {
        ArrayList<Student> l;
        l=srvS.allStudents();
        Student aux;
        assert(l.size()==0);
        aux = srvS.add(s1);
        l=srvS.allStudents();
        assert(l.size()==1);
        assert(aux==null);
        Student aux2 = srvS.add(s1);
        assert(aux2==s1);
    }

    @Test
    void remove() {
        ArrayList<Student> l;
        Student aux;
        srvS.add(s1);
        l=srvS.allStudents();
        assert(l.size()==1);
        aux=srvS.remove(s1);
        l=srvS.allStudents();
        assert(l.size()==0);
        assert(aux==s1);
        aux=srvS.remove(s1);
        assert(aux==null);
    }

    @Test
    void update() {
        ArrayList<Student> l;
        Student aux;
        srvS.add(s1);
        l=srvS.allStudents();
        assert(!l.contains(s3));
        aux = srvS.update(s3);
        assert(aux==null);
        l=srvS.allStudents();
        assert(l.contains(s3));
        aux = srvS.update(s2);
        assert(aux==s2);
    }

    @Test
    void addTema() {
        ArrayList<Tema> l;
        l=srvT.allTeme();
        Tema aux;
        assert(l.size()==0);
        aux = srvT.addTema(t1);
        l=srvT.allTeme();
        assert(l.size()==1);
        assert(aux==null);
        Tema aux2 = srvT.addTema(t1);
        assert(aux2==t1);
    }

    @Test
    void prelungireDeadline(){
        Tema aux=srvT.prelungireDeadline(t2);
        assert(aux.getDeadline().equals("5"));
        aux=srvT.prelungireDeadline(t1);
        assert(aux.getDeadline().equals("3"));
    }

    @Test
    void calculateNota(){
        float notaProvizorie = srvN.calculeazaNota(n1,t1,0);
        assert(notaProvizorie == 10);
        notaProvizorie = srvN.calculeazaNota(n2,t2,0);
        assert(notaProvizorie == 6.5);
        notaProvizorie = srvN.calculeazaNota(n3,t2,2);
        assert(notaProvizorie == 7.5);
        notaProvizorie = srvN.calculeazaNota(n3,t2,1);
        assert(notaProvizorie == 5);
        try {
            notaProvizorie = srvN.calculeazaNota(n3, t2, 0);
            assert (false);
        }catch (ValidationException e) {
            assert (e.getMessage().equals("Tema nu mai poate fi predata! Au trecut mai mult de doua saptamani!"));
        }

    }
}