package day20;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

public class Sudoku{
	static	String inputFileName = null;
	static	String outputFileName = null;
	static int phraseWordNum , sortedPrintNum;
	static int sudoku[][];
	static int emptyNum ;
	
public static  void main(String args[]) throws IOException {
	loadArgs(args);
	int i = 0;
	int emptyRow = 0, emptyCol = 0;
    sudoku = new int[phraseWordNum][phraseWordNum];
	boolean first ;
	String s = null;
	@SuppressWarnings("unused")
	int tempArray[] = new int[2];
	try{
		BufferedReader br = new BufferedReader(new FileReader(inputFileName));
		PrintStream ps  = new PrintStream(new FileOutputStream(outputFileName,true));
		System.setOut(ps);
		for(int rc=0 ; rc<sortedPrintNum ; rc++){
			i=0;  first = true;
			emptyRow = 0; emptyCol = 0;
			emptyNum = 0;
			//读入文件写入数组循环
			for(int k=0 ; k < phraseWordNum ; k++){
				if((s=br.readLine())!=null);{
					String temp[] = s.split(" ");
					for(int j=0;j<phraseWordNum;j++){
						sudoku[i][j]=Integer.parseInt(temp[j]);
						if(sudoku[i][j]==0){//第一个要填的位置
							if(first){
								emptyRow = i;
								emptyCol = j;
								first = false;
							}
						emptyNum++;//记录要填空缺
						}
					}
					i++;
				}
				sudokuSolve(emptyRow,emptyCol);
				if(rc<sortedPrintNum-1) {
					System.out.println();
				}
			}
			br.readLine();
		}
		br.close();
		ps.close();
	}catch (FileNotFoundException e1) {
		System.out.println("未找到指定文件");
		e1.printStackTrace();
		System.exit(-1);
	}catch (IOException e2) {
		e2.printStackTrace();
		System.exit(-1);
	}
}

public static void loadArgs(String args[]){
	if(args.length > 0 && args != null){
		for(int i = 0 ; i < args.length  ; i++){
			if((args[i].compareTo("-i"))==0) {
				inputFileName = args[++i];
			}else if((args[i].compareTo("-o"))==0) {
				outputFileName = args[++i];
			}else if ((args[i].compareTo("-m"))==0) {
				phraseWordNum = Integer.valueOf(args[++i]);
			}else if((args[i].compareTo("-n"))==0) {
				sortedPrintNum = Integer.valueOf(args[++i]);
			}else {
				System.out.println("输入格式错误");
			}
		}
	}else{
		System.exit(1);
		}
	}

static void sudokuSolve(int row , int col){
	if(row>phraseWordNum||col>phraseWordNum||row<0||col<0){
		System.out.println("待填空缺获取坐标越界");
		System.exit(-1);
		}
	for(int i = 1 ; i <= phraseWordNum ;i++){
		if(differentInRow(row,i)&&differentInCol(col,i)&&differentInPalace(row,col,i)){
			sudoku[row][col] = i ;
			emptyNum--;
			if(emptyNum==0)
			{
				writeOutArray();
				break;
				}
			int tempArray[] ;
			tempArray = nextEmpty(row,col);
			sudokuSolve(tempArray[0] ,tempArray[1]);
			sudoku[row][col] = 0;
			emptyNum++;
			}		
		}
	}
//行判断
static boolean differentInRow(int row, int tempNum){
	for(int i = 0 ; i < phraseWordNum;i++){
		if(sudoku[row][i]==tempNum) {
			return false;
		}
	}
	return true;
}
//列判断
static boolean differentInCol(int col, int tempNum){
	for(int i = 0 ; i < phraseWordNum  ; i++){
		if(sudoku[i][col]==tempNum) {
			return false;
		}
	}
	return true;
}
//同阶判断
static boolean differentInPalace(int row, int col,int tempNum){
	int rowNum,colNum;
	if(phraseWordNum==3||phraseWordNum==5||phraseWordNum==7) {
		return true;
	}else if(phraseWordNum==4||phraseWordNum==6){
		rowNum = 2 ; 
		colNum = phraseWordNum/2;
	}else if(phraseWordNum==8){
		rowNum = 4 ; 
		colNum = 2;
	}else{
		rowNum = colNum = 3;
	}
	int rowStart=row/rowNum*rowNum , colStart = col/colNum*colNum;
	for(int i = rowStart; i <rowStart + rowNum ; i++){
		for(int j = colStart ; j < colStart + colNum ; j++){
			if(sudoku[i][j] == tempNum) {
				return false;
			}
		}
	}
	return true;
}

static int [] nextEmpty(int row,int col){//寻找未填空缺
	int a[] = new int[2];
	@SuppressWarnings("unused")
	boolean running = true;
	for(int i = row ; i < phraseWordNum ; i++){
		for(int j = 0;j < phraseWordNum ; j++){
			if(sudoku[i][j]==0){
				a[0] = i;
				a[1] = j;
				return a;
			}
		}
	}
	return a;
}

static void writeOutArray(){
	for(int i = 0 ; i < phraseWordNum ; i++){
		for(int j = 0;j < phraseWordNum ; j++){
			System.out.print(sudoku[i][j]+ " ");
		}
		System.out.println();
	}
}	
}