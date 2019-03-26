package gui;

import domain.Student;
import domain.validator.ValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import service.StudentService;
import utils.Observable;
import utils.Observer;
import utils.StudentChangeEvent;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class StudentController implements Observer<StudentChangeEvent> {

    private StudentService srvS;
    private ObservableList<Student> model;
    private StudentView view;

    public StudentController(StudentService srvS) {
        this.srvS = srvS;
        List<Student> list = StreamSupport.stream(srvS.allStudents().spliterator(),false)
                .collect(Collectors.toList());
        model = FXCollections.observableArrayList(list);
        srvS.addObserver(this);
    }

    @Override
    public void update(StudentChangeEvent studentChangeEvent){
        model.setAll(StreamSupport.stream(srvS.allStudents().spliterator(),false)
            .collect(Collectors.toList()));
    }

    public StudentView getView(){
        return view;
    }

    public void setView(StudentView view){
        this.view = view;
    }

    public ObservableList<Student> getModel(){
        return model;
    }

    public void handleAddStudent(ActionEvent actionEvent) {
        Student s = extractStudent();
        try{
            Student saved = srvS.add(s);
            if(saved == null){
                showStudent(Alert.AlertType.INFORMATION,"Salvare cu succes","Studentul a fost adaugat!");
                showStudentDetails(null);
            }
            else
                showErrorMessage("Exista deja un student cu acest id!");
        }catch(ValidationException e){
            showErrorMessage(e.getMessage());
        }
    }

    public void handleUpdateStudent(ActionEvent actionEvent){
        Student s =extractStudent();
        try{
            Student saved = srvS.update(s);
            if(saved == null){
                showStudent(Alert.AlertType.INFORMATION,"Modificare cu succes","Studentul a fost modificat!");
                showStudentDetails(null);
            }
            else
                showErrorMessage("Nu exista studentul cu acest id!");
        }catch(ValidationException e){
            showErrorMessage(e.getMessage());
        }
    }

    public void handleRemoveStudent(ActionEvent actionEvent){
        Student s = extractStudent();
        try{
            Student saved = srvS.remove(s);
            if(saved != null){
                showStudent(Alert.AlertType.INFORMATION,"Stergere cu succes","Studentul a fost sters!");
                showStudentDetails(null);
            }
            else
                showErrorMessage("Nu exista studentul cu acest id!");
        }catch(ValidationException e){
            showErrorMessage(e.getMessage());
        }

    }

    private void showErrorMessage(String s) {
        Alert message = new Alert(Alert.AlertType.ERROR);
        message.setTitle("Mesaj de eroare");
        message.setContentText(s);
        message.showAndWait();
    }

    private void showStudent(Alert.AlertType information, String salvare_cu_succes, String s) {
        Alert message = new Alert(information);
        message.setHeaderText(salvare_cu_succes);
        message.setContentText(s);
        message.showAndWait();
    }

    private Student extractStudent() {
        String id = view.textFieldId.getText();
        String name = view.textFieldName.getText();
        String group = view.textFieldGroup.getText();
        String email = view.textFieldEmail.getText();
        Student s = new Student(id,name,group,email);
        return s;
    }

    public void showStudentDetails(Student newValue) {
                if(newValue == null){
                    view.textFieldId.setText("");
                    view.textFieldName.setText("");
                    view.textFieldGroup.setText("");
                    view.textFieldEmail.setText("");
        }
        else
        {
            view.textFieldId.setText(newValue.getID());
            view.textFieldName.setText(newValue.getNume());
            view.textFieldGroup.setText(newValue.getGrupa());
            view.textFieldEmail.setText(newValue.getEmail());
        }
    }
    public void handleClearFields(ActionEvent actionEvent){
        showStudentDetails(null);
        view.textFieldId.setEditable(true);
    }
}
