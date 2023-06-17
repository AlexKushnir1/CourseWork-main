package com.example.kursova;

import Tanks.grom;
import Tanks.smok;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Bases extends StackPane {

    //    public static Image systemImage = new Image(Main.class.getResource("System.png").toString(), 400, 400, false, false);
    private final ObservableList<smok> listObjInSys = FXCollections.observableArrayList();
    public static ObservableList<Bases> listSystem = FXCollections.observableArrayList();
    private Team team;
    private String name;
    public final ImageView imageView;
    private int x;
    private int y;
    private static final int height = 600;
    private static final int weight = 475;
    //    private ImageView imageView = new ImageView(systemImage); // Текстура
//    private Rectangle hitbox = new Rectangle(size, size); // Хітбокс
    private Text flagCountText;
    private Text numOfTeamTanks;

    private Integer flagCounter = 0;

    private static Random random = new Random();

    private double maxHp = 400;
    private double hp = maxHp;

    private int resources = 20;
    private double damage = 20;


    public Bases(String name, int x, int y, ImageView imageView) {
        this.name = name;
        this.x = x;
        this.y = y;

        this.setPrefSize(weight, height);

        this.imageView = imageView;


//        hitbox.setFill(Color.GRAY);
//        hitbox.setStroke(Color.BLACK);
//        hitbox.setStrokeWidth(3);
//        hitbox.setArcWidth(size);
//        hitbox.setArcHeight(size);


//        Circle circle = new Circle(200, Color.TRANSPARENT);
//        circle.setFill(new ImagePattern(imageView.getImage()));

        numOfTeamTanks = new Text(String.format("Танків на базі: %d", listObjInSys.size()));
        numOfTeamTanks.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        flagCountText = new Text("Прапорів доставлено: 0");
        flagCountText.setFont(Font.font("Arial", FontWeight.BOLD, 30));

        VBox vbox1 = new VBox(numOfTeamTanks);
        VBox vbox2 = new VBox(flagCountText);

        vbox1.setAlignment(Pos.TOP_LEFT);
        vbox2.setAlignment(Pos.BOTTOM_LEFT);

        vbox1.setPadding(new Insets(-30,30,0,0));
        vbox2.setPadding(new Insets(30,30,0,0));

        listSystem.add(this);
//        this.getChildren().addAll(hitbox, circle, vbox);
        this.getChildren().addAll(imageView, vbox1, vbox2);   //hitbox,

//        AnimationTimer animationTimer = new AnimationTimer() {
//            @Override
//            public void handle(long now) {
//                listObjInSys.clear();
////                circle.setRotate(circle.getRotate() + 1);
//                for (smok object : Main.root.getListObj()) {
//                    if (getBoundsInParent().intersects(object.getBoundsInParent())) {
//                        if (object.equals(Flag.pickedUpBy)) {
//                            Flag.pickedUpBy = null;
//                            incFlagCounter();
//                        }
//
//                        if (!listObjInSys.contains(object)) {
//                            listObjInSys.add(object);
//                        }
//                    }
//                }
//                numOfTeamTanks.setText(String.valueOf(hp));
//            }
//        };
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                listObjInSys.clear();

                for (smok object : Main.root.getListObj()) {
                    if (getBoundsInParent().intersects(object.getBoundsInParent())) {
                        if (object.equals(Flag.pickedUpBy)) {
                            Flag.pickedUpBy = null;
                            incFlagCounter();
                        }

                        if (!listObjInSys.contains(object)) {
                            listObjInSys.add(object);
                        }
                    }
                }
                numOfTeamTanks.setText(String.format("Танків на базі: %d", listObjInSys.size()));
            }
        }, 0, 300);

//        animationTimer.start();

//        Timeline timeline = new Timeline(
//                new KeyFrame(Duration.ZERO, event -> {
//                    for (smok object : Main.root.getListObj()) {
//                        if (!(object.getCommand() == getFraction())) {
//                            if (getBoundsInParent().intersects(object.getBoundsInParent())) {
//                                if (hp <= 0) {
//                                    if (team != null) {
//                                        team.removeSystem(Bases.this);
//                                    }
//                                    if (object.getCommand() != null) {
//                                        object.getCommand().addSystem(Bases.this);
//                                    }
//                                }
//                                if (getBoundsInParent().intersects(object.getBoundsInParent())) {
//                                    attack(object);
//                                    takeDamage(object);
//                                }
//                            }
//                        }
//                    }
//                }),
//                new KeyFrame(Duration.millis(700), event -> {})
//        );

