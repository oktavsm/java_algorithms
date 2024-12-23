
import java.util.*;
public class bruteForceSudoku {
    static int grid[][];
    static int gridSize;
    static int coordinates[][];
    static int blank = 0;
    public static final String RESET = "\u001B[0m";    // Mengembalikan warna ke default
    public static final String GREEN = "\u001B[32m";   // Hijau
    public static final String RED = "\u001B[31m";     // Merah
    static int solving =0;
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        gridSize= in.nextInt();
        // input harus kuadrat sempurna
        if (Math.sqrt(gridSize) % 1 != 0) {
            System.err.println("Grid size must be a perfect square!");
            return;
        }
        
        grid = new int[gridSize][gridSize];
        for(int i=0;i<gridSize;i++){
            for(int j=0;j<gridSize;j++){
                grid[i][j]=in.nextInt();
            }
        }
        for(int i=0;i<gridSize;i++){
            for(int j=0;j<gridSize;j++){
                if(grid[i][j]==0){
                    blank++;
                }
            }
        }
        coordinates = new int[blank][2];
        int sumOfCoor=0;
        //catat koordinat field yang
        for(int i=0;i<gridSize;i++){
            for(int j=0;j<gridSize;j++){
                if(grid[i][j]==0){
                    coordinates[sumOfCoor][0]=i;
                    coordinates[sumOfCoor][1]=j;
                    sumOfCoor++;
                }
            }
        }
        if (!solve(0)) {
            System.out.println(RED + "No solution found for the given Sudoku grid!" + RESET);
        } else{
            solve(0);
            printGrid();
            System.out.println(GREEN+String.format("Successfully solved with %d attempts",solving)+RESET);
        }        
    }
    
    static boolean solve(int pos) {
        //base case ketika sudah mencapai posisi blank terakhir (tidak ada field yang harus diperiksa lagi)
        if (pos == blank) {
            return true;
        }

        // rekursi untuk mencoba semua kemungkinan angka(1-9)
        for (int i = 1; i <= gridSize; i++) {
            grid[coordinates[pos][0]][coordinates[pos][1]] = i;
            solving++;
            // printGrid();
            // try {
            //     Thread.sleep(1); // Jeda selama 1 detik
            // } catch (InterruptedException e) {
            //     System.err.println("Thread terganggu: " + e.getMessage());
            // }
            // System.out.println(GREEN+"Solving..."+RESET);
            //cek untuk memastikan field sudah terisi dengan baik sebelum cek field selanjutnya
            if (horizontalCheck(coordinates[pos][0], coordinates[pos][1]) &&
                verticalCheck(coordinates[pos][0], coordinates[pos][1]) &&
                squareCheck(coordinates[pos][0], coordinates[pos][1])) {
                //proses pemanggilan fungsi rekursi
                if (solve(pos + 1)) {
                    return true; // Berhenti jika solusi ditemukan
                }
            }
            grid[coordinates[pos][0]][coordinates[pos][1]] = 0; // Backtrack
        }
        //setelah iterasi ternyata tidak menghasilkan nilai terbaik, maka kembalikan false ke pemanggilan sebelumnya dan field yang sebelumnya terisi di backtranking
        return false;
    }

    static boolean horizontalCheck(int row,int column){
        for(int i=0;i<gridSize;i++){
            if(grid[row][i]==grid[row][column]&&i!=column){
                return false;
            }
        }
        return true;
    }
    
    static boolean verticalCheck(int row, int column){
        for(int i=0;i<gridSize;i++){
            if(grid[i][column]==grid[row][column]&&i!=row){
                return false;
            }
        }
        return true;
    }
    static boolean squareCheck(int row,int column){
        int square = (int)Math.sqrt(gridSize);
        for(int i=square*(row/square);i<square*(row/square+1);i++){
            for(int j=square*(column/square);j<square*(column/square+1);j++){
                if(grid[i][j]==grid[row][column]&&i!=row&&j!=column){
                    return false;
                }
            }
        }
        return true;
    }

    static void printGrid(){
        clearScreen();
        int pos = 0;
        for(int i = 0;i<gridSize;i++){
            for(int j = 0;j<gridSize;j++){
                if(pos<coordinates.length){
                    if(i==coordinates[pos][0]&&j==coordinates[pos][1]&&grid[i][j]!=0){
                        if(grid[i][j]>9){
                            System.out.printf("%s%4c%s",GREEN,grid[i][j]+55,RESET);
                        } else{
                            System.out.printf("%s%4d%s",GREEN,grid[i][j],RESET);
                        }
                        pos++;
                    }
                    else if (grid[i][j]==0){
                        System.out.printf("%s%4d%s",RED,grid[i][j],RESET);
                    }
                    else{
                        if(grid[i][j]>9){
                            System.out.printf("%4c",grid[i][j]+55);
                        } else{
                            System.out.printf("%4d",grid[i][j]);
                        }
                    }
                }else{
                    if(grid[i][j]>9){
                        System.out.printf("%4c",grid[i][j]+55);
                    } else{
                        System.out.printf("%4d",grid[i][j]);
                    }
                }  
                
                
            }
            System.out.println();
        }
    }
    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033\143");
            }
        } catch (Exception e) {
            System.err.println("Tidak bisa clearscreen");
        }
    }

}
