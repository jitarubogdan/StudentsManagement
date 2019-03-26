package controller;

import domain.Nota;
import domain.Student;
import domain.Tema;
import domain.validator.NotaValidator;
import domain.validator.StudentValidator;
import domain.validator.TemaValidator;
import domain.validator.Validator;
import gui.StudentController;
import gui.StudentView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import repository.CrudRepository;
import repository.InFileNotaRepository;
import repository.InFileStudentRepository;
import repository.InFileTemaRepository;
import service.NotaService;
import service.StudentService;
import service.TemaService;

import java.io.IOException;

public class MainWindow {
//    Validator<Student> vs = new StudentValidator();
//    CrudRepository<String, Student> repoS = new InFileStudentRepository("./data/student.txt",vs);
//    StudentService srvS = new StudentService(repoS);
//    Validator<Tema> vt = new TemaValidator();
//    CrudRepository<String, Tema> repoT = new InFileTemaRepository("./data/homework.txt",vt);
//    TemaService srvT = new TemaService(repoT);
//    Validator<Nota> vn = new NotaValidator();
//    CrudRepository<String, Nota> repoN = new InFileNotaRepository("./data/grade.txt",vn);
//    NotaService srvN = new NotaService(repoN);

    private StudentService srvS;
    private TemaService srvT;
    private NotaService srvN;

    public void setService(StudentService srvS, TemaService srvT, NotaService srvN) {
        this.srvS = srvS;
        this.srvT = srvT;
        this.srvN = srvN;
    }

    @FXML
    public void initialize(){}

    public void handleStudentsButtonAction(ActionEvent actionEvent) {
        Validator<Student> vs = new StudentValidator();
        CrudRepository<String, Student> repoS = new InFileStudentRepository("./data/student.txt",vs);
        StudentService srvS = new StudentService(repoS);
        StudentController ctrlS = new StudentController(srvS);
        StudentView viewS = new StudentView(ctrlS);
        ctrlS.setView(viewS);
        Stage primaryStageStudent = new Stage();
        primaryStageStudent.setTitle("Student CRUD Operations");
        primaryStageStudent.setScene(new Scene(viewS.getView(),720,600));
        primaryStageStudent.show();
    }

    public void handleGradesButtonAction(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/gradeWindow.fxml"));
            AnchorPane rootLayout = loader.load();
            GradeController controller = loader.getController();
            controller.setService(srvN,srvS,srvT);
            Stage primaryStageGrade = new Stage();
            Scene scene = new Scene(rootLayout);
            primaryStageGrade.setTitle("Grade CRUD Operations");
            primaryStageGrade.setScene(scene);
            primaryStageGrade.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void handleFilterButtonAction(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/filterWindow.fxml"));
            AnchorPane rootLayout = loader.load();
            FilterController controller = loader.getController();
            controller.setService(srvN,srvS,srvT);
            Stage primaryStageGrade = new Stage();
            Scene scene = new Scene(rootLayout);
            primaryStageGrade.setTitle("Grade filter");
            primaryStageGrade.setScene(scene);
            primaryStageGrade.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
