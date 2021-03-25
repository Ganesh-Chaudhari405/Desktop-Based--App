package Controller;


import DB.Global;
import DB.User;
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
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

import static java.lang.System.exit;


public class MainController {


        @FXML
        private TextField uname;

        @FXML
        private PasswordField pass;

        @FXML
        private Button log;

        @FXML
        private Button reset;

        @FXML
        private Label warn;

        @FXML
        private Label fpass;

        @FXML
        private TextField txtpass;

        @FXML
        private CheckBox chpass;
        @FXML
        private Label sign;
        @FXML
        private PasswordField passs;


    @FXML // This method is called by the FXMLLoader when initialization is complete
        void initialize() {

        reset.setOnAction(event -> exit(0));

        sign.setOnMouseClicked(event ->
                {
                    Parent root1=null;
                    try {
                        root1 = FXMLLoader.load(MainController.class.getResource("/Design/passsignup.fxml"));
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                    Stage stage1 = new Stage();
                    stage1.setTitle(Global.client);
                    stage1.getIcons().add(new Image(Main.class.getResource("/Design/iganesh.jpg").toString()));
                    stage1.setScene(new Scene(root1,800,600));
                    stage1.setResizable(false);
                    stage1.show();

                    ( (Node) (event.getSource())).getScene().getWindow().hide();

                    Session session= Global.getSession();
                    User user= (User) session.createQuery("from User l where l.pass='"+passs+"' ").uniqueResult();
                    session.close();
                    Session session1= Global.getSession();
                    Transaction transaction = session1.beginTransaction();
                    User user1 = session1.load(User.class, user.getPass());

                    if(passs.equals(user1.getPass()))
                    {
                        Parent root=null;
                        try {
                            root = FXMLLoader.load(ForgetPass.class.getResource("/Design/signup.fxml"));
                        } catch (IOException e) {
                            warn.setText("Something went wrong");
                        }
                        Screen screen = Screen.getPrimary();
                        Rectangle2D bounds = screen.getVisualBounds();
                        Stage stage = new Stage();
                        Scene scene = new Scene(root, bounds.getWidth() - 50, bounds.getHeight() - 50, Color.BLUE);
                        stage.setScene(scene);
                        stage.getIcons().add(new Image(Main.class.getResource("/Design/iganesh.jpg").toString()));
                        stage.setMaximized(true);
                        stage.setTitle(Global.client);
                        stage.show();
                        ((Node) (event.getSource())).getScene().getWindow().hide();
                    }
                    else {

                        passs.setPromptText("Enter correct password");
                    }
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


        fpass.setOnMouseClicked(event ->
                {
                    try
                    {
                        Parent root;
                        root = FXMLLoader.load(MainController.class.getResource("/Design/forgetpass.fxml"));
                        Stage stage = new Stage();
                        stage.setTitle(Global.client);
                        stage.getIcons().add(new Image(Main.class.getResource("/Design/iganesh.jpg").toString()));
                       stage.setScene(new Scene(root,800,600));
                        stage.setResizable(false);
                        stage.show();

                            ( (Node) (event.getSource())).getScene().getWindow().hide();

                    }
                    catch (Exception e)
                    {
                        warn.setText("Something Went Wrong ..!!");
                    }
                }
        );

            log.setOnMouseClicked((event) ->
            {
                String name=uname.getText().trim();
                String password= pass.getText().trim();
                if (name.isEmpty())
                {
                    warn.setText("Please enter Username..!!");
                }
                else if(password.isEmpty())
                {
                    warn.setText("Please enter Password..!!");
                }
                else
                {
                   try
                    {

                        Session session= Global.getSession();
                        User user= (User) session.createQuery("from User l where l.uname='"+name+"' and l.pass='"+password+"' ").uniqueResult();
                        session.close();

                        if (!name.equals(user.getUname()))
                            warn.setText("Please enter valid username ..!!");
                        else if(!password.equals(user.getPass()))
                            warn.setText("Enter valid password ...");

                        else
                        {
                            LocalTime lt = user.getTime();
                            Instant instant = lt.atDate(user.getDate()).atZone(ZoneId.systemDefault()).toInstant();
                            Date time = Date.from(instant);
                            Global.llogin=time.toString();
                            Global.uname = name;
                            Session session1= Global.getSession();
                            Transaction transaction = session1.beginTransaction();
                            User user1 = session1.load(User.class, user.getUname());
                            user1.setDate(LocalDate.now());
                            user1.setTime(LocalTime.now());
                            session1.persist(user1);
                            transaction.commit();
                            session1.close();

                            Global.flag = "home";
                            Parent root;
                            root=FXMLLoader.load(Home.class.getResource("/Design/home.fxml"));
                            Stage stage=new Stage();
                            stage.setTitle(Global.client);
                            Screen screen=Screen.getPrimary();
                            Rectangle2D bounds=screen.getVisualBounds();
                            stage.getIcons().add(new Image(Main.class.getResource("/Design/iganesh.jpg").toString()));
                            stage.setScene(new Scene(root,bounds.getWidth()-50,bounds.getHeight()-50));
                            stage.setMaximized(true);
                            stage.show();
                            ((Node)(event.getSource())).getScene().getWindow().hide();
                        }
                    }
                    catch (Exception e)
                    {
                        warn.setText("Something went wrong");
                        System.out.print(e);
                    }
                }

            });

                }




    public MainController() {

    }

    public Parent getContent()
    {
        Parent root = null;

        try {
            root = FXMLLoader.load(MainController.class.getResource("../Design/login.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  root;
    }



    }


