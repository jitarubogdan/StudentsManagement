package gui;

import domain.Student;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class StudentView {
    private StudentController ctrl;

    public StudentView(StudentController ctrl){
        this.ctrl = ctrl;
        initView();
    }

    private BorderPane borderPane;
    TableView<Student> tableView = new TableView<>();

    TextField textFieldId = new TextField();
    TextField textFieldName = new TextField();
    TextField textFieldGroup = new TextField();
    TextField textFieldEmail = new TextField();

    private void initView(){
        borderPane = new BorderPane();
        borderPane.setTop(initTop());
        borderPane.setLeft(initLeft());
        borderPane.setCenter(initCenter());

        Label copyright = new Label("@........");
        AnchorPane a = new AnchorPane(copyright);
        AnchorPane.setBottomAnchor(copyright,50d);
        borderPane.setBottom(a);
    }

    private AnchorPane initTop(){
        AnchorPane topAnchorPane = new AnchorPane();

        Label titleLabel = new Label("Student Operations");
        topAnchorPane.getChildren().add(titleLabel);
        AnchorPane.setTopAnchor(titleLabel,20d);
        AnchorPane.setLeftAnchor(titleLabel,200d);
        AnchorPane.setBottomAnchor(titleLabel,20d);
        titleLabel.setFont(new Font(30));
        return topAnchorPane;
    }

    private AnchorPane initCenter(){
        AnchorPane centerAnchorPane = new AnchorPane();
        GridPane gridPane = createGridPane();
        centerAnchorPane.getChildren().add(gridPane);
        AnchorPane.setLeftAnchor(gridPane,20d);

        HBox buttonsGroups = createButtons();

        AnchorPane.setBottomAnchor(buttonsGroups,280d);
        AnchorPane.setLeftAnchor(buttonsGroups,80d);
        centerAnchorPane.getChildren().add(buttonsGroups);
        return centerAnchorPane;
    }

    private AnchorPane initLeft(){
        AnchorPane leftAnchorPane = new AnchorPane();
        tableView = createStudentTable();
        leftAnchorPane.getChildren().add(tableView);
        AnchorPane.setLeftAnchor(tableView,20d);
        return leftAnchorPane;
    }

    private TableView<Student> createStudentTable() {
        TableColumn<Student,String> idColumn = new TableColumn<>("Id");
        TableColumn<Student,String> nameColumn = new TableColumn<>("Name");
        TableColumn<Student,String> groupColumn = new TableColumn<>("Group");
        TableColumn<Student,String> emailColumn = new TableColumn<>("Email");

        tableView.getColumns().addAll(idColumn,nameColumn,groupColumn,emailColumn);

        idColumn.setCellValueFactory(new PropertyValueFactory<Student,String>("ID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Student,String>("nume"));
        groupColumn.setCellValueFactory(new PropertyValueFactory<Student,String>("grupa"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<Student,String>("email"));

        tableView.setItems(ctrl.getModel());

        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable,oldValue,newValue)->{
                    ctrl.showStudentDetails(newValue);
                    textFieldId.setEditable(false);
                }
        );
        return tableView;
    }

    private GridPane createGridPane(){
        GridPane gridPaneStudentDetails = new GridPane();

        gridPaneStudentDetails.setHgap(5);
        gridPaneStudentDetails.setVgap(5);

        Label labelId = createLabel("Id:");
        Label labelName = createLabel("Name:");
        Label labelGroup = createLabel("Group:");
        Label labelEmail = createLabel("Email:");

        gridPaneStudentDetails.add(labelId,0,0);
        gridPaneStudentDetails.add(labelName,0,1);
        gridPaneStudentDetails.add(labelGroup,0,2);
        gridPaneStudentDetails.add(labelEmail,0,3);

        gridPaneStudentDetails.add(textFieldId,1,0);
        gridPaneStudentDetails.add(textFieldName,1,1);
        gridPaneStudentDetails.add(textFieldGroup,1,2);
        gridPaneStudentDetails.add(textFieldEmail,1,3);

        ColumnConstraints c1 = new ColumnConstraints();
        c1.setPrefWidth(60d);

        ColumnConstraints c2 = new ColumnConstraints();
        c2.setPrefWidth(250d);

        gridPaneStudentDetails.getColumnConstraints().addAll(c1,c2);

        return gridPaneStudentDetails;
    }

    private Label createLabel(String s) {
        Label l = new Label(s);
        l.setFont(new Font(15));
        l.setTextFill(Color.BLACK);
        l.setStyle("-fx-font-weight: bold");
        return l;
    }

    public HBox createButtons(){
        Button buttonAdd = new Button("Add");
        Button buttonUpdate = new Button("Update");
        Button buttonDelete = new Button("Delete");
        Button buttonClear = new Button("Clear");

        HBox hb = new HBox(10,buttonAdd,buttonUpdate,buttonDelete,buttonClear);
        buttonAdd.setOnAction(ctrl::handleAddStudent);
        buttonClear.setOnAction(ctrl::handleClearFields);
        buttonUpdate.setOnAction(ctrl::handleUpdateStudent);
        buttonDelete.setOnAction(ctrl::handleRemoveStudent);
        return hb;
    }

    public BorderPane getView(){
        return borderPane;
    }
}
