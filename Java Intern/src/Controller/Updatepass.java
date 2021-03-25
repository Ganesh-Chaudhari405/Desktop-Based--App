package Controller;

import DB.Global;
import DB.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Updatepass {

    @FXML
    private TextField opass;

    @FXML
    private TextField npass;

    @FXML
    private TextField cpass;

    @FXML
    private Button update;

    @FXML
    private Button reset;

    @FXML
    private Label warn;

    @FXML
    private CheckBox showop,shownp,showcp;

    @FXML
    private TextField txtop,txtcp,txtnp;


    @FXML
    void initialize() {

        reset.setOnAction(event -> {
            opass.setText("");
            npass.setText("");
            cpass.setText("");
            warn.setText("");
        });

        showop.setOnAction(event ->
                {
                        txtop.managedProperty().bind(showop.selectedProperty());
                        txtop.visibleProperty().bind(showop.selectedProperty());

                        opass.managedProperty().bind(showop.selectedProperty().not());
                        opass.visibleProperty().bind(showop.selectedProperty().not());

                        txtop.textProperty().bindBidirectional(opass.textProperty());

                }
                );

        shownp.setOnAction(event ->
                {
                    txtnp.managedProperty().bind(shownp.selectedProperty());
                    txtnp.visibleProperty().bind(shownp.selectedProperty());

                    npass.managedProperty().bind(shownp.selectedProperty().not());
                    npass.visibleProperty().bind(shownp.selectedProperty().not());

                    txtnp.textProperty().bindBidirectional(npass.textProperty());

                }
        );

        showcp.setOnAction(event ->
                {
                    txtcp.managedProperty().bind(showcp.selectedProperty());
                    txtcp.visibleProperty().bind(showcp.selectedProperty());

                    cpass.managedProperty().bind(showcp.selectedProperty().not());
                    cpass.visibleProperty().bind(showcp.selectedProperty().not());

                    txtcp.textProperty().bindBidirectional(cpass.textProperty());

                }
        );



        update.setOnAction(event -> {
                    String opasswd = opass.getText();
                    String npasswd = npass.getText();
                    String cpasswd = cpass.getText();

                    if (opasswd.equals(""))
                        warn.setText("Please enter old password");
                    else if (npasswd.equals(""))
                        warn.setText("Please enter new password");

                    else if(npasswd.length() <6)
                        warn.setText("Password should be greter than 6");

                    else if (!npasswd.equals(cpasswd))
                        warn.setText("Password not match");

                    else {

                        Session session = Global.getSession();
                        Transaction transaction = session.beginTransaction();
                        try {
                            User user = session.load(User.class, Global.uname);
                            if (opasswd.equals(user.getPass())) {
                                user.setPass(npasswd);
                                session.persist(user);
                                transaction.commit();
                                session.close();
                                warn.setText("Password Updated");
                                opass.setText("");
                                npass.setText("");
                                cpass.setText("");
                            } else {
                                transaction.commit();
                                session.close();
                                warn.setText("Please enter valid old password");
                            }
                        }
                        catch (Exception e) {
                            warn.setText("Enter correct username");
                            opass.setText("");
                            npass.setText("");
                            cpass.setText("");
                        }
                    }
                }
            );
    }
}
