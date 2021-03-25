package Controller;

import DB.Customer;
import DB.Global;
import VTable.CustomerView;
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
import java.util.Iterator;
import java.util.List;

import static Controller.Home.singleSelectionModel1;
import static Controller.Home.tab;


public class Viewcustomer {

    @FXML
    private TableColumn<VTable.CustomerView, Long> id;

    @FXML
    private TableColumn<VTable.CustomerView, String> name;

    @FXML
    private TableColumn<VTable.CustomerView, String> mob;

    @FXML
    private TableColumn<VTable.CustomerView, String> email;

    @FXML
    private TableColumn<VTable.CustomerView, String> addr;

    @FXML
    private TableColumn<VTable.CustomerView, String> gstno;

    @FXML
    private TableColumn<VTable.CustomerView, String> panno;

    @FXML
    private TableColumn<CustomerView, String> custtype;

    @FXML
    private TableColumn<CustomerView, String> state;

    @FXML
    private TableColumn<CustomerView, String> msg;

    @FXML
    private TableColumn<CustomerView, String> custfile;

    @FXML
    private TextField search;

    @FXML
    private Button export;

    @FXML
    private ObservableList<VTable.CustomerView> data = FXCollections.observableArrayList();

    @FXML
    private TableView<VTable.CustomerView> tbl;

    public void initialize() {


       PropertyValueFactory factory = new PropertyValueFactory("id");
        id.setCellValueFactory(factory);
        name.setCellValueFactory(new PropertyValueFactory("name"));
        mob.setCellValueFactory(new PropertyValueFactory("mob"));
        email.setCellValueFactory(new PropertyValueFactory("email"));
        addr.setCellValueFactory(new PropertyValueFactory("addr"));
        gstno.setCellValueFactory(new PropertyValueFactory("gstno"));
        panno.setCellValueFactory(new PropertyValueFactory<>("panno"));
        custtype.setCellValueFactory(new  PropertyValueFactory<>("custtype"));
        state.setCellValueFactory(new PropertyValueFactory<>("state"));
        msg.setCellValueFactory(new PropertyValueFactory<>("msg"));
        custfile.setCellValueFactory(new PropertyValueFactory<>("custfile"));

      tbl.setItems(getData());

        FilteredList<CustomerView> filtereddata = new FilteredList<>(data, p -> true);

        search.textProperty().addListener((observable, oldValue, newValue) -> {
            filtereddata.setPredicate(customer -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (customer.getName().toLowerCase().contains(lowerCaseFilter))
                    return true;
                else if (customer.getAddr() != null && customer.getAddr().toLowerCase().contains(lowerCaseFilter))
                    return true;
                else if (customer.getEmail() != null && customer.getEmail().toLowerCase().contains(lowerCaseFilter))
                    return true;
                else if (customer.getCusttype() !=null && customer.getCusttype().toLowerCase().contains(lowerCaseFilter))
                    return true;

                else
                    return false;
            });
        });

        SortedList<VTable.CustomerView> sortedData = new SortedList<>(filtereddata);
        sortedData.comparatorProperty().bind(tbl.comparatorProperty());
        tbl.setItems(sortedData);


        export.setOnAction(event -> {
            try {
                Workbook workbook = new HSSFWorkbook();
                Sheet spreadsheet = workbook.createSheet("Customer");

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
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel File (*.xls)", "*.xls");
                fileChooser.getExtensionFilters().add(extFilter);
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


        tbl.setRowFactory(tv -> {
            TableRow<VTable.CustomerView> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    VTable.CustomerView rowData = row.getItem();
                    Global.custid = rowData.getId();
                    try {

                        Tab tab1 = new Tab("Update Customer");
                        tab1.setClosable(true);
                        StackPane stackpane = new StackPane();
                        stackpane.setAlignment(Pos.CENTER);
                        stackpane.getChildren().clear();
                        Node node = FXMLLoader.load(this.getClass().getResource("/Design/updatecustomer.fxml"));
                        stackpane.getChildren().setAll(node);
                        tab1.setContent(stackpane);
                        tab.getTabs().add(tab1);
                        singleSelectionModel1.select(tab1);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;

        });



    }

    private ObservableList<CustomerView> getData() {


        Session session= Global.getSession();
        Transaction transaction=session.beginTransaction();
        try{
            List<Customer> custlist=session.createQuery("from DB.Customer l where l.d=1").list();

            Iterator iterator = custlist.iterator();

            while(iterator.hasNext())
            {

                DB.Customer dcust =(DB.Customer) iterator.next();

                VTable.CustomerView cust=new VTable.CustomerView();

                cust.setId(dcust.getId());
                cust.setName(dcust.getName());
                cust.setEmail(dcust.getEmail());
                cust.setAddr(dcust.getAddr());
                cust.setMob(dcust.getMob());
                cust.setGstno(dcust.getGstno());
                cust.setPanno(dcust.getPanno());
                cust.setState(dcust.getState());
                cust.setCusttype(dcust.getCusttype());

                data.add(cust);
            }
            transaction.commit();
            session.close();
            return data;

        }
        catch (Exception e)
        {
            return data;

        }
    }
}

