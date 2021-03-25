package Controller;


import DB.Global;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.hibernate.Session;

import static java.lang.System.exit;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
       if(Global.flag1==1)
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


       }

   }

    @Override
    public void stop() throws Exception {
       // super.stop();
        Global.closeFactory();
        exit(0);
    }

    public static void main(String[] args) {


        launch(args);
    }

 class MyThread implements Runnable
    {
       public  MyThread()
       {

       }
        @Override
        public void run() {
            Session session =Global.getSession();
            session.close();
        }
    }

}
