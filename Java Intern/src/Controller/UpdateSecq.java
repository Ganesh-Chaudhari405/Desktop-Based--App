package Controller;

import DB.Global;
import DB.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UpdateSecq {


    @FXML
    private TextField pass;

    @FXML
    private TextField ans;

    @FXML
    private Button update;

    @FXML
    private ComboBox<String> seq;

    @FXML
    private Label warn;

    @FXML
    private Button reset;

    @FXML
    void initialize() {

        ObservableList<String> ques = FXCollections.observableArrayList("What is your favorite movie?",
                "what is your favorite website?",
                "Who is your favorite actor, musician, or artist?",
                "What high school did you attend?",
                "In what city were you born?",
                "What is your favorite color?",
                "What is the name of your favourite pet?",
                "What is your favorite Book?"
        );

        seq.getItems().addAll(ques);

        reset.setOnAction(event ->
                {
                    pass.setText("");
                    seq.getItems().removeAll(ques);
                    seq.getItems().addAll(ques);
                    ans.setText("");

                }
                );

        update.setOnAction(event ->
                {
                    String question = seq.getSelectionModel().getSelectedItem();
                    String passwd = pass.getText();
                    String answer = ans.getText();

                    if(passwd.isEmpty())
                        warn.setText("Enter password");
                    else if(answer.isEmpty())
                     warn.setText("Enter Answer");
                    else if(ques.isEmpty())
                        warn.setText("Select Question");
                    else
                    {
                        Session session = Global.getSession();
                        Transaction  transaction =session.beginTransaction();
                        try
                        {
                            User user = session.load(User.class,Global.uname);

                            if(passwd.equals(user.getPass()))
                            {
                                user.setQname(question);
                                user.setAns(answer);
                                session.persist(user);
                                transaction.commit();
                                session.close();
                                warn.setText("Security question Successfully Updated ...");
                                pass.setText("");
                                ans.setText("");
                                seq.getItems().removeAll(question);
                                seq.getItems().addAll(question);

                            }
                            else {
                                transaction.commit();
                                session.close();
                                warn.setText("Enter Valid Password");
                            }

                        }
                        catch (Exception e)
                        {
                            warn.setText("Something went wrong try again...");
                            pass.setText("");
                            ans.setText("");
                            seq.getItems().clear();
                            seq.getItems().addAll(question);


                        }


                    }

                }
                );





    }
}
