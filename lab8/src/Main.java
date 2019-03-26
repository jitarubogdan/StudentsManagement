import controller.MainWindow;
import domain.Nota;
import domain.Student;
import domain.Tema;
import domain.validator.*;
import gui.StudentController;
import gui.StudentView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import repository.*;
import service.NotaService;
import service.StudentService;
import service.TemaService;
import ui.UI;

import java.io.IOException;

import static javafx.application.Application.launch;

public class Main extends Application {

    public static void main(String[] args) {
       // UI ui = new UI();
      //  ui.run();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        try{
            Validator<Student> vs = new StudentValidator();
            CrudRepository<String, Student> repoS = new InFileStudentRepository("./data/student.txt",vs);
            StudentService srvS = new StudentService(repoS);
            Validator<Tema> vt = new TemaValidator();
            CrudRepository<String, Tema> repoT = new InFileTemaRepository("./data/homework.txt",vt);
            TemaService srvT = new TemaService(repoT);
            Validator<Nota> vn = new NotaValidator();
            CrudRepository<String, Nota> repoN = new InFileNotaRepository("./data/grade.txt",vn);
            NotaService srvN = new NotaService(repoN);


            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/mainWindow.fxml"));
            Parent rootLayout = loader.load();
            MainWindow controller = loader.getController();

            controller.setService(srvS, srvT, srvN);

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /*
    @Override
    public void start(Stage primaryStage) throws Exception {
        Validator<Student> vs = new StudentValidator();
        CrudRepository<String, Student> repoS = new InFileStudentRepository("./data/student.txt",vs);
        StudentService srvS = new StudentService(repoS);
        StudentController ctrlS = new StudentController(srvS);
        StudentView viewS = new StudentView(ctrlS);
        ctrlS.setView(viewS);

        primaryStage.setTitle("Student CRUD Operations");
        primaryStage.setScene(new Scene(viewS.getView(),720,600));
        primaryStage.show();
    }
    */
}
