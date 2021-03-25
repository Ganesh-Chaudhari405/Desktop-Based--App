package Controller;

import DB.Feedback;
import DB.Global;
import VTable.FeedbackView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

import static Controller.Home.singleSelectionModel1;
import static Controller.Home.tab;

public class ViewFeedback {

    @FXML
    private TableView<FeedbackView> tbl;

    @FXML
    private TableColumn<?, String> id1;

    @FXML
    private TableColumn<FeedbackView, String> name;

    @FXML
    private TableColumn<FeedbackView, String> feedmsg;

    @FXML
    private TableColumn<FeedbackView, String> rating;

    @FXML
    private TableColumn<FeedbackView, LocalDate> date;

    @FXML
    private Button export;

    @FXML
    private TextField search;

    @FXML
    private ObservableList<FeedbackView> data = FXCollections.observableArrayList();


    @FXML
    void initialize() {

        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        feedmsg.setCellValueFactory(new PropertyValueFactory<>("feedmsg"));
        rating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        tbl.setItems((ObservableList<FeedbackView>) Feed());

        //for search feedback

        FilteredList<FeedbackView> filteredList = new FilteredList<>(data, p->true);
        search.textProperty().addListener((observable, oldValue, newValue) ->
        {
            filteredList.setPredicate(feedb ->{

                if(newValue==null||newValue.isEmpty())
                    return true;
                String lowerCaseFilter = newValue.toLowerCase();

                if (feedb.getName().toLowerCase().contains(lowerCaseFilter))
                    return true;
                else if(feedb.getFeedmsg().toLowerCase().contains(lowerCaseFilter))
                    return true;
                else
                    return false;

            });
        });
        SortedList<FeedbackView> sortedData = new SortedList<>(filteredList);
        sortedData.comparatorProperty().bind(tbl.comparatorProperty());
        tbl.setItems(sortedData);

        //for Export
        export.setOnAction(event -> {
            try {
                Workbook workbook = new HSSFWorkbook();
                Sheet spreadsheet = workbook.createSheet("Product");
                org.apache.poi.ss.usermodel.Row row = spreadsheet.createRow(0);

                for (int j = 0; j < tbl.getColumns().size(); j++) {
                    row.createCell(j).setCellValue(tbl.getColumns().get(j).getText());
                }

                for (int i = 0; i < tbl.getItems().size(); i++) {
                    row = spreadsheet.createRow(i + 1);
                    for (int j = 0; j < tbl.getColumns().size(); j++) {
                        if (tbl.getColumns().get(j).getCellData(i) != null) {
                            row.createCell(j).setCellValue(tbl.getColumns().get(j).getCellData(i).toString());
                        }
                        else {
                            row.createCell(j).setCellValue("");
                        }
                    }
                }

                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Specify a location of file to save");
                FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Excel File (*.xls,*.csv)", "*.xls,*.csv");
                fileChooser.getExtensionFilters().add(extensionFilter);
                File file = fileChooser.showSaveDialog(new Stage());
                if (file != null) {
                    try {
                        FileOutputStream fileOut = new FileOutputStream(file.getAbsolutePath());
                        workbook.write(fileOut);
                        fileOut.close();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });

        tbl.setRowFactory(eve->
                {
                    TableRow<FeedbackView> row = new TableRow<>();
                    row.setOnMouseClicked(event ->
                            {
                                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                                    FeedbackView rowdata = row.getItem();
                                    Global.feedid = rowdata.getD();
                                    try {

                                        Tab tab1 = new Tab("Update Feedback");
                                        tab1.setClosable(true);
                                        StackPane stackpane = new StackPane();
                                        stackpane.setAlignment(Pos.CENTER);
                                        stackpane.getChildren().clear();
                                        Node node = FXMLLoader.load(this.getClass().getResource("/Design/updatefeedback.fxml"));
                                        stackpane.getChildren().setAll(node);
                                        tab1.setContent(stackpane);
                                        tab.getTabs().add(tab1);
                                        singleSelectionModel1.select(tab1);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                            }
                    );

                    return row;
                }
        );



    }

    private ObservableList<FeedbackView> Feed() {

            Session session = Global.getSession();
            Transaction transaction =session.beginTransaction();
            try {
                List<Feedback> prod = session.createQuery("From DB.Feedback g where g.d=1").list();
                Iterator iterator = prod.iterator();

                while(iterator.hasNext())
                {
                    Feedback feedback = (Feedback) iterator.next();
                    FeedbackView feedbackView = new FeedbackView();

                   feedbackView.setName(feedback.getName());
                   feedbackView.setDate(feedback.getDate());
                   feedbackView.setRating(feedback.getRating());
                   feedbackView.setFeedmsg(feedback.getFeedmsg());

                    data.add(feedbackView);
                }
                transaction.commit();
                session.close();
                return data;

            }
            catch (Exception e)
            {
                e.printStackTrace();
                return data;
            }


    }
}
