package controller;

import domain.Nota;
import domain.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import service.NotaService;
import service.StudentService;
import service.TemaService;
import utils.GradeChangeEvent;
import utils.Observable;
import utils.Observer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class FilterController implements Observer<GradeChangeEvent> {
    private NotaService serviceNota;
    private StudentService serviceStudent;
    private TemaService serviceTema;
    private ObservableList<Nota> modelNota = FXCollections.observableArrayList();

    @FXML
    TableView<Nota> tableView;

    @FXML
    TableColumn<Nota,String> tableColumnName;

    @FXML
    TableColumn<Nota,String> tableColumnTema;

    @FXML
    TableColumn<Nota,Float> tableColumnNota;

    @FXML
    TableColumn<Nota,Integer> tableColumnData;

    @FXML
    TextField textFieldName;
    @FXML
    TextField textFieldIdTema;
    @FXML
    DatePicker textFieldNota;
    @FXML
    DatePicker textFieldData;

    @FXML
    public void initialize() {
        tableColumnName.setCellValueFactory(new PropertyValueFactory<Nota,String>("ids"));
        tableColumnTema.setCellValueFactory(new PropertyValueFactory<Nota,String>("idt"));
        tableColumnNota.setCellValueFactory(new PropertyValueFactory<Nota,Float>("nota"));
        tableColumnData.setCellValueFactory(new PropertyValueFactory<Nota,Integer>("data"));

        tableView.setItems(modelNota);
    }

    public void setService(NotaService serviceNota, StudentService serviceStudent, TemaService serviceTema){
        this.serviceNota = serviceNota;
        this.serviceStudent = serviceStudent;
        this.serviceTema = serviceTema;
        serviceNota.addObserver(this);
        modelNota.setAll((List<Nota>) StreamSupport.stream(serviceNota.allNote().spliterator(),false).collect(Collectors.toList()));
    }

    public void handleFilterTemaButtonAction(ActionEvent actionEvent){
        String idT = textFieldIdTema.getText();
        Predicate<Nota> p1 = n->n.getIdt().equals(idT);

        modelNota.setAll(serviceNota.allNoteL()
            .stream()
            .filter(p1)
            .collect(Collectors.toList()));
    }

    public void handleFilterStudentButtonAction(ActionEvent actionEvent){
        String name = textFieldName.getText();
        Student student = serviceStudent.findStudentByName(name);
        Predicate<Nota> p1 = n->n.getIds().equals(student.getID());

        modelNota.setAll(serviceNota.allNoteL()
                .stream()
                .filter(p1)
                .collect(Collectors.toList()));
    }

    public void handleFilterStudentTemaButtonAction(ActionEvent actionEvent){
        String idT = textFieldIdTema.getText();
        String name = textFieldName.getText();
        Student student = serviceStudent.findStudentByName(name);
        Predicate<Nota> p1 = n->n.getIdt().equals(idT);
        Predicate<Nota> p2 = n->n.getIds().equals(student.getID());

        modelNota.setAll(serviceNota.allNoteL()
                .stream()
                .filter(p1.and(p2))
                .collect(Collectors.toList()));
    }

    public void handleFilterDateButtonAction(ActionEvent actionEvent){
        LocalDate date1 = textFieldNota.getValue();
        LocalDate date2 = textFieldData.getValue();
        Calendar calendar=Calendar.getInstance();
        int day1 = date1.getDayOfYear();
        int day2 = date2.getDayOfYear();
        int week1 = day1/7+1;
        int week2 = day2/7+1;
        int facultyweek1 = week1-39;
        int facultyweek2 = week2-39;
        Predicate<Nota> p1 = n->n.getData()>=facultyweek1;
        Predicate<Nota> p2 = n->n.getData()<=facultyweek2;
        modelNota.setAll(serviceNota.allNoteL()
                .stream()
                .filter(p1.and(p2))
                .collect(Collectors.toList()));
    }

    @Override
    public void update(GradeChangeEvent gradeChangeEvent) {
        modelNota.setAll(StreamSupport.stream(serviceNota.allNote().spliterator(),false)
                .collect(Collectors.toList()));

//        List<String> list = serviceTema.allTeme().stream()
//                .map(t->t.getID().toString())
//                .collect(Collectors.toList());
//
//        modelTema.setAll(list);
    }
}
