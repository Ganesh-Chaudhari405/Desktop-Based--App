package Controller;


import DB.Global;
import DB.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;

public class ForgetPass {

        @FXML
        private TextField uname;

        @FXML
        private TextField ans;

        @FXML
        private ComboBox<String> qname;

        @FXML
        private Button update;

        @FXML
        private Button cancel;

        @FXML
        private Label warn;

        @FXML
        private PasswordField pass;

        @FXML
        private PasswordField cpass;

        @FXML
        private CheckBox checkp;

        @FXML
        private CheckBox checkc;

        @FXML
        private TextField txtpass;

        @FXML
        private TextField txtcpass;

        @FXML
        void initialize() {

            checkp.setOnAction(event ->
                    {
                        txtpass.managedProperty().bind(checkp.selectedProperty());
                        txtpass.visibleProperty().bind(checkp.selectedProperty());
                        pass.managedProperty().bind(checkp.selectedProperty().not());
                        pass.visibleProperty().bind(checkp.selectedProperty().not());

                        txtpass.textProperty().bindBidirectional(pass.textProperty());
                    }
                    );



            checkc.setOnAction(event ->
                    {
                        txtcpass.managedProperty().bind(checkc.selectedProperty());
                        txtcpass.visibleProperty().bind(checkc.selectedProperty());
                        cpass.managedProperty().bind(checkc.selectedProperty().not());
                        cpass.visibleProperty().bind(checkc.selectedProperty().not());

                        txtcpass.textProperty().bindBidirectional(cpass.textProperty());

                    }
            );


            cancel.setOnAction(event ->
                   { Parent root = null;
                           try {
                                    root = FXMLLoader.load(ForgetPass.class.getResource("/Design/login.fxml"));
                             }
                           catch (IOException e) {
                                   warn.setText("Something went wrong");
                           }
                           Screen screen=Screen.getPrimary();
                           Rectangle2D bounds=screen.getVisualBounds();
                           Stage stage = new Stage();
                           Scene scene = new Scene(root,bounds.getWidth()-50,bounds.getHeight()-50, Color.BLUE);
                           stage.setScene(scene);
                       stage.getIcons().add(new Image(Main.class.getResource("/Design/iganesh.jpg").toString()));
                           stage.setMaximized(true);
                           stage.setTitle(Global.client);
                           stage.show();
                           ((Node)(event.getSource())).getScene().getWindow().hide();
                   }
                   );


            ObservableList <String> options = FXCollections.observableArrayList("What is your favorite movie?",
                    "what is your favorite website?",
                    "Who is your favorite actor, musician, or artist?",
                    "What high school did you attend?",
                    "In what city were you born?",
                    "What is your favorite color?",
                    "What is the name of your favourite pet?",
                    "What is your favorite Book?"
            );

            qname.getItems().addAll(options);
            update.setOnAction(event ->
         {

             String name1= uname.getText().trim();
           String answer = ans.getText().trim();
           String pass1 = pass.getText().trim();
           String pass2 = cpass.getText().trim();

             if (name1.isEmpty())
                 warn.setText("Please enter Username");

             else if(answer.isEmpty())
                 warn.setText("Please enter answer");

             else if(pass1.isEmpty() )
                 warn.setText("Please enter password");

             else if(pass1.length() <6)
                 warn.setText("Password length should be greter than 6");

             else if(!pass1.equals(pass2))
                 warn.setText("Password not match");


             else {

                 try {
                     String question = qname.getValue();
                     Session session = Global.getSession();
                     Transaction transaction = session.beginTransaction();
                     User user = session.load(User.class, name1);

                      if (question.equals(user.getQname()) && answer.equals(user.getAns()))
                      {
                         if (pass1.equals(pass2))
                         {
                             user.setPass(pass1);
                             warn.setText("Password successfully updated");
                             uname.setText("");
                             ans.setText("");
                             pass.setText("");
                             cpass.setText("");
                             session.persist(user);
                         }

                     }

                     else {
                          warn.setText("Please enter correct question or answer..!!");
                     }

                     transaction.commit();
                     session.close();
                 }

                 catch (Exception e) {
                   warn.setText("Enter correct Username");
                 }
             }

         }
         );

        }
    }


