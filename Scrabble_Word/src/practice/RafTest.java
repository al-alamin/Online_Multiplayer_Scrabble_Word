package practice;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import javafx.scene.layout.Pane;


public class RafTest {
	
	ArrayList<PlayerInformation> highScores=new ArrayList<>();
	
	void loadTopResults()
	{
		try {
			Scanner sc=new Scanner(new File("abc.txt"));
			
			while (sc.hasNext())
			{
				String s=sc.next();
				int n=sc.nextInt();
				
				System.out.println(s+"  "+n);
			//	PlayerInformation pi=new PlayerInformation(s, n);
			//	highScores.add(pi);
			}
			
			Collections.sort(highScores);
			
			while (highScores.size()>10)
			{
				highScores.remove(10);
			}
			
			System.out.println(highScores);
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		System.out.println("raf test");
		
		RafTest ob=new RafTest();
		
		ob.loadTopResults();
		try {
			BufferedWriter bw=new BufferedWriter(new FileWriter(new File("abc.txt"),true));
			
			
			bw.write("alamin");
		    bw.newLine();
		    bw.write(40+"");
		    bw.newLine();
			
			bw.flush();
			bw.close();
		
		
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	
		
		
	}

	
}
