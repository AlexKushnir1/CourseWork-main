package com.example.kursova;

import Tanks.smok;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
    private Text allTanks;
    private Text text;
    public static smok pickedUpBy;
    public Integer pickedUpCounter = 0;

    private static Group group = new Group();


    public Flag(int x, int y, int height, int width) {
        this.imageView = new ImageView(new Image(Main.class.getResource("flag.png").toString(), height, width, false, false));
        this.x = x;
        this.y = y;

        allTanks = new Text(100, 0, String.valueOf(Main.root.getListObj().size()));
        allTanks.setFont(Font.font("Arial", FontWeight.BOLD, 30));

        listFlag.add(this);

        text = new Text(100, 100, "Не підібраний");
        text.setFont(Font.font("Arial", FontWeight.BOLD, 30));

        VBox vbox1 = new VBox(allTanks);
        VBox vbox2 = new VBox(text);

        vbox1.setAlignment(Pos.TOP_LEFT);
        vbox2.setAlignment(Pos.BOTTOM_LEFT);

        vbox1.setPadding(new Insets(-50, 0, 0, 0)); // Зсув тексту підібраності вниз на 20 одиниць
        vbox2.setPadding(new Insets(0, 0, -50, 0)); // Зсув тексту підібраності вниз на 20 одиниць

        this.getChildren().addAll(imageView ,vbox1,vbox2);
//        getChildren().addAll(vbox1, vbox2);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                allTanks = new Text(String.valueOf(Main.root.getListObj().size()));

                for (smok object : Main.root.getListObj()) {
                    if (getBoundsInParent().intersects(object.getBoundsInParent()) && Flag.pickedUpBy == null) {
                        Bases tanksCommandSystem = object.getTanksCommandSystem();
                        tanksCommandSystem.incFlag();
                        Flag.pickedUpBy = object;
                        object.moveTo(tanksCommandSystem);
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