//        timeline.setCycleCount(Timeline.INDEFINITE);
//        timeline.play();
    }

    public void generateResources() {
        Platform.runLater(() -> {
            if (getFraction() != null) {
                resources++;
                if (getResources() >= 10) {
                    Random random1 = new Random();
                    if (random1.nextInt(100) > 50) {
                        setResources(getResources() - 10);
                        smok obj = new smok();
                        obj.setX(getRandomXinSys());
                        obj.setY(getRandomYinSys());
                        Main.root.addObj(obj);
                        team.addObj(obj);
                    } else {
                        setResources(getResources() - 10);
                        grom obj = new grom();
                        obj.setX(getRandomXinSys());
                        obj.setY(getRandomYinSys());
                        Main.root.addObj(obj);
                        team.addObj(obj);
                    }
                }
            }
        });
    }

    public void setFraction(Team team) {
        if (this.team != null) {
            this.team.removeSystem(this);
        }
        this.team = team;
        this.hp = maxHp;
//        hitbox.setFill(team.getFcolor());
//        flagCountText.setText(name + " " + team.getName());
    }

    public void removeFraction() {
        if (this.team != null) {
            this.team.removeSystem(this);
            this.team = null;
//            hitbox.setFill(Color.GRAY);
//            flagCountText.setText(name);
        }
    }

    public Team getFraction() {
        return team;
    }

    public Long getFractionTankCount() {
        return Main.root.getListObj().stream()
                .filter(t -> t.hasFraction(this.team))
                .count();
    }

    public void setHp(double hp) {
        this.hp = hp;
    }

    public double getHp() {
        return hp;
    }

//    public double getRotation() {
//        return imageView.getRotate();
//    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getName() {
        return name;
    }

//    public double getWidthP() {
//        return hitbox.getWidth();
//    }
//
//    public double getHeightP() {
//        return hitbox.getHeight();
//    }

    public static ObservableList<Bases> getListSystem() {
        return listSystem;
    }

    public ObservableList<smok> getListObjInSys() {
        return listObjInSys;
    }

    public int getResources() {
        return resources;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setResources(int resources) {
        this.resources = resources;
    }

    public int getRandomXinSys() {
        return random.nextInt((int) getPrefWidth()) + getX();
    }

    public int getRandomYinSys() {
        return random.nextInt((int) getPrefHeight()) + getY();
    }

    public void attack(smok obj) {
        if (obj != null) {
            if (obj.getCommand() == null || !obj.getCommand().equals(this.team)) {
                // Розрахунок пошкодження атаки
                double attackDamage = damage;

                // Зменшення пошкодження в залежності від броні цілі
                attackDamage -= obj.getDamage();

                // Застосування пошкодження до цілі
                obj.setHealth(obj.getHealth() - attackDamage);

                // Перевірка на смерть цілі
                if (obj.getHealth() <= 0) {
                    System.out.println("Ціль " + obj.getName() + " була убита!");
                    obj.dead();
                } else {
                    System.out.println("Ціль " + obj.getName() + " зазнала пошкоджень: " + attackDamage + " HP");
                }
            }
        }
    }

    public void takeDamage(smok obj) {
        if (this.getFraction() == null) {
            // Розрахунок пошкодження атаки
            double attackDamage = obj.getArmor();
            // Застосування пошкодження до цілі
            setHp(getHp() - attackDamage);
        } else if (!this.getFraction().equals(obj.getCommand())) {
            // Розрахунок пошкодження атаки
            double attackDamage = obj.getArmor();
            // Застосування пошкодження до цілі
            setHp(getHp() - attackDamage);
        }
    }

    public ImageView getObjImage(double size) {
        // Отримуємо зображення об'єкту з використанням снапшота
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        Image image = this.snapshot(parameters, null);

        // Змінюємо розмір отриманого зображення до бажаного розміру
        double scaleFactor = size / Math.max(image.getWidth(), image.getHeight());
        WritableImage scaledImage = new WritableImage(
                (int) (image.getWidth() * scaleFactor),
                (int) (image.getHeight() * scaleFactor));
        PixelWriter writer = scaledImage.getPixelWriter();
        PixelReader reader = image.getPixelReader();
        for (int y = 0; y < scaledImage.getHeight(); y++) {
            for (int x = 0; x < scaledImage.getWidth(); x++) {
                writer.setArgb(x, y, reader.getArgb((int) (x / scaleFactor), (int) (y / scaleFactor)));
            }
        }
        return new ImageView(scaledImage);
    }

    public void incFlagCounter() {
        this.flagCounter += 1;
        flagCountText.setText(String.format("Прапорів доставлено: %d", getFlagCounter()));
    }

    public Integer getFlagCounter() {
        return flagCounter;
    }

    public void setFlagCounter(Integer flagCounter) {
        this.flagCounter = flagCounter;
    }

}
