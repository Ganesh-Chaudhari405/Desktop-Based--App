package Controller;

import DB.Global;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class Home {


    @FXML
    private MenuItem uppass;

    @FXML
    private MenuItem uppsec;

    @FXML
    private MenuItem exit;

    @FXML
    private MenuItem addcust;

    @FXML
    private MenuItem viewa;

    @FXML
    private Button addp;


    @FXML
    private MenuItem addfeedb;

    @FXML
    private MenuItem viewfeedb;


    @FXML
    private MenuItem addprod;

    @FXML MenuItem viewprod;

    @FXML
    private Button closea;

    @FXML
    private StackPane stack;

    @FXML
    public static TabPane tab;
    @FXML
    public SingleSelectionModel<Tab> selectionModel;
    public static SingleSelectionModel<Tab> singleSelectionModel1 ;

    @FXML
    void initialize() {




        //feedback
        addfeedb.setOnAction(event ->
                {
                    try {
                        Tab tab1 = new Tab("Add Feedback");
                        tab1.setClosable(true);
                        StackPane stackPane = new StackPane();
                        stackPane.setAlignment(Pos.CENTER);
                        stackPane.getChildren().clear();
                        Node node = FXMLLoader.load(this.getClass().getResource("/Design/Feedback.fxml"));
                        stackPane.getChildren().setAll(node);
                        tab1.setContent(stackPane);

                        tab.getTabs().add(tab1);
                        singleSelectionModel1.select(tab1);

                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                );


        viewfeedb.setOnAction(event ->
                {
                    try {
                        Tab tab1 = new Tab("View Feedback");
                        tab1.setClosable(true);
                        StackPane stackPane = new StackPane();
                        stackPane.setAlignment(Pos.CENTER);
                        stackPane.getChildren().clear();
                        Node node = FXMLLoader.load(this.getClass().getResource("/Design/viewfeedback.fxml"));
                        stackPane.getChildren().setAll(node);
                        tab1.setContent(stackPane);
                        tab.getTabs().add(tab1);
                        singleSelectionModel1.select(tab1);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }

        );



        exit.setOnAction(event ->
                { Parent root = null;
                    try {
                        root = FXMLLoader.load(ForgetPass.class.getResource("/Design/login.fxml"));
                    }
                    catch (IOException e) {
                        e.printStackTrace();
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

        tab = new TabPane();
        stack.getChildren().clear();
        stack.getChildren().add(tab);
        selectionModel = tab.getSelectionModel();
        singleSelectionModel1 = tab.getSelectionModel();

        try {
            Tab tab1 = new Tab("Dashboard");
            tab1.setClosable(false);
            StackPane stackPane = new StackPane();
            stackPane.setAlignment(Pos.CENTER);
            stackPane.getChildren().clear();
            Node node = FXMLLoader.load(Home.class.getResource("/Design/dashboard.fxml"));
            stackPane.getChildren().setAll(node);
            tab1.setContent(stackPane);
            tab.getTabs().add(tab1);
            singleSelectionModel1.select(tab1);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        uppass.setOnAction(event -> {
            try {
                Tab tab1 = new Tab("Update Password");
                tab1.setClosable(true);
                StackPane stackPane = new StackPane();
                stackPane.setAlignment(Pos.CENTER);
                stackPane.getChildren().clear();
                Node node = FXMLLoader.load(Home.class.getResource("/Design/updatepass.fxml"));
                stackPane.getChildren().setAll(node);
                tab1.setContent(stackPane);

                tab.getTabs().add(tab1);
               singleSelectionModel1.select(tab1);

            }
            catch (IOException e) {
                e.printStackTrace();
            }
        });


        addcust.setOnAction(event -> {
            try {
                Tab tab1 = new Tab("Add Customer");
                tab1.setClosable(true);
                StackPane stackPane = new StackPane();
                stackPane.setAlignment(Pos.CENTER);
                stackPane.getChildren().clear();
                Node node = FXMLLoader.load(this.getClass().getResource("/Design/addcustomer.fxml"));
                stackPane.getChildren().setAll(node);
                tab1.setContent(stackPane);

                tab.getTabs().add(tab1);
                singleSelectionModel1.select(tab1);

            }
            catch (IOException e) {
                e.printStackTrace();
            }
        });

        viewa.setOnAction(event ->
                {

                    try {
                        Tab tab1 = new Tab("View Customer");
                        tab1.setClosable(true);
                        StackPane stackPane = new StackPane();
                        stackPane.setAlignment(Pos.CENTER);
                        stackPane.getChildren().clear();
                        Node node = FXMLLoader.load(this.getClass().getResource("/Design/viewcustomer.fxml"));
                        stackPane.getChildren().setAll(node);
                        tab1.setContent(stackPane);

                        tab.getTabs().add(tab1);
                        singleSelectionModel1.select(tab1);

                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                );

        closea.setOnAction(event ->{
            int totaltabs = tab.getTabs().size();
            tab.getTabs().remove(1, totaltabs);
        });


        uppsec.setOnAction(event -> {
            try {
                Tab tab1 = new Tab("Update Security Questions");
                tab1.setClosable(true);
                StackPane stackPane = new StackPane();
                stackPane.setAlignment(Pos.CENTER);
                stackPane.getChildren().clear();
                Node node = FXMLLoader.load(this.getClass().getResource("/Design/updatesecq.fxml"));
                stackPane.getChildren().setAll(node);
                tab1.setContent(stackPane);

                tab.getTabs().add(tab1);
                singleSelectionModel1.select(tab1);

            }
            catch (IOException e) {
                e.printStackTrace();
            }
        });

        //product

        addprod.setOnAction(event ->
                {

                    try {
                        Tab tab1 = new Tab("Add Product");
                        tab1.setClosable(true);
                        StackPane stackPane = new StackPane();
                        stackPane.setAlignment(Pos.CENTER);
                        stackPane.getChildren().clear();


                        Node node = FXMLLoader.load(this.getClass().getResource("/Design/addproduct.fxml"));


                        stackPane.getChildren().setAll(node);
                        tab1.setContent(stackPane);

                        tab.getTabs().add(tab1);
                        singleSelectionModel1.select(tab1);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }

                );

            viewprod.setOnAction(event ->
                    {
                        try {
                            Tab tab1 = new Tab("View Product");
                            tab1.setClosable(true);
                            StackPane stackPane = new StackPane();
                            stackPane.setAlignment(Pos.CENTER);
                            stackPane.getChildren().clear();
                            Node node = FXMLLoader.load(this.getClass().getResource("/Design/viewproduct.fxml"));
                            stackPane.getChildren().setAll(node);
                            tab1.setContent(stackPane);
                            tab.getTabs().add(tab1);
                            singleSelectionModel1.select(tab1);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }

                    );



    }
}
