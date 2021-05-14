/**
 * Name: Michael Sun
 * Pennkey: m1chaels
 * Execution: java Game2048
 *
 * Program Description: Runs Board and Tile to execute a 2048 game. Use WASD to move.
 */

public class Game2048
{
    private static Board board = new Board();

    // Has the game ended?
    private static boolean gameActive = true;

    /**
     * Input: none
     * Output: none
     * Description: Sets canvas size of PennDraw to default
     */
    public static void setParameters()
    {
        PennDraw.setCanvasSize();
    }

    /**
     * Input: none
     * Output: none
     * Description: Draws welcome screen with game name, my name, and a countdown
     * delay to start.
     */
    public static void drawWelcomeScreen() throws InterruptedException
    {
        PennDraw.clear();

        // Blue outline
        PennDraw.setPenColor(PennDraw.BLUE);
        PennDraw.square(0.5, 0.5, 0.5);

        // Welcome to 2048 text
        PennDraw.setFontBold();
        PennDraw.setFontSize(60);
        PennDraw.text(0.5, 0.75, "Welcome to 2048");

        // Author credits
        PennDraw.setFontSize(35);
        PennDraw.text(0.5, 0.55, "Created by Michael Sun");

        // Countdown timer
        PennDraw.setPenColor(PennDraw.RED);

        int counter = 5;
        for (int i = 0; i < 5; i++)
        {
            PennDraw.setPenColor(PennDraw.RED);
            PennDraw.text(0.5, 0.25, "Starting in " + counter);
            Thread.sleep(1000);
            PennDraw.setPenColor(PennDraw.WHITE);
            PennDraw.filledSquare(0.7, 0.25, 0.06);
            counter--;
        }
    }

    /**
     * Input: none
     * Output: none
     * Description: Draws black background and white tiles to create a
     * 16-tile grid.
     */
    public static void drawBackground()
    {
        PennDraw.clear();

        // Black background
        PennDraw.setPenColor(PennDraw.BLACK);
        PennDraw.filledSquare(0.5, 0.5, 0.5);

        // 2048 header
        PennDraw.setPenColor(PennDraw.WHITE);
        PennDraw.setFontSize(40);
        PennDraw.text(0.5, 0.93, "2048");

        // Light gray board background
        PennDraw.setPenRadius(0.005);
        PennDraw.square(0.5, 0.5, 0.5);
        PennDraw.setPenColor(PennDraw.WHITE);

        // Individual squares
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                PennDraw.filledSquare(board.getTileX(i), board.getTileX(j),
                                      0.09);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException
    {
        setParameters();
        drawWelcomeScreen();

        PennDraw.enableAnimation(240);

        while (gameActive) {

            drawBackground();
            board.draw();

            // Checks to see if player has won
            if (board.winChecker()) {
                gameActive = false;
                board.winDrawing();
            }

            // Checks to see if player has lost
            else if (board.lossChecker()) {
                gameActive = false;
                board.loseDrawing();
            }

            // Processes next move
            else if (PennDraw.hasNextKeyTyped() && gameActive) {
                char key = PennDraw.nextKeyTyped();
                board.move(key);
            }

            PennDraw.advance();
        }
    }
}
