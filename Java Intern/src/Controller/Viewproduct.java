package Controller;

import DB.Global;
import DB.Product;
import VTable.ProductView;
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

public class Viewproduct {


    @FXML
    private TableView<VTable.ProductView> tbl;

    @FXML
    private TableColumn<ProductView, Long> id1;

    @FXML
    private TableColumn<ProductView,String> prodname;

    @FXML
    private TableColumn<ProductView,String> dom;

    @FXML
    private TableColumn<ProductView, Long> hsn;

    @FXML
    private TableColumn<ProductView,String> gst;

    @FXML
    private Button export;

    @FXML
    private TextField search;

    @FXML
    private ObservableList<ProductView> data = FXCollections.observableArrayList();

    @FXML
    void initialize() {
                             //take data from productview
        id1.setCellValueFactory(new PropertyValueFactory<>("id1"));
        prodname.setCellValueFactory(new PropertyValueFactory<>("prodname"));
        dom.setCellValueFactory(new PropertyValueFactory<>("dom"));
        hsn.setCellValueFactory(new PropertyValueFactory<>("hsn"));
        gst.setCellValueFactory(new PropertyValueFactory<>("gst"));

        tbl.setItems(getprod());


        //for update product
        tbl.setRowFactory(eve->
                {
                    TableRow<ProductView> row = new TableRow<>();
                    row.setOnMouseClicked(event ->
                            {
                                if(event.getClickCount()==2 && (!row.isEmpty()))
                                {
                                 ProductView rowdata = row.getItem();
                                 Global.prodid =rowdata.getId1();
                                    try {

                                        Tab tab1 = new Tab("Update Customer");
                                        tab1.setClosable(true);
                                        StackPane stackpane = new StackPane();
                                        stackpane.setAlignment(Pos.CENTER);
                                        stackpane.getChildren().clear();
                                        Node node = FXMLLoader.load(this.getClass().getResource("/Design/updateproduct.fxml"));
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

        //for search product

        FilteredList<ProductView> filteredList = new FilteredList<>(data,p->true);
        search.textProperty().addListener((observable, oldValue, newValue) ->
                {
                    filteredList.setPredicate(product ->{

                        if(newValue==null||newValue.isEmpty())
                            return true;
                        String lowerCaseFilter = newValue.toLowerCase();

                        if (product.getProdname().toLowerCase().contains(lowerCaseFilter))
                            return true;
                        else
                            return false;

                   });
                });
        SortedList<ProductView> sortedData = new SortedList<>(filteredList);
        sortedData.comparatorProperty().bind(tbl.comparatorProperty());
        tbl.setItems(sortedData);


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



    }


    private ObservableList<ProductView> getprod() {

        Session session = Global.getSession();
        Transaction transaction =session.beginTransaction();
        try {
            List<Product> prod = session.createQuery("From DB.Product g where g.idd=1").list();
            Iterator iterator = prod.iterator();

            while(iterator.hasNext())
            {
            Product product = (Product) iterator.next();
            ProductView productView = new ProductView();

            productView.setId1(product.getId1());
            productView.setProdname(product.getProdname());
            productView.setDom(product.getDom());
            productView.setHsn(product.getHsn());
            productView.setGst(product.getGst());

            data.add(productView);
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
