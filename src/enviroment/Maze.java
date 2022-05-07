package src.enviroment;
import java.util.*;
public class Maze {

    private MapType mapMatrix[][]; //encapsula este porfa un getter
    // genero getters a setters? solo getMapMatrix
    private Random ran = new Random();
    private final int lenght;
	private final int width;
    // private Vector<Point> cells;
    
    //try to emulate the behaviour of a set with a LinkedList
    private  Set<Point> cells = new HashSet<>();
    // una celula sobrevive si tiene entre 1-5 vecinos
    // una célula aparece en una celda si esta celda tiene exactamente 3 vecinos 

    public Maze(int len, int wid) {
        lenght = len;
        width = wid;
        this.mapMatrix = MapType.newMapLW(len, wid);
        generateMaze();
    
    }

    public MapType[][] getMapMatrix() {
        return this.mapMatrix;
        // por que es void?
        // pq el autocompletado es mas inutil que acosta en algoritimia
        //JA
        //voy a probarlo, con mucha fe
    }
    

    class Point {
        public int i, j;
        public boolean nexState;
        Point(int i, int j){
            this.i = i;
            this.j = j;
        }

        @Override public boolean equals(Object obj) {
            if(obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) { return false; }

            Point p = (Point) obj;
            return p.i == this.i && p.j == this.j;
        }

        @Override public int hashCode() {
            return Objects.hash(this.i, this.j);
        }
    }
    

    private void generateMaze(){
        startRandom();
        for(int i = 0; i < 200; i++){
            // displayTest();
            tick();
        }
    }



    private void  startRandom(){

        //count will be 50 just temporarily
        int count = 20;

        while (count > 0) {
            int istart = (this.width - 10) / 2, jstart = (this.lenght - 10) / 2;
            loop:
            for(int i = 0; i < 10; i++){
                for(int j = 0; j < 10; j++) {
                    // generate random obstacles in the map
                    if(ran.nextInt(2) == 1 && mapMatrix[i + istart][j + jstart]!=MapType.OBSTACLE  ) {
                        mapMatrix[i + istart][j + jstart] = MapType.OBSTACLE;
                        cells.add( new Point(i+istart, j+jstart));
                        count--;
                    }
                    if (count == 0) {
                        break loop;
                    }

                }

            }
        }
        
    }

    private void removeEmpties(){

        //tas eliminando mientras iteras
        // me bota error
        System.out.println("Antes de: "+cells.size() );
        Collection<Point> conjuntoAuxiliar = new LinkedList<>(); //que

        for (Point cell: cells) {
            if (this.mapMatrix[cell.i][cell.j] == MapType.EMPTY) {
                conjuntoAuxiliar.add(cell);
                // y at a ya
            }
        }
        cells.removeAll(conjuntoAuxiliar);
        System.out.println("Despues de: "+cells.size() );

    }

    private void addNeighbours(){
        
        Collection<Point> conjuntoAuxiliar = new LinkedList<>(); //que
        for(Point cell : cells){
            for (Point otherCell: getNeighbours(cell)){
                conjuntoAuxiliar.add(otherCell);
            }
        }
        cells.addAll(conjuntoAuxiliar);
    }

    private void tick(){
        //run rules
        System.out.println("Un tick");
        addNeighbours();
        for(Point cell : cells){
            if(pointBool(cell)){
                // check if point survives
                int count = 0;
                int times = 0;
                for(Point neighbour : getNeighbours(cell)) {
                    if(pointBool(neighbour)) count++;
                    times++;
                }
                System.out.print(count+", "+times+"- ");
                if( 1<= count && count <= 5){
                    cell.nexState = true;
                } else cell.nexState = false;
                
                
            } else {
                //check if cell must exist
                int count = 0;
                for(Point neighbour : getNeighbours(cell)) if(pointBool(neighbour)) count++;
                if(count == 3){
                    cell.nexState = true;
                } else cell.nexState = false;

                
            }
            
        }

        //update state
        System.out.println(cells.size());
        for(Point cell : cells) mapMatrix[cell.i][cell.j] = cell.nexState == true ? MapType.OBSTACLE : MapType.EMPTY;
        removeEmpties();

    }

    private boolean pointBool(Point point){
        return mapMatrix[point.i][point.j] == MapType.OBSTACLE ? true: false;
    }
    private boolean offLimits(Point point){
        return offLimits(point.i, point.j);
    }
    private boolean offLimits(int i, int j) {
        if (i < 0 || i >= this.lenght || j < 0 || j >= this.width) {
            return true;
        }
        return false;
    }

    private Collection<Point> getNeighbours(Point point){
        Collection<Point> neighbours;
        neighbours = new LinkedList<Point>();
        for (int index = -1; index < 2; index ++) {
            for (int j = -1; j < 2; j++) {
                if (!offLimits(point.i + index, point.j + j) && (index != 0 || j != 0)) {

                    neighbours.add(new Point(point.i + index, point.j + j));
                }
            }
        }

        return neighbours;
    }

    public void displayTest() {
        for(int i = 0; i<lenght; i++){
            for(int j = 0; j<width; j++){
                System.out.print(mapMatrix[i][j].toString()+" ");
            }
            System.out.println("");
        }
    }
}


