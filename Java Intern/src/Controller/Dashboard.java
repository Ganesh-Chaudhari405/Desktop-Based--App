package Controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Dashboard {

    @FXML
    private Label date;

    @FXML
    private Label time;

    @FXML
    void initialize() {

        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = java.util.Calendar.getInstance().getTime();
        date.setText(format1.format(date1));

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1),event ->
        {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            time.setText(formatter.format(now));
        }
        ));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();


    }
}
