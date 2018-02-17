package minesweeper.nkmine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class App 
{
    public static void main( String[] args ) throws IOException
    {
        MineSweeper ms = new MineSweeper(10, 20);
        //ms.printBoard();
        ms.printBoardState();
        while(! ms.isGameEnded()) {
        	 	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
             System.out.print("Enter row number:\n");
             String sRow = br.readLine();
             System.out.print("Enter col number:\n");
             String sCol = br.readLine();
             try{
                 int iRow = Integer.parseInt(sRow);
                 int iCol = Integer.parseInt(sCol);
                 ms.open(iRow, iCol);
             }catch(NumberFormatException nfe){
                 System.err.println("Invalid Format!");
             }
             ms.printBoardState();
        }
        ms.printScore();
        ms.printBoard();
       
        
    }
}
