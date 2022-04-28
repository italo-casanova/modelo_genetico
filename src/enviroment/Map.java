package src.enviroment;

import java.util.*;

public class Map {

    public MapType mapMatrix[][];

    private final int[][] maze;
    private final int[][] mazeTest;
    private final int SIZE;
    private final int x;
	private final int y;


    public Map(int size){
        SIZE = size*2 + 1;
        mapMatrix = MapType.newMap(SIZE);
        //all of the livingObjects start at (0,0)
        this.x = size;
		this.y = size;
		maze = new int[this.x][this.y];
        mazeTest = new int[SIZE][SIZE];

		// generateMaze(0, 0);
        // copyMap();


    }

    // implement maze generator algorithm with the maze matrix
    private void generateMaze(int cx, int cy) {
        // 5,5
        // 6,5
		DIR[] dirs = DIR.values();
		Collections.shuffle(Arrays.asList(dirs));
		for (DIR dir : dirs) { // S //N //E
			int nx = cx + dir.dx;
			int ny = cy + dir.dy;
			if (between(nx, x) && between(ny, y) && (maze[nx][ny] == 0)
					) {
				maze[cx][cy] |= dir.bit; // 4 0110
				maze[nx][ny] |= dir.opposite.bit; // 8 1000
				generateMaze(nx, ny);
			}
		}
	}
 
	private static boolean between(int v, int upper) {
		return (v >= 0) && (v < upper);
	}
 
	private enum DIR {
		N(1, 0, -1), S(2, 0, 1), E(4, 1, 0), W(8, -1, 0);
		private final int bit;
		private final int dx;
		private final int dy;
		private DIR opposite;
 
		// use the static initializer to resolve forward references
		static {
			N.opposite = S;
			S.opposite = N;
			E.opposite = W;
			W.opposite = E;
		}
 
		private DIR(int bit, int dx, int dy) {
			this.bit = bit;
			this.dx = dx;
			this.dy = dy;
		}
	};
 
    private void copyMap() {
		for (int i = 0; i < y; i++) {
			// draw the north edge
			for (int j = 0; j < x; j++) {
				if( (maze[j][i] & 1) == 0  ) {
                    mapMatrix[2*i][2*j] = MapType.OBSTACLE;
                    mapMatrix[2*i][2*j+1] = MapType.OBSTACLE;
                } else{ 
                    mapMatrix[2*i][2*j] = MapType.OBSTACLE;
                    // mapMatrix[2*i][2*j+1] = MapType.OBSTACLE;
                }
			}
			// draw the west edge
			for (int j = 0; j < x; j++) {
                if( (maze[j][i] & 8) == 0) {
                    mapMatrix[2*i+1][2*j] = MapType.OBSTACLE;
                } else{ 
                    // mapMatrix[i+1][j+1] = MapType.OBSTACLE;
                }
			}
            // filling right col
            mapMatrix[2*i][2*(x-1)+2] = MapType.OBSTACLE;
            
            // filling left col 
            mapMatrix[2*i+1][2*(x-1)+2] = MapType.OBSTACLE;
            
            // filling the last row
            //fails if it is not a square
            mapMatrix[2*(x-1)+2][2*i] = MapType.OBSTACLE;
            mapMatrix[2*(x-1)+2][2*i+1] = MapType.OBSTACLE;
        }
            mapMatrix[2*(x-1)+2][2*(x-1)+2] = MapType.OBSTACLE;

	}
    
    public void display() {
        for(int i = 0; i<SIZE; i++){
            for(int j = 0; j<SIZE; j++){
                System.out.print(mapMatrix[i][j].toString()+" ");
            }
            System.out.println("");
        }
    }




}
