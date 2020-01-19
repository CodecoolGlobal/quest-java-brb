package com.codecool.quest;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.GameMap;
import com.codecool.quest.logic.MapLoader;
import com.codecool.quest.logic.actors.*;
import com.codecool.quest.logic.items.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.Math;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.stage.StageStyle;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class Main extends Application {

    private boolean shooting;

    public GameMap getMap() {
        return map;
    }

    GameMap map;
    Canvas canvas;
    GraphicsContext context;
    Class playerCast;
    Label healthLabel = new Label();
    Label combatingLabel = new Label();
    Label powerLabel = new Label();
    GridPane ui = new GridPane();
    ListView<String> inventoryList;
    ListView<String> shopList;
    Stage mainStage;
    BorderPane bp;
    Label weaponDurability = new Label();
    Label helmetDurability = new Label();
    Label shieldDurability = new Label();
    Label shopLabel = new Label();
    Label coins = new Label();
    Label defenseLabel = new Label();
    Armory savedweapon;
    Helmet savedhelmet;
    Shield savedshield;
    Cell savedPlayerPosition;
    ProgressIndicator pi = new ProgressIndicator(0);
    HashMap<String, Class<?>> itemTypes = new HashMap<>() {{
        put("KEY", Key.class);
        put("HEALTHPOTION", HealthPotion.class);
        put("SHOVEL", Shovel.class);
    }};
    // slow moving enemies
    Timeline slowTimeline = new Timeline(
            new KeyFrame(Duration.seconds(1), e -> moveSlowEnemies())
    );
    // fast moving enemies
    Timeline fastTimeline = new Timeline(
            new KeyFrame(Duration.seconds(0.5), e -> moveFastEnemies())
    );
    // projectiles fired by the player
    Timeline bulletTimeline = new Timeline(
            new KeyFrame(Duration.seconds(0.2), e -> moveBullets())
    );
    //its basically only used by the cooldown timer
    Timeline veryfast = new Timeline(
            new KeyFrame(Duration.seconds(0.01), e -> {
                prorefresh();
                updateSpellProgress();
            })
    );

    private ObservableList<String> inventoryLabels = FXCollections.observableArrayList();
    private static ArrayList<Consumable> playerInventory = new ArrayList<>();
    private ObservableList<String> shopItemLabels = FXCollections.observableArrayList();


    private static String[] levels = {"/level1.txt", "/level2.txt", "/level3.txt"};
    private static String[] caves = {"/cave1.txt", "/cave2.txt", "/cave3.txt"};
    private static int nextLevel = 0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        mainStage = primaryStage;
        setUpCharacterSelect(primaryStage);
    }

    public void setUpMain(Stage primaryStage, Class choosenCast) {
        playerCast = choosenCast;
        map = MapLoader.loadMap(levels[nextLevel], choosenCast);
        canvas = new Canvas(
                11 * Tiles.TILE_WIDTH,
                11 * Tiles.TILE_WIDTH);
        context = canvas.getGraphicsContext2D();
        setUpUi();

        BorderPane borderPane = new BorderPane();
        bp = borderPane;
        borderPane.setCenter(canvas);
        borderPane.setRight(ui);
        Scene scene = new Scene(borderPane);

        primaryStage.setScene(scene);

        scene.setOnKeyPressed(this::onKeyPressed);
        scene.setOnKeyReleased(this::onKeyReleased);

        primaryStage.setTitle("Codecool Quest");
        primaryStage.show();
        startAllMovement();
    }

    private void setUpUi() {
        setUpWindow();
        setUpHealth();
        setUpPower();
        setUpInventory();
        setUpDurability();
        setUpCombatLogs();
        setSpellProgress();
        setUpBank();
        setUpShop();
        restockShop();
        setUpDefense();
    }

    private void setUpDefense() {
        Label defense = new Label("Defense: ");
        ui.add(defense, 0, 2);
        setLabelStyle(defense, 15);
        defense.setTextFill(Color.PURPLE);
        ui.add(defenseLabel, 1, 2);
        setLabelStyle(defenseLabel, 15);
        defenseLabel.setTextFill(Color.PURPLE);
        defenseLabel.setEffect(new Glow(0.5));
    }

    public void setSpellProgress() {
        Label pogressLabel = new Label("Spell Cooldown: ");
        setLabelStyle(pogressLabel, 14);
        ui.add(pogressLabel, 0, 13);
        ui.add(pi, 1, 13);
    }

    public void setUpWindow() {
        ui.setPrefWidth(250);
        ui.setPadding(new Insets(10));
        ui.setVgap(3);
    }

    public void setUpDurability(){
        Label dura = new Label("Durability:");
        ui.add(dura, 0, 6);
        setLabelStyle(dura, 15);
        ui.add(helmetDurability, 0, 8);
        setLabelStyle(helmetDurability, 14);
        ui.add(shieldDurability, 0, 9);
        setLabelStyle(shieldDurability, 14);
        ui.add(weaponDurability, 0, 7);
        setLabelStyle(weaponDurability, 14);

    }

    public void setUpBank() {
        Label bank = new Label("Coins: ");
        setLabelStyle(bank, 15);
        ui.add(bank, 0, 3);
        ui.add(coins, 1, 3);
        setLabelStyle(coins, 15);
        bank.setTextFill(Color.rgb(193, 183, 0));
        coins.setTextFill(Color.rgb(204, 183, 49));
        coins.setEffect(new Glow(0.5));
    }


    public void setUpCharacterSelect(Stage primaryStage) {
        Button cast1 = new Button("");
        cast1.setPadding(new Insets(250, 120, 250, 120));
        cast1.setBackground(Background.EMPTY);
        cast1.setOnAction(e -> setUpMain(primaryStage, Warrior.class));

        Button cast2 = new Button("");
        cast2.setPadding(new Insets(250, 120, 250, 120));
        cast2.setBackground(Background.EMPTY);
        cast2.setOnAction(e -> setUpMain(primaryStage, Rouge.class));

        Button cast3 = new Button("");
        cast3.setBackground(Background.EMPTY);
        cast3.setPadding(new Insets(250, 120, 250, 120));
        cast3.setOnAction(e -> setUpMain(primaryStage, Mage.class));

        HBox buttonBox = new HBox();
        buttonBox.setPadding(new Insets(0, 30, 0, 25));
        buttonBox.getChildren().addAll(cast1, cast2, cast3);
        buttonBox.setSpacing(10);

        VBox root = new VBox();
        FileInputStream file = null;
        try {
            file = new FileInputStream("src/main/resources/charselect.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BackgroundImage myBI = new BackgroundImage(new Image(file),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        root.setBackground(new Background(myBI));
        root.getChildren().addAll(buttonBox);
        root.setSpacing(0);
        root.setMinSize(800, 600);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Character Select");
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
        ui.add(inventory, 0, 4);
        setLabelStyle(inventory, 15);
        inventory.setTextFill((Color.SLATEBLUE));
        inventoryList = new ListView<>(inventoryLabels);
        inventoryList.setPrefSize(170, 90);
        inventoryList.setOnKeyPressed(key -> {
            if (key.getCode().equals(KeyCode.ENTER)) itemUsed();
        });
        ui.add(inventoryList, 0, 5);
    }

    public void restockShop() {
        shopItemLabels.clear();
        for (int i = 0; i < 5; i++) {
            shopItemLabels.add(ShopItems.getRandomShopItem());
        }
    }

    public void setUpShop() {
        shopLabel.setText("Shop:\n  Price    Item");
        ui.add(shopLabel, 0, 19);
        setLabelStyle(shopLabel, 14);
        shopLabel.setTextFill((Color.SLATEBLUE));
        shopList = new ListView<>(shopItemLabels);
        shopList.setPrefSize(180, 160);
        shopList.setOnKeyPressed(key -> {
            if (key.getCode().equals(KeyCode.ENTER)) buyItem();
        });
        ui.add(shopList, 0, 20);
        shopList.setVisible(false);
        shopLabel.setVisible(false);
    }

    private void buyItem() {
        String choosenItem = shopList.getSelectionModel().getSelectedItem().toUpperCase().split("   ")[1];
        System.out.println(choosenItem);
        if (ShopItems.valueOf(choosenItem).getWorth() <= map.getPlayer().getBank()) {
            shopItemLabels.remove(shopList.getSelectionModel().getSelectedItem());
            map.getPlayer().withdrawFromBank(ShopItems.valueOf(choosenItem).getWorth());
            try {
                ItemTypes.valueOf(choosenItem).getItemName().getConstructor(Cell.class).newInstance(map.getPlayer().getCell());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
            map.getPlayer().pickUp();
            updateInventory();
        }
    }

    public void setUpCombatLogs() {
        Label combat = new Label("Nearby Enemies: ");
        ui.add(combat, 0, 14);
        setLabelStyle(combat, 15);
        ui.add(combatingLabel, 0, 15);
        setLabelStyle(combatingLabel, 13);
        combatingLabel.setMinHeight(200);
        combatingLabel.setAlignment(Pos.TOP_LEFT);
    }

    private void updateSpellProgress() {
        pi.setProgress((double) java.time.Duration.between(map.getPlayer().getSpellLastUsed(), Instant.now()).toMillis() / map.getPlayer().getSpellCooldown());
    }


    public void startAllMovement() {
        slowTimeline.setCycleCount(Animation.INDEFINITE);
        fastTimeline.setCycleCount(Animation.INDEFINITE);
        bulletTimeline.setCycleCount(Animation.INDEFINITE);
        veryfast.setCycleCount(Animation.INDEFINITE);
        slowTimeline.play();
        fastTimeline.play();
        bulletTimeline.play();
        veryfast.play();
    }

    public void endGame() {
        if (isGameOver()) showGameOverAlert();
    }

    private void itemUsed() {
        Class<?> item = itemTypes.get(inventoryList.getSelectionModel().getSelectedItem());
        map.getPlayer().useItem(item);
        if (item == Shovel.class && map.getPlayer().isDiggable()){
            savedPlayerPosition = map.getPlayer().getCell();
            dig();
        }
        //refresh();
        updateInventory();
    }


    private void restart() {
        nextLevel = 0;
        setStage("/level1.txt");
        updateInventory();
    }


    public boolean isGameOver() {
        return map.getPlayer().isDead();
    }

    private void showVictoryAlert() {
        Alert victory = new Alert(Alert.AlertType.CONFIRMATION);
        victory.setTitle("Victory");
        victory.setHeaderText(null);
        victory.setContentText("Victory, you found the crown and became the ruler of the kingdom! Would you like to play again?");
        victory.initStyle(StageStyle.TRANSPARENT);

        ButtonType buttonNewGame = new ButtonType("Yes");
        ButtonType buttonClose = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        victory.getButtonTypes().setAll(buttonNewGame, buttonClose);

        Optional<ButtonType> result = victory.showAndWait();
        if (result.get() == buttonNewGame) {
            restart();
        } else {
            mainStage.close();
        }
    }

    private void showGameOverAlert() {
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
            restart();
        } else {
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
            case SHIFT:
                shooting = true;
                break;
            case W:

                if (shooting && map.getPlayer().getWeapon() instanceof RangedWeapon)
                    ((RangedWeapon) map.getPlayer().getWeapon()).shoot(map.getPlayer().getCell().getNeighbor(0, -1), 0, -1);
                else map.getPlayer().move(0, -1);
                break;
            case S:

                if (shooting && map.getPlayer().getWeapon() instanceof RangedWeapon)
                    ((RangedWeapon) map.getPlayer().getWeapon()).shoot(map.getPlayer().getCell().getNeighbor(0, 1), 0, 1);
                else map.getPlayer().move(0, 1);
                break;
            case A:

                if (shooting && map.getPlayer().getWeapon() instanceof RangedWeapon)
                    ((RangedWeapon) map.getPlayer().getWeapon()).shoot(map.getPlayer().getCell().getNeighbor(-1, 0), -1, 0);
                else map.getPlayer().move(-1, 0);
                break;
            case D:

                if (shooting && map.getPlayer().getWeapon() instanceof RangedWeapon)
                    ((RangedWeapon) map.getPlayer().getWeapon()).shoot(map.getPlayer().getCell().getNeighbor(1, 0), 1, 0);
                else map.getPlayer().move(1, 0);

                break;
            case E:
                map.getPlayer().pickUp();
                updateInventory();
                break;
            case Q:
                map.getPlayer().castSpell();
                break;

        }
       // refresh();
        if (map.getPlayer().isStairs()) {
            saveInventory();
            setStage(levels[++nextLevel]);
            loadInventory();
        }
        else if (map.getPlayer().isObjective()) {
            showVictoryAlert();
        }
        if (map.getPlayer().isShop()) {
            openShop();
        } else closeShop();

        if (map.getPlayer().isExitCave()) {
            returnToLevel();
        }
        endGame();
    }

    private void returnToLevel() {
        saveInventory();
        setStage(levels[nextLevel]);
        loadInventory();
        map.movePlayeToCell(savedPlayerPosition);
    }

    private void closeShop() {
        shopList.setVisible(false);
        shopLabel.setVisible(false);

    }

    private void openShop() {
        shopList.setVisible(true);
        shopLabel.setVisible(true);
    }

    private void onKeyReleased(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.SHIFT) {
            shooting = false;
        }
    }

    public void setStage(String level) {
        map = MapLoader.loadMap(level, playerCast);
        context = canvas.getGraphicsContext2D();
        bp.setCenter(canvas);
        restockShop();
    }

    private void saveInventory() {
        savedhelmet = map.getPlayer().getHelmet();
        savedshield = map.getPlayer().getShield();
        savedweapon = map.getPlayer().getWeapon();
        playerInventory = (ArrayList<Consumable>) map.getPlayer().getInventory();

    }

    private void loadInventory() {
        map.getPlayer().setInventory(playerInventory);
        map.getPlayer().setHelmet(savedhelmet);
        map.getPlayer().setShield(savedshield);
        map.getPlayer().setWeapon(savedweapon);
    }

    private void moveSlowEnemies() {
        for (Actor enemy : map.getSlowEnemies()) {
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

    }

    private void moveFastEnemies() {
        for (Actor enemy : map.getFastEnemies()) {
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

    }

    private void moveBullets() {
        for (Ammo bullet : map.getAllAmmos()) {
            bullet.moveBullet();
        }
        //refresh();
    }
/*
    private void refresh() {
        System.out.println(savedPlayerPosition != null ? "X:"+savedPlayerPosition.getX()+"Y:"+savedPlayerPosition.getY() : "dunno");
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
                } else if (cell.getAmmo() != null) {
                    Tiles.drawTile(context, cell.getAmmo(), x, y);
                } else {
                    Tiles.drawTile(context, cell, x, y);
                }
            }
        }
        updateUI();
    }
*/
    private void prorefresh() {
        int playerX = map.getPlayer().getX();
        int playerY = map.getPlayer().getY();
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        Tiles.iterator = Tiles.greenery.listIterator();
        for (int x = playerX-5; x < playerX+6; x++) {
            for (int y = playerY-5; y < playerY+6; y++) {
                Cell cell = map.getCell(x, y);
                if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor(), x, y,playerX,playerY);
                } else if (cell.getItem() != null) {
                    Tiles.drawTile(context, cell.getItem(), x, y,playerX,playerY);
                } else if (cell.getAmmo() != null) {
                    Tiles.drawTile(context, cell.getAmmo(), x, y,playerX,playerY);
                } else {
                    Tiles.drawTile(context, cell, x, y,playerX,playerY);
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
            weaponDurability.setText("  " + map.getPlayer().getWeapon().getClass().getSimpleName() + ": " + map.getPlayer().getWeapon().getMaxDurability() + "/" + map.getPlayer().getWeapon().getDurability());
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
        coins.setText("" + map.getPlayer().getBank());
    }


    private void updateUI() {
        updateLabels();
        updateDurabilites();
        updateNearbyEnemies();
    }

    private void dig() {
        saveInventory();
        setStage(caves[(int) Tiles.getRandomIntegerBetweenRange(0, caves.length - 1)]);
        loadInventory();
    }

    public static void setTimeout(Runnable runnable, int delay) {
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            } catch (Exception e) {
                System.err.println(e);
            }
        }).start();
    }
}
