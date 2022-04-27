package src.enviroment;

import java.util.Arrays;

public enum MapType {
    LIVING_OBJECT, //x
    FOOD, // o
    OBSTACLE, // □
    EMPTY; // 0
    public static MapType[][] newMap( int size) {
        MapType mapMatrix[][] = new MapType[size][size];
        // for(int i = 0; i<size; i++){
        //     for(int j = 0; j<size; j++){
        //        mapMatrix[i][j] = EMPTY;
        //     }

        for(int i = 0; i<size; i++){
            Arrays.fill(mapMatrix[i], EMPTY);
        }
        return mapMatrix;
    }
    @Override 
    public String toString() {
        switch (this) {
            case LIVING_OBJECT:
                return "x";
            case FOOD:
                return "o";
            case OBSTACLE:
                return "1";
            case EMPTY:
                return "0";
            default:
                return " ";
        }
    }
    
}
