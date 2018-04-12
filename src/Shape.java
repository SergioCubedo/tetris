/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author victor
 */
public class Shape {

    private Tetrominoes pieceShape;
    private int[][] coordinates;
    private static int[][][] coordsTable = new int[][][]{
        {{0, 0}, {0, 0}, {0, 0}, {0, 0}},
        {{0, -1}, {0, 0}, {-1, 0}, {-1, 1}},
        {{0, -1}, {0, 0}, {1, 0}, {1, 1}},
        {{0, -1}, {0, 0}, {0, 1}, {0, 2}},
        {{-1, 0}, {0, 0}, {1, 0}, {0, 1}},
        {{0, 0}, {1, 0}, {0, 1}, {1, 1}},
        {{-1, -1}, {0, -1}, {0, 0}, {0, 1}},
        {{1, -1}, {0, -1}, {0, 0}, {0, 1}}

    };

    public Shape(Tetrominoes pieceShape) {
        this.pieceShape = pieceShape;
        coordinates = coordsTable[pieceShape.ordinal()];
    }

    public Shape() {
        int randomNumber = (int) (Math.random() * 7 + 1);
        pieceShape = Tetrominoes.values()[randomNumber];
        coordinates = coordsTable[randomNumber];
    }

    public static Shape getRandomShape() {
        return new Shape();
    }

    public int[][] getCoordnates() {
        return coordinates;
    }

    public Tetrominoes getShape() {
        return pieceShape;
    }

    public int getXmin() {
        int min = coordinates[0][0];
        
        for(int i=1; i<coordinates.length;i++){
            if(min>coordinates[i][0]){
                min=coordinates[i][0];
                
            } 
            
        }
        return min;

    }

    public int getXmax() {
        int max = coordinates[0][0];
        
           for(int i=1; i<coordinates.length;i++){
            if(max>coordinates[i][0]){
                max=coordinates[i][0];
                
            } 
            
        }
        return max;

    }

    public int getYmin() {
        int min = coordinates[0][1];
        
            for(int i=1; i<coordinates.length;i++){
            if(min>coordinates[i][1]){
                min=coordinates[i][1];
                
            } 
            
        }
        return min;

    }

    public int getYmax() {
        int max = coordinates[0][1];
        
               for(int i=1; i<coordinates.length;i++){
                if(max>coordinates[i][1]){
                    max=coordinates[i][1];
            } 
            
        }
        return max;
        
        

    }

}
