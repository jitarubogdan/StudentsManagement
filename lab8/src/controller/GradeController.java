package controller;

import domain.Nota;
import domain.NotaDTO;
import domain.Student;
import domain.Tema;
import domain.validator.ValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import service.NotaService;
import service.StudentService;
import service.TemaService;
import utils.ChangeEventType;
import utils.GradeChangeEvent;
import utils.Observer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class GradeController implements Observer<GradeChangeEvent> {
    private NotaService serviceNota;
    private StudentService serviceStudent;
    private TemaService serviceTema;
    private ObservableList<Nota> modelNota = FXCollections.observableArrayList();
    private ObservableList<String> modelTema = FXCollections.observableArrayList();

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
    TextField textFieldScutiri;

    @FXML
    TextField textFieldName;
    @FXML
    ComboBox textFieldTema;
    @FXML
    TextField textFieldNota;
    @FXML
    TextField textFieldData;
    @FXML
    TextField textFieldNameStudent;

    @FXML
    TextArea textAreaFeedback;

    @FXML
    public void initialize() {
       // tableColumnName.setCellValueFactory(new PropertyValueFactory<Nota,String>("studentName"));
       // tableColumnTema.setCellValueFactory(new PropertyValueFactory<Nota,String>("temaId"));
       // tableColumnNota.setCellValueFactory(new PropertyValueFactory<Nota,Float>("nota"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<Nota,String>("ids"));
        tableColumnTema.setCellValueFactory(new PropertyValueFactory<Nota,String>("idt"));
        tableColumnNota.setCellValueFactory(new PropertyValueFactory<Nota,Float>("nota"));
        tableColumnData.setCellValueFactory(new PropertyValueFactory<Nota,Integer>("data"));

        tableView.setItems(modelNota);

        //modelNota.setAll((List<Nota>) StreamSupport.stream(serviceNota.allNote().spliterator(),false).collect(Collectors.toList()));

    }

    private void initComboBox() {
//        textFieldTema.getItems().addAll(
//                "1","2","3","4","5","6","7","8","9","10","11","12","13","14"
//        );

        List<String> list = serviceTema.allTeme().stream()
                .map(t->t.getID().toString())
                .collect(Collectors.toList());

        modelTema.setAll(list);
        textFieldTema.getItems().addAll(modelTema);

        Calendar calendar=Calendar.getInstance();
        int week = calendar.get(Calendar.WEEK_OF_YEAR);
        int facultyweek = week-39;
        var wrapper = new Object(){
            String s = "";
        };
        serviceTema.allTeme().forEach((x)->{
           if(Integer.parseInt(x.getDeadline()) == facultyweek)
               wrapper.s = x.getID();
        });
        textFieldTema.getSelectionModel().select(wrapper.s);
    }


/*
    private List<NotaDTO> getNotaDTOList(){
        return serviceNota.allNote()
                .stream()
                .map(nota->{
                    String numeS = serviceStudent.findStudent(nota.getIds()).getNume();
                    String idT = nota.getIdt();
                    float val = nota.getNota();
                    return new NotaDTO(numeS,idT,val);
                })
                .collect(Collectors.toList());
//        return (List<NotaDTO>) StreamSupport.stream(serviceNota.allNote().forEach({
//                (Nota)nota->{
//                  String numeS = serviceStudent.findStudent(nota.getIds()).getNume();
//                    String idT = nota.getIdt();
//                    float val = nota.getNota();
//                   return new NotaDTO(numeS,idT,val);
//                })
//        });)
    }*/

    public void setService(NotaService serviceNota, StudentService serviceStudent, TemaService serviceTema){
        this.serviceNota = serviceNota;
        this.serviceStudent = serviceStudent;
        this.serviceTema = serviceTema;
        serviceNota.addObserver(this);
        modelNota.setAll((List<Nota>) StreamSupport.stream(serviceNota.allNote().spliterator(),false).collect(Collectors.toList()));


        initComboBox();
    }

    public void handleGradeAddButtonAction(ActionEvent actionEvent){
        String idS = textFieldName.getText();
        Student student = serviceStudent.findStudent(idS);
        String idT = textFieldTema.getValue().toString();
        Tema tema = serviceTema.findTema(idT);
        float notaC = Float.parseFloat(textFieldNota.getText());
        int data = Integer.parseInt(textFieldData.getText());
        Nota nota = new Nota(idS,idT,notaC,data);
        String feedback = textAreaFeedback.getText();
        int scutire = Integer.parseInt(textFieldScutiri.getText());
        try {
            if(notaC<1 || notaC>10)
                showErrorMessage("Nota invalida!");
            else {
                float notaProvizorie = serviceNota.calculeazaNota(nota,tema,scutire);
                Alert alertboxconf = new Alert(Alert.AlertType.CONFIRMATION);
                alertboxconf.setContentText("Ati introdus pentru studentul:" + student.getNume() +
                        "\nla tema:" + idT +
                        "\nnota:" + notaC +
                        "\nnota finala:" + notaProvizorie +
                        "\nintarzieri:" + (data-Integer.parseInt(tema.getDeadline())) +
                        "\nscutiri:" + scutire);
                if(notaC - notaProvizorie == (float)2.5){
                    feedback="Nota a fost niminuata cu 2.5 puncte";
                    textAreaFeedback.setText(feedback);
                }
                if(notaC - notaProvizorie == (float)5){
                    feedback="Nota a fost niminuata cu 5 puncte";
                    textAreaFeedback.setText(feedback);
                }
                Optional<ButtonType> option = alertboxconf.showAndWait();
                if (option.get() == ButtonType.OK) {
                    Nota saved = serviceNota.addNota(nota, student, tema, scutire, feedback);

                    if (saved == null) {
                        showNota(Alert.AlertType.INFORMATION, "Modificare cu succes", "Studentul a fost modificat!");
                        showStudentDetails(null);
                    }
                }
            }
        }catch(ValidationException e) {
            showErrorMessage(e.getMessage());
        }
    }

    public void handleGradeFindButtonAction(ActionEvent actionEvent){
        String name = textFieldNameStudent.getText();
        Student student = serviceStudent.findStudentByName(name);
        if(student != null){
            textFieldName.setText(student.getID());
        }
        else
            showErrorMessage("Studentul nu se afla in lista!");
    }

    private void showErrorMessage(String s) {
        Alert message = new Alert(Alert.AlertType.ERROR);
        message.setTitle("Mesaj de eroare");
        message.setContentText(s);
        message.showAndWait();
    }

    private void showNota(Alert.AlertType information, String salvare_cu_succes, String s) {
        Alert message = new Alert(information);
        message.setHeaderText(salvare_cu_succes);
        message.setContentText(s);
        message.showAndWait();
    }

    public void showStudentDetails(Nota newValue) {
        if(newValue == null){
            textFieldName.setText("");
            textFieldTema.setButtonCell(null);
            textFieldNota.setText("");
            textFieldData.setText("");
        }
        else
        {
            textFieldName.setText(newValue.getIds());
            //textFieldTema.setButtonCell(newValue.getIdt());
            textFieldNota.setText(newValue.getNota().toString());
            textFieldData.setText(newValue.getData().toString());
        }
    }

    @Override
    public void update(GradeChangeEvent gradeChangeEvent) {
        modelNota.setAll(StreamSupport.stream(serviceNota.allNote().spliterator(),false)
                .collect(Collectors.toList()));

        List<String> list = serviceTema.allTeme().stream()
                .map(t->t.getID().toString())
                .collect(Collectors.toList());

        modelTema.setAll(list);
    }
}
