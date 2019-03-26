package ui;

import domain.Nota;
import domain.Student;
import domain.Tema;
import domain.validator.*;
import repository.*;
import service.NotaService;
import service.StudentService;
import service.TemaService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.Provider;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UI {

    private Validator<Student> vs = new StudentValidator();
    private CrudRepository<String, Student> repoS = new InFileStudentRepository("./data/student.txt",vs);
    private CrudRepository<String, Student> XMLrepoS = new XMLStudentRepository("./data/Student.xml",vs);

    private Validator<Tema> vt = new TemaValidator();
    private CrudRepository<String, Tema> repoT = new InFileTemaRepository("./data/homework.txt",vt);
    private CrudRepository<String, Tema> XMLrepoT = new XMLTemaRepository("./data/Homework.xml",vt);


    private Validator<Nota> vn = new NotaValidator();
    private CrudRepository<String, Nota> repoN = new InFileNotaRepository("./data/grade.txt",vn);
    private CrudRepository<String, Nota> XMLrepoN = new XMLNotaRepository("./data/Grade.xml",vn);


    public StudentService srvS = new StudentService(XMLrepoS);
    public TemaService srvT = new TemaService(XMLrepoT);
    public NotaService srvN = new NotaService(XMLrepoN);
    public Scanner scanner = new Scanner(System.in);

    void menu(){
        System.out.println("1.Add student\n" +
                "2.Delete student\n" +
                "3.Update student\n" +
                "4.Add homework\n" +
                "5.Delay a deadline\n" +
                "6.Print all students\n" +
                "7.Print all homeworks\n" +
                "8.Add a grade\n" +
                "9.Print all grades\n");
    }

    void UIAddStudent(){
        String id,nume,grupa,email;
        System.out.println("Introduceti id:");
        id = scanner.next();
        System.out.println("Introduceti numele:");
        nume = scanner.next() + " " + scanner.next();
        System.out.println("Introduceti grupa:");
        grupa = scanner.next();
        System.out.println("Introduceti emailul:");
        email = scanner.next();
        Student s = new Student(id,nume,grupa,email);
        srvS.add(s);
    }

    void UIDeleteStudent(){
        String id,nume,grupa,email;
        System.out.println("Introduceti id:");
        id = scanner.next();
        System.out.println("Introduceti numele:");
        nume = scanner.next() + " " + scanner.next();
        System.out.println("Introduceti grupa:");
        grupa = scanner.next();
        System.out.println("Introduceti emailul:");
        email = scanner.next();
        Student s = new Student(id,nume,grupa,email);
        srvS.remove(s);
    }

    void UIUpdateStudent(){
        String id,nume,grupa,email;
        System.out.println("Introduceti id:");
        id = scanner.next();
        System.out.println("Introduceti numele:");
        nume = scanner.next() + " " + scanner.next();
        System.out.println("Introduceti grupa:");
        grupa = scanner.next();
        System.out.println("Introduceti emailul:");
        email = scanner.next();
        Student s = new Student(id,nume,grupa,email);
        srvS.update(s);
    }

    void UIAddTema(){
        String id,descriere,deadline,receive;
        System.out.println("Introduceti id:");
        id = scanner.next();
        System.out.println("Introduceti descriere:");
        descriere = scanner.next();
        System.out.println("Introduceti deadline:");
        deadline = scanner.next();
        System.out.println("Introduceti receive:");
        receive = scanner.next();
        Tema t = new Tema(id,descriere,deadline,receive);
        srvT.addTema(t);
    }

    void UIDelay(){
        String id,descriere,deadline,receive;
        System.out.println("Introduceti id:");
        id = scanner.next();
        System.out.println("Introduceti descriere:");
        descriere = scanner.next();
        System.out.println("Introduceti deadline:");
        deadline = scanner.next();
        System.out.println("Introduceti receive:");
        receive = scanner.next();
        Tema t = new Tema(id,descriere,deadline,receive);
        srvT.prelungireDeadline(t);
    }

    void UIAddNota(){
        String ids,idt,feedback;
        int data;
        float nota,scutire;
        Student student;
        Tema tema;
        System.out.println("Introduceti id-ul studentului:");
        ids = scanner.next();
        student = srvS.findStudent(ids);
        if(student == null)
            throw new ValidationException("Studentul nu exista!");
        System.out.println("Introduceti id-ul temei:");
        idt = scanner.next();
        tema = srvT.findTema(idt);
        if(tema == null)
            throw new ValidationException("Tema nu exista!");
        System.out.println("Introduceti nota:");
        nota = scanner.nextFloat();
        //scanner.nextLine();
        System.out.println("Introduceti data:");
        data = scanner.nextInt();
        //scanner.nextLine();
        Nota n = new Nota(ids,idt,nota,data);
        System.out.println("Introduceti numarul scutirilor:");
        scutire = scanner.nextFloat(); scanner.nextLine();
        System.out.println("Introduceti un feedback:");
        feedback = scanner.nextLine();
        //System.out.println(feedback);
//        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
//            feedback = br.readLine();
//            srvN.addNota(n,student,tema,scutire,feedback);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        srvN.addNota(n,student,tema,scutire,feedback);
    }

    void UIAllStudents(){
        ArrayList<Student> l= srvS.allStudents();
        l.forEach(s-> System.out.println(s));
    }

    void UIAllTeme(){
        ArrayList<Tema> l= srvT.allTeme();
        l.forEach(t-> System.out.println(t));
    }

    void UIAllNote(){
        //List<Nota> l= srvN.allNote();
        //l.forEach(n-> System.out.println(n));
    }

    public void run(){
        int cmd;
        while (true){
            menu();
            System.out.println("Introdueceti o comanda:");
            cmd = scanner.nextInt();
            try {
                if (cmd == 1)
                    UIAddStudent();
                else if (cmd == 2)
                    UIDeleteStudent();
                else if (cmd == 3)
                    UIUpdateStudent();
                else if (cmd == 4)
                    UIAddTema();
                else if (cmd == 5)
                    UIDelay();
                else if (cmd == 6)
                    UIAllStudents();
                else if (cmd == 7)
                    UIAllTeme();
                else if (cmd == 8)
                    UIAddNota();
                else if (cmd == 9)
                    UIAllNote();
                else
                    System.out.println("Comanda invalida!");
            }
            catch (IllegalArgumentException il){
                System.out.println(il.getMessage());
            }
            catch (ValidationException ve){
                System.out.println(ve.getMessage());
            }
        }
    }

}
