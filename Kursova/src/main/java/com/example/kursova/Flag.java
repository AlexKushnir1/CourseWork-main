package com.example.kursova;

import Tanks.smok;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

public class Flag extends StackPane {

    private final ImageView imageView;

    private int x;
    private int y;

    private static ObservableList<Flag> listFlag = FXCollections.observableArrayList();
    private Text num;
    private Text text;
    public static smok pickedUpBy;
    public Integer pickedUpCounter = 0;


    public Flag(int x, int y, int height, int width) {
        this.imageView = new ImageView(new Image(Main.class.getResource("flag.png").toString(), height, width, false, false));
        this.x = x;
        this.y = y;

//        num = new Text(100, 0, String.valueOf(Main.root.getListObj().size()));
//        num.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        text = new Text(-100, -500,"Не підібраний");
        text.setLayoutX(100);
        text.setFont(Font.font("Arial", FontWeight.BOLD, 30));
//        VBox vbox1 = new VBox(num);
//        VBox vbox2 = new VBox(text);
//        vbox1.setLayoutX(500);
//        vbox1.setLayoutY(500);
//        vbox2.setLayoutX(0);
//        vbox2.setLayoutY(500);
        listFlag.add(this);

        getChildren().addAll(imageView);
        getChildren().addAll(text);
//        getChildren().addAll(vbox1, vbox2);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                num = new Text(String.valueOf(Main.root.getListObj().size()));

                Iterator<smok> iterator = Main.root.getListObj().iterator();
                while (iterator.hasNext()) {
                    smok object = iterator.next();
                    if (getBoundsInParent().intersects(object.getBoundsInParent()) && Flag.pickedUpBy == null) {
                        Flag.pickedUpBy = object;
                        object.moveTo(object.getTanksCommandSystem());
                    }
                }
            }
        }, 0, 1000);

    }

    public void setText(Text text) {
        this.text = text;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static ObservableList<Flag> getListFlag() {
        return listFlag;
    }
}
