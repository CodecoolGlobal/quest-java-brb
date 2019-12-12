package com.codecool.quest;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.GameMap;
import com.codecool.quest.logic.MapLoader;
import com.codecool.quest.logic.actors.Actor;
import com.codecool.quest.logic.items.HealthPotion;
import com.codecool.quest.logic.items.Item;
import com.codecool.quest.logic.items.Key;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.Math;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.stage.StageStyle;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class Main extends Application {

    public GameMap getMap() {
        return map;
    }

    GameMap map = MapLoader.loadMap("/level1.txt");
    Canvas canvas = new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            map.getHeight() * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();

    Label healthLabel = new Label();
    Label combatingLabel = new Label();
    Label powerLabel = new Label();
    GridPane ui = new GridPane();
    ListView<String> list;
    Stage mainStage;
    BorderPane bp;
    Label weaponDurability = new Label();
    Label helmetDurability = new Label();
    Label shieldDurability = new Label();
    Label defenseLabel = new Label();
    HashMap<String, Class<?>> itemTypes = new HashMap<>() {{
        put("KEY", Key.class);
        put("HEALTHPOTION", HealthPotion.class);
    }};
    Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(1), e -> moveEnemies())
    );

    private ObservableList<String> inventoryLabels = FXCollections.observableArrayList();


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        setUpCharacterSelect(primaryStage);
    }

    public void setUpMain(Stage primaryStage){

        setUpWindow();
        setUpHealth();
        setUpPower();
        setUpInventory();
        setUpCombatLogs();
        ui.setGridLinesVisible(false);
        ui.setVgap(3);

        Label dura = new Label("Durability:");

        ui.add(dura, 0, 5);
        setLabelStyle(dura, 15);
        ui.add(helmetDurability, 0, 7);
        setLabelStyle(helmetDurability, 14);
        ui.add(shieldDurability, 0, 8);
        setLabelStyle(shieldDurability, 14);
        ui.add(weaponDurability, 0, 6);
        setLabelStyle(weaponDurability, 14);

        Label defense = new Label("Defense: ");
        ui.add(defense, 0, 2);
        setLabelStyle(defense, 15);
        defense.setTextFill(Color.PURPLE);
        ui.add(defenseLabel, 1, 2);
        setLabelStyle(defenseLabel, 15);
        defenseLabel.setTextFill(Color.PURPLE);
        defenseLabel.setEffect(new Glow(0.5));
        BorderPane borderPane = new BorderPane();
        bp = borderPane;
        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        mainStage = primaryStage;
        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);

        primaryStage.setTitle("Codecool Quest");
        primaryStage.show();
        startEnemyMovement();
    }

    public void setUpWindow() {
        ui.setPrefWidth(250);
        ui.setPadding(new Insets(10));
    }

    public void setUpCharacterSelect(Stage primaryStage) {

        Button cast1 = new Button("Warrior");
        cast1.setPadding(new Insets(250,100,250,100));
        cast1.setBackground(Background.EMPTY);
        cast1.setOnAction(e -> setUpMain(primaryStage));


        Button cast2 = new Button("Rouge");
        cast2.setPadding(new Insets(250,100,250,100));
        cast2.setBackground(Background.EMPTY);
        cast2.setOnAction(e -> setUpMain(primaryStage));

        Button cast3 = new Button("Mage");
        cast3.setBackground(Background.EMPTY);
        cast3.setPadding(new Insets(250,100,250,100));

        cast3.setOnAction(e -> setUpMain(primaryStage));

        // Create the HBox
        HBox buttonBox = new HBox();
        buttonBox.setPadding(new Insets(0,25,0,25));
        // Add the children to the HBox
        buttonBox.getChildren().addAll(cast1, cast2, cast3);
        // Set the vertical spacing between children to 15px
        buttonBox.setSpacing(0);

        // Create the VBox
        VBox root = new VBox();
        FileInputStream file = null;
        try {
            file = new FileInputStream("/home/adrian/codecool/oop/quest-java-brb/src/main/resources/castselect.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BackgroundImage myBI= new BackgroundImage(new Image(file),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        root.setBackground(new Background(myBI));
        // Add the children to the VBox
        root.getChildren().addAll(buttonBox);
        // Set the vertical spacing between children to X
        root.setSpacing(0);
        // Set the Size of the VBox
        root.setMinSize(800, 600);



        // Create the Scene
        Scene scene = new Scene(root);
        // Add the scene to the Stage
        primaryStage.setScene(scene);
        // Set the title of the Stage
        primaryStage.setTitle("Character Select");
        // Display the Stage
        primaryStage.show();

    }

    public void setUpHealth() {
        Label hp = new Label("Health:");
        ui.add(hp, 0, 0);
        setLabelStyle(hp, 15);
        hp.setTextFill(Color.GREEN);
        ui.add(healthLabel, 1, 0);
        setLabelStyle(healthLabel, 15);
        healthLabel.setTextFill(Color.GREEN);
        healthLabel.setEffect(new Glow(0.5));
    }

    public void setLabelStyle(Label label, int fontSize) {
        label.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, fontSize));
    }

    public void setUpPower() {
        Label pwr = new Label("Attack:");
        ui.add(pwr, 0, 1);
        setLabelStyle(pwr, 15);
        pwr.setTextFill(Color.RED);
        ui.add(powerLabel, 1, 1);
        setLabelStyle(powerLabel, 15);
        powerLabel.setTextFill(Color.RED);
        powerLabel.setEffect(new Glow(0.5));
    }

    public void setUpInventory() {
        Label inventory = new Label("Inventory:");
        ui.add(inventory, 0, 3);
        setLabelStyle(inventory, 15);
        inventory.setTextFill((Color.SLATEBLUE));
        list = new ListView<>(inventoryLabels);
        list.setPrefSize(170, 90);
        list.setOnKeyPressed(key -> {
            if (key.getCode().equals(KeyCode.ENTER)) itemUsed();
        });
        ui.add(list, 0, 4);
    }

    public void setUpCombatLogs() {
        Label combat = new Label("Nearby Enemies: ");
        ui.add(combat, 0, 14);
        setLabelStyle(combat, 15);
        ui.add(combatingLabel, 0, 15);
        setLabelStyle(combatingLabel, 13);
    }

    public void startEnemyMovement() {
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void endGame() {
        if (isGameOver()) showAlert();
    }

    private void itemUsed() {
        map.getPlayer().useItem(itemTypes.get(list.getSelectionModel().getSelectedItem()));
        refresh();
    }


    private void restart() {
        setStage("/level1.txt");
    }


    public boolean isGameOver() {
        return map.getPlayer().isDead();
    }

    private void showAlert() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText("Do you want to restart?");
        alert.initStyle(StageStyle.TRANSPARENT);


        ButtonType buttonRestart = new ButtonType("Restart");
        ButtonType buttonClose = new ButtonType("Quit", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonRestart, buttonClose);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonRestart) {
            // ... user chose "restart"
            restart();
        } else {
            // ... user chose quit or closed the dialog
            mainStage.close();
        }
    }

    private void updateInventory() {
        inventoryLabels.clear();
        for (Item item : map.getPlayer().getInventory()) {
            inventoryLabels.add(item.getTileName().toUpperCase());
        }
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case W:
                map.getPlayer().move(0, -1);
                break;
            case S:
                map.getPlayer().move(0, 1);
                break;
            case A:
                map.getPlayer().move(-1, 0);
                break;
            case D:
                map.getPlayer().move(1, 0);
                break;
            case E:
                map.getPlayer().pickUp();
                break;
        }
        refresh();
        if (map.getPlayer().isStairs()) {
            setStage("/level2.txt");
        }
        endGame();
    }

    public void setStage(String level) {
        map = MapLoader.loadMap(level);
        canvas = new Canvas(
                map.getWidth() * Tiles.TILE_WIDTH,
                map.getHeight() * Tiles.TILE_WIDTH);
        context = canvas.getGraphicsContext2D();
        bp.setCenter(canvas);
    }

    private void moveEnemies() {
        for (Actor enemy : map.getAllEnemies()) {
            double random = Tiles.getRandomIntegerBetweenRange(0, 5);
            int value = (int) random;
            switch (value) {
                case 0:
                    break;
                case 1:
                    enemy.move(0, -1);
                    break;
                case 2:
                    enemy.move(0, 1);
                    break;
                case 3:
                    enemy.move(-1, 0);
                    break;
                case 4:
                    enemy.move(1, 0);
                    break;
            }
        }

        refresh();
    }


    private void refresh() {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        Tiles.iterator = Tiles.greenery.listIterator();
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Cell cell = map.getCell(x, y);
                if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor(), x, y);
                } else if (cell.getItem() != null) {
                    Tiles.drawTile(context, cell.getItem(), x, y);
                } else {
                    Tiles.drawTile(context, cell, x, y);
                }
            }
        }
        updateUI();
    }

    private void updateNearbyEnemies() {
        List<Actor> enemies = map.getPlayer().getCell().getAdjacentEnemies();
        StringBuilder status = new StringBuilder();
        for (Actor enemy : enemies) {
            status.append(enemy.getTileName().toUpperCase()).append(":\n").append("   HP: ").append(enemy.getHealth()).append("\n").append("   Attack: ").append(enemy.getPower()).append("\n");
        }
        combatingLabel.setText("" + status);
    }

    private void updateDurabilites() {
        try {
            weaponDurability.setText("  Weapon: " + map.getPlayer().getWeapon().getMaxDurability() + "/" + map.getPlayer().getWeapon().getDurability());
        } catch (Exception e) {
            weaponDurability.setText("  No weapon");
        }
        try {
            helmetDurability.setText("  Helmet: " + map.getPlayer().getHelmet().getMaxDurability() + "/" + map.getPlayer().getHelmet().getDurability());
        } catch (Exception e) {
            helmetDurability.setText("  No helmet");
        }
        try {
            shieldDurability.setText("  Shield: " + map.getPlayer().getShield().getMaxDurability() + "/" + map.getPlayer().getShield().getDurability());
        } catch (Exception e) {
            shieldDurability.setText("  No shield");
        }
    }

    private void updateLabels() {
        healthLabel.setText("" + map.getPlayer().getHealth() + "/" + map.getPlayer().getMaxHealth());
        powerLabel.setText("" + map.getPlayer().getPower());
        defenseLabel.setText("" + (100 - Math.round(map.getPlayer().getResi() * 100)));
    }

    private void updateUI() {
        updateLabels();
        updateDurabilites();
        updateNearbyEnemies();
        updateInventory();
    }
}
