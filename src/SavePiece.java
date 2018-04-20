import java.awt.Color;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JPanel;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author alu20490610w
 */
public class SavePiece extends JPanel {

    private Shape saveShape;

    private Tetrominoes[][] saveMatrix;

    public SavePiece() {

        super();
        saveShape = null;
        saveMatrix = new Tetrominoes[NextPiecePanel.MATRIX_SIZE][NextPiecePanel.MATRIX_SIZE];

        for (int row = 0; row < NextPiecePanel.MATRIX_SIZE; row++) {
            for (int col = 0; col < NextPiecePanel.MATRIX_SIZE; col++) {

                saveMatrix[row][col] = Tetrominoes.NoShape;
            }
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (saveShape != null) {
            saveShape.draw(g, 1, 1, squareWidth(), squareHeight());
        }

    }

    


    private int squareWidth() {

        return getWidth() / NextPiecePanel.MATRIX_SIZE;
    }

    private int squareHeight() {

        return getHeight() / NextPiecePanel.MATRIX_SIZE;
    }

    
    

    public Shape setPieceShape(Shape saveShape) {

        if (this.saveShape == null) {

            this.saveShape = saveShape;
            repaint();

            return null;
        } else {
            Shape temp = this.saveShape;

            this.saveShape = saveShape;
            repaint();
            return temp;

        }

    }
}
