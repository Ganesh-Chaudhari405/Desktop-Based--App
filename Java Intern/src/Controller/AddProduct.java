package Controller;

import DB.Global;
import DB.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class AddProduct {


    @FXML
    private TextField hsn;

    @FXML
    private Button add;

    @FXML
    private ComboBox<String> dom;

    @FXML
    private Label warn;

    @FXML
    private Button reset;

    @FXML
    private ComboBox<String> prodname;

    @FXML
    private TextField gst;

    @FXML
    void initialize() {

        ObservableList<String> produts = FXCollections.observableArrayList("wheat chana",
                "Maize Cattle Feed", "Soya Lecithin Cattle Feed","Buffalo Feed",
                "Guar Meal" , "Maize Oil Cake" , "Paddy Straws" , "Chana Churi" , "Sunflower Seed Meal");

        ObservableList<String> domatter= FXCollections.observableArrayList("0.5","1.0","1.5","2","2.5","3");

        prodname.getItems().addAll(produts);
        prodname.setValue("");
        dom.getItems().addAll(domatter);
        dom.setValue("");


        reset.setOnAction(event ->
                {

                    prodname.getItems().removeAll(produts);
                    prodname.setValue("");
                    prodname.getItems().addAll(produts);

                    gst.setText("");
                    hsn.setText("");
                    dom.setValue("");
                    dom.getItems().removeAll(domatter);
                    dom.getItems().addAll(domatter);


                }
                );


        add.setOnAction(event ->
                {
                    String pname = prodname.getValue();
                    String domat = dom.getValue();
                    String hsnom =hsn.getText();
                    String gst1 = gst.getText();
                    if (pname.equals(""))
                        warn.setText("Enter product name");
                    else if(domat.equals(""))
                        warn.setText("Enter DOM");
                    else if(hsnom.isEmpty())
                    warn.setText("Enter HSN");
                    else if (gst1.isEmpty())
                        warn.setText("Enter GST");

                    else {

                        try {
                            Session session = Global.getSession();
                            Transaction transaction = session.beginTransaction();
                            Product product = new Product();

                            product.setProdname(pname);
                            product.setDom(domat);
                            product.setHsn(hsnom);
                            product.setGst(gst1);
                            product.setIdd(1);
                            session.persist(product);

                            transaction.commit();
                            session.close();

                            warn.setText("Produt Successfully Updated");

                            prodname.getItems().removeAll(produts);
                            prodname.setValue("");
                            prodname.getItems().addAll(produts);
                            gst.setText("");
                            hsn.setText("");
                            dom.getItems().removeAll(domatter);
                            dom.setValue("");
                            dom.getItems().addAll(domatter);
                        } catch (Exception e) {
                            warn.setText("something went wrong"+e);
                        }
                    }
                }
                );


    }
}
