
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.Timer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author victor
 */
public class Board extends JPanel implements ActionListener {

    class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (canMoveTo(currentShape, currentRow, currentCol - 1)) {
                        currentCol--;
                    }

                    break;
                case KeyEvent.VK_RIGHT:
                    if (canMoveTo(currentShape, currentRow, currentCol + 1)) {
                        currentCol++;
                    }

                    break;
                case KeyEvent.VK_UP:
                    Shape rotaShape = currentShape.rotated();
                    if (canMoveTo(rotaShape, currentRow, currentCol) && !isPaused) {
                        currentShape = rotaShape;
                    }

                    break;
                case KeyEvent.VK_DOWN:
                    if (canMoveTo(currentShape, currentRow + 1, currentCol) && !isPaused) {
                        currentRow++;
                    }
                    break;
                case KeyEvent.VK_P:
                    if (timer.isRunning()) {
                        timer.stop();
                        isPaused = true;
                    } else {
                        timer.start();
                        isPaused = false;
                    }
                default:
                    break;
            }
            repaint();
        }
    }

    public ScoreBoard scoreboard;
    private NextPiecePanel nextPiecePanel;
    private SavePiece savepiece;

    public static final int NUM_ROWS = 22;
    public static final int NUM_COLS = 10;

    private Tetrominoes[][] matrix;
    private int deltaTime;

    private Shape currentShape;

    private int currentRow;
    private int currentCol;

    private Timer timer;

    private boolean isPaused;

    MyKeyAdapter myKeyAdapter;

    public static final int INIT_ROW = -2;

    public Board() {
        super();
        matrix = new Tetrominoes[NUM_ROWS][NUM_COLS];

        myKeyAdapter = new MyKeyAdapter();
        initValues();
        timer = new Timer(deltaTime, this);

    }

    public void setNextPiecePanel(NextPiecePanel p) {
        nextPiecePanel = p;
    }

    public void setSavePiece(SavePiece sp) {
        savepiece = sp;

    }

    public void setScoreBoard(ScoreBoard scoreboard) {
        this.scoreboard = scoreboard;
    }

    public void initValues() {
        setFocusable(true);
        cleanBoard();
        deltaTime = 500;
        currentShape = new Shape();
        currentRow = 0;
        currentCol = NUM_COLS / 2;
        isPaused = false;

    }

    public void initGame() {
        initValues();
        currentShape = Shape.getRandomShape();
        timer.start();
        addKeyListener(myKeyAdapter);
    }

    //Game Main Loop
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (canMoveTo(currentShape, currentRow + 1, currentCol)) {
            currentRow++;
            repaint();
        } else {
            moveCurrentShapeToMatrix();
            checkCompletedLines();
            currentShape = nextPiecePanel.getShape();
            nextPiecePanel.changeShape(Shape.getRandomShape());
            currentShape = new Shape();
            currentRow = INIT_ROW;
            currentCol = NUM_COLS / 2;
        }

    }

    public void checkCompletedLines() {
        for (int row = 0; row < NUM_ROWS; row++) {
            boolean lineCompleted = true;
            for (int col = 0; col < NUM_COLS; col++) {
                if (matrix[row][col] == Tetrominoes.NoShape) {
                    lineCompleted = false;
                }

            }
            if (lineCompleted) {
                removeLine(row);
            }

        }
        repaint();
    }

    public void removeLine(int lineToRemove) {
        for (int row = lineToRemove; row > 0; row--) {
            for (int col = 0; col < NUM_COLS; col++) {
                matrix[row][col] = matrix[row - 1][col];
            }
        }
        for (int col = 0; col < NUM_COLS; col++) {
            matrix[0][col] = Tetrominoes.NoShape;
        }
    }

    private void moveCurrentShapeToMatrix() {
        int[][] squaresArray = currentShape.getCoordnates();
        for (int point = 0; point <= 3; point++) {
            int row = currentRow + squaresArray[point][1];
            int col = currentCol + squaresArray[point][0];
            matrix[row][col] = currentShape.getShape();
        }
    }

    public void cleanBoard() {
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                matrix[row][col] = Tetrominoes.NoShape;
            }
        }
    }

    private boolean canMoveTo(Shape shape, int newRow, int newCol) {
        if ((newCol + shape.getXmin() < 0) || (newCol + shape.getXmax() >= NUM_COLS) || (newRow + shape.getYmax() >= NUM_ROWS) || hitWithMatrix(shape, newRow, newCol)) {
            return false;
        }
        return true;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
        if (currentShape != null) {
            drawCurrentShape(g);
        }
        drawBorder(g);

    }

    public void drawBorder(Graphics g) {
        g.setColor(Color.red);
        g.drawRect(0, 0, NUM_COLS * squareWidth(), NUM_ROWS * squareHeight());
    }

    public void drawBoard(Graphics g) {
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                Util.drawSquare(g, row, col, matrix[row][col], squareWidth(), squareHeight());
            }
        }
    }

    private void drawCurrentShape(Graphics g) {
        int[][] squareArray = currentShape.getCoordnates();
        for (int point = 0; point <= 3; point++) {
            Util.drawSquare(g, currentRow + squareArray[point][1], currentCol + squareArray[point][0], currentShape.getShape(), squareWidth(), squareHeight());
        }
    }

    private int squareWidth() {
        return getWidth() / NUM_COLS;
    }

    private int squareHeight() {
        return getHeight() / NUM_ROWS;
    }

    private boolean hitWithMatrix(Shape shape, int newRow, int newCol) {
        int[][] squaresArray = shape.getCoordnates();
        for (int point = 0; point < 4; point++) {
            int row = squaresArray[point][1] + newRow;
            int col = squaresArray[point][0] + newCol;
            if (row >= 0) {
                if (matrix[row][col] != Tetrominoes.NoShape) {
                    return true;
                }
            }
        }
        return false;

    }

}
