package com.codecool.quest;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.GameMap;
import com.codecool.quest.logic.MapLoader;
import com.codecool.quest.logic.actors.Actor;
import com.codecool.quest.logic.items.HealthPotion;
import com.codecool.quest.logic.items.Item;
import com.codecool.quest.logic.items.Key;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.effect.Glow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
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

    HashMap<String, Class<?>> itemTypes = new HashMap<>(){{
        put("key",Key.class);
        put("healthPotion", HealthPotion.class);
    }};
    Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(1), e -> {
                try {
                    moveEnemies();
                } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException ex) {
                    ex.printStackTrace();
                }
            })
    );

    private ObservableList<String> inventoryLabels = FXCollections.observableArrayList();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        setUpWindow();
        setUpHealth();
        setUpPower();
        setUpInventory();
        setUpCombatLogs();

        BorderPane borderPane = new BorderPane();
        bp = borderPane;
        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        mainStage = primaryStage;
        refresh();
        scene.setOnKeyPressed(keyEvent -> {
            try {
                onKeyPressed(keyEvent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        primaryStage.setTitle("Codecool Quest");
        primaryStage.show();
        startEnemyMovement();
    }

    public void setUpWindow() {
        ui.setPrefWidth(250);
        ui.setPadding(new Insets(10));
    }

    public void setUpHealth() {
        Label hp = new Label("Health: ");
        ui.add(hp, 0, 0);
        hp.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
        hp.setTextFill(Color.GREEN);
        ui.add(healthLabel, 1, 0);
        healthLabel.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
        healthLabel.setTextFill(Color.GREEN);
        healthLabel.setEffect(new Glow(0.5));
    }

    public void setUpPower() {
        Label pwr = new Label("Power: ");
        ui.add(pwr, 0, 1);
        pwr.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
        pwr.setTextFill(Color.RED);
        ui.add(powerLabel, 1, 1);
        powerLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.REGULAR, 15));
        powerLabel.setTextFill(Color.RED);
        powerLabel.setEffect(new Glow(0.5));
    }

    public void setUpInventory() {
        Label inventory = new Label("Inventory: ");
        ui.add(inventory, 0, 2);
        inventory.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
        inventory.setTextFill((Color.SLATEBLUE));
        list = new ListView<>(inventoryLabels);
        list.setPrefSize(170, 90);
        list.setOnKeyPressed(key -> {
            if (key.getCode().equals(KeyCode.ENTER)) itemUsed();
        });
        ui.add(list,0,3);
    }

    public void setUpCombatLogs() {
        Label combat = new Label("Combat Logs: ");
        ui.add(combat, 0, 4);
        combat.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
        ui.add(combatingLabel, 0, 5);
    }

    public void startEnemyMovement() {
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void endGame() throws Exception {
        if(isGameOver()) showAlert();
    }

    private void itemUsed() {
        // Kell Valami ami az item nevéből vissza adja a megfelelo Classt hozza
        map.getPlayer().useItem(itemTypes.get(list.getSelectionModel().getSelectedItem()));
        refresh();
    }


    private void restart() throws Exception {
        setStage("/level1.txt");
    }


    public boolean isGameOver(){
        return map.getPlayer().isDead();
    }

    private void showAlert() throws Exception {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText("Do you want to restart?");
        alert.initStyle(StageStyle.TRANSPARENT);


        ButtonType buttonRestart = new ButtonType("Restart");
        ButtonType buttonClose = new ButtonType("Quit", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonRestart, buttonClose);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonRestart){
            // ... user chose "restart"
            restart();
        } else {
            // ... user chose quit or closed the dialog
            mainStage.close();
        }
    }

    private void updateInventory(){
        inventoryLabels.clear();
        for(Item item : map.getPlayer().getInventory()){
            inventoryLabels.add(item.getTileName());
        }
    }

    private void onKeyPressed(KeyEvent keyEvent) throws Exception {
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
                map.getPlayer().move(1,0);
                break;
            case E:
                map.getPlayer().pickUp();
                break;

        }
        refresh();
        if(map.getPlayer().isStairs()) {
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

    private void moveEnemies() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        for (Actor enemy: map.getAllEnemies()) {
            double random = Tiles.getRandomIntegerBetweenRange(0, 5);
            int value = (int) random;
            switch(value) {
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
                }
                else if(cell.getItem() != null){
                    Tiles.drawTile(context, cell.getItem(), x, y);
                }
                else {
                    Tiles.drawTile(context, cell, x, y);
                }
            }
        }
        healthLabel.setText("" + map.getPlayer().getHealth());
        powerLabel.setText("" + map.getPlayer().getPower());
        updateNearbyEnemies();
        updateInventory();
    }

    private void updateNearbyEnemies(){
        List<Actor> enemies = map.getPlayer().getCell().getAdjacentEnemies();
        StringBuilder status = new StringBuilder();
        for (Actor enemy : enemies){
            status.append(enemy.getTileName()).append(" HP: ").append(enemy.getHealth()).append(" ").append("Pwr: ").append(enemy.getPower()).append("\n");
        }
        combatingLabel.setText(""+status);
    }
}
