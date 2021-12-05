package edu.isu.cs2263.CS2263_Acquire_Project;


import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.jupiter.api.*;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testfx.api.FxAssert.verifyThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class TestUIController extends ApplicationTest {
    @BeforeEach
    public void setUpClass() throws Exception {
        ApplicationTest.launch(UI.class);
    }
    @Override
    public void start(Stage stage) throws Exception{
        stage.show();
    }

    @AfterEach
    public void afterEachTest() throws Exception {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    @Test
    public void  testMergeTurn() throws InterruptedException {
        clickOn("#newGameButton");
        clickOn("2");
        clickOn("4");
        clickOn("OK");

        List<String> mergePlayers = new ArrayList<>();
        mergePlayers.add("Player 1");

        List<String> mergeCorps = new ArrayList<>();
        mergeCorps.add("American");

        GameState gameState = GameState.getInstance(null);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                gameState.getScoreboard().runMergeTurn(mergeCorps, "Luxor", mergePlayers);
            }
        });
    }

    @Test
    @Order(1)
    public void testNewGameButton() throws InterruptedException {
        clickOn("#newGameButton");
        clickOn("2");
        clickOn("4");
        clickOn("OK");
        assertThat(lookup("turnLabel").equals("Player 1"));
    }
    @Test
    @Order(2)
    public void testCheckHandTiles() throws InterruptedException {
        clickOn("#newGameButton");
        clickOn("2");
        clickOn("4");
        clickOn("OK");
        GameState gameState = GameState.getInstance(null);
        PlayerInfo playerInfo = gameState.getCurrentPlayer();
        Hand testHand = playerInfo.getPHand();
        Tile testTile1 = testHand.getPlayersTiles().get(0);
        Tile testTile2 = testHand.getPlayersTiles().get(1);
        Tile testTile3 = testHand.getPlayersTiles().get(2);
        Tile testTile4 = testHand.getPlayersTiles().get(3);
        Tile testTile5 = testHand.getPlayersTiles().get(4);
        Tile testTile6 = testHand.getPlayersTiles().get(5);

        assertThat("Tile0".equals(testTile1.getLocation()));
        assertThat("Tile1".equals(testTile2.getLocation()));
        assertThat("Tile2".equals(testTile3.getLocation()));
        assertThat("Tile3".equals(testTile4.getLocation()));
        assertThat("Tile4".equals(testTile5.getLocation()));
        assertThat("Tile5".equals(testTile6.getLocation()));
    }
    @Test
    @Order(3)
    public void testCheckScoreboard(){
        clickOn("#newGameButton");
        clickOn("2");
        clickOn("4");
        clickOn("OK");

        GameState gameState = GameState.getInstance(null);
        assertThat(gameState.getScoreboard().getPlayerScore("Player 1") == 6000);
        assertThat(gameState.getScoreboard().getPlayers().getPlayerByName("Player 1").getPWallet().getCash() == 6000);
    }
    @Test
    @Order(4)
    public void testShowRules(){
        clickOn("#newGameButton");
        clickOn("2");
        clickOn("4");
        clickOn("OK");
        clickOn("#showRulesButton");
        verifyThat("#showRulesButton", NodeMatchers.isVisible());
    }
    @Test
    @Order(5)
    public void testPlayTile() throws InterruptedException {
        clickOn("#newGameButton");
        clickOn("2");
        clickOn("4");
        clickOn("OK");

        GameState gameState = GameState.getInstance(null);
        PlayerInfo playerInfo = gameState.getCurrentPlayer();
        Hand testHand = playerInfo.getPHand();
        Tile testTile1 = testHand.getPlayersTiles().get(0);

        clickOn("#Tile0");
        assertThat(gameState.getGameboard().getTile(testTile1.getRow(), testTile1.getCol()).isStatus() == true);
    }
}
