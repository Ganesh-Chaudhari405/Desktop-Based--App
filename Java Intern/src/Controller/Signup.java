package Controller;

import DB.Global;
import DB.User;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.Transaction;

import static java.lang.System.exit;

public class Signup {

    @FXML
    private TextField uname;

    @FXML
    private PasswordField pass;

    @FXML
    private Button sign;

    @FXML
    private Button cancel;

    @FXML
    private Label warn;

    @FXML
    private Label log;

    @FXML
    private CheckBox chpass;

    @FXML
    private TextField txtpass;

    @FXML
    void initialize() {

        cancel.setOnAction(event ->exit(0));

        log.setOnMouseClicked(event ->
                {
                    MainController controller = new MainController();
                    Screen screen=Screen.getPrimary();
                    Rectangle2D bounds=screen.getVisualBounds();
                    Stage stage = new Stage();
                    Scene scene = new Scene(controller.getContent(),bounds.getWidth()-50,bounds.getHeight()-50, Color.BLUE);
                    stage.setScene(scene);
                    stage.setMaximized(true);
                    stage.getIcons().add(new Image(Main.class.getResource("/Design/iganesh.jpg").toString()));
                    stage.setTitle(Global.client);
                    stage.show();
                    ((Node)(event.getSource())).getScene().getWindow().hide();

                }
                );

        chpass.setOnAction(event ->
                {
                 txtpass.managedProperty().bind(chpass.selectedProperty());
                 txtpass.visibleProperty().bind(chpass.selectedProperty());

                 pass.managedProperty().bind(chpass.selectedProperty().not());
                 pass.visibleProperty().bind(chpass.selectedProperty().not());

                 txtpass.textProperty().bindBidirectional(pass.textProperty());

                }
                );




        sign.setOnAction(event ->
                {
                    String name = uname.getText().trim();
                    String passwd = pass.getText().trim();

                    if(name.isEmpty())
                        warn.setText("Enter Name");
                    else if(passwd.isEmpty())
                        warn.setText("Enter password");
                    else if(passwd.length()<6)
                        warn.setText("Password should be greter than six letter");

                    else {
                        try {
                            Session session = Global.getSession();
                            Transaction transaction = session.beginTransaction();

                            User user = new User();

                            if (!name.equals(user.getUname())) {
                                user.setUname(name);
                                user.setPass(passwd);
                                session.persist(user);
                                transaction.commit();
                                session.close();
                                warn.setText("Signup Successfully");
                                uname.setText("");
                                pass.setText("");
                            }

                        }
                        catch (Exception e) {
                               warn.setText("Already registered");
                        }
                    }

                }
                );


    }
}
