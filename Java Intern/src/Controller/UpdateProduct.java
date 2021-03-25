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

import static DB.Global.prodid;

public class UpdateProduct {


    @FXML
    private TextField hsn;

    @FXML
    private Button update;

    @FXML
    private ComboBox<String> dom;

    @FXML
    private Label warn;

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

        try
        {
            Session session = Global.getSession();
            Transaction transaction = session.beginTransaction();
            Product product = (Product) session.createQuery
                    ("from DB.Product g where g.id1=:id and g.idd=1").setParameter("id",prodid).uniqueResult();

            prodname.setValue(product.getProdname());
            dom.setValue(product.getDom());
            hsn.setText(product.getHsn());
            gst.setText(product.getGst());
            transaction.commit();
            session.close();

            update.setOnAction(event ->
                    {

                        String prodn = prodname.getValue();
                        String domat = dom.getValue();
                        String hsnom = hsn.getText();
                        String gsts =  gst.getText();

                        if(prodn.isEmpty())
                            warn.setText("Enter product name");

                        else if(gsts.isEmpty())
                            warn.setText("Enter GST");

                        else {

                            try {
                                Session session1 = Global.getSession();
                                Transaction transaction1 = session1.beginTransaction();
                                Product product1 = (Product) session1.createQuery("from DB.Product h where h.id1=:id and h.idd=1").setParameter("id", prodid).uniqueResult();

                                product1.setProdname(prodn);
                                product1.setDom(domat);
                                product1.setHsn(hsnom);
                                product1.setGst(gsts);
                                transaction1.commit();
                                session1.close();
                                warn.setText("Updated Successfully");

                                prodname.getItems().clear();
                                dom.getItems().clear();
                                dom.setValue("");
                                prodname.setValue("");
                                hsn.setText("");
                                gst.setText("");
                                prodname.getItems().addAll(produts);
                                dom.getItems().addAll(domatter);


                            }
                            catch (Exception e) {
                                e.printStackTrace();
                                warn.setText("error"+e);
                            }
                        }
                   }
                    );

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }




    }
}
