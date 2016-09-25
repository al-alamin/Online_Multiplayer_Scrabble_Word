package Database;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CreatingTextFileFromMyPc {

	public static void main(String[] args) throws IOException {
		File from=new File("J:\\old files\\Bangla Dictionary\\Text");
		if (!from.exists())
		{
			System.out.println("this directory does not exit in this pc try later");
			System.exit(1);
		}
		File files[]=from.listFiles();
		
		int i=0;
		ArrayList<String> words=new ArrayList<>();
		for (File f:files)
		{
			words.add(f.getName());
//			i++;
//			if (i>10)
//			{
//				break;
//			}
			
			
		}
		
		System.out.println(words.toString());
		
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(new File("wordlist.txt")));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
				
		for (i=0;i<words.size();i++)
		{
			try {
				bw.write(words.get(i)+"  ");
				
				if (i%7==0)
				{
					bw.newLine();
				
				}
				if (i%100==0)
				{
					bw.newLine();
					bw.newLine();
					
				}
				} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		System.out.println("written to the file");
		bw.flush();
		bw.close();
			
		
		
		System.out.println(words.size());
		System.out.println(files.length);
		
		System.out.println("a text file for words has been created");

	}

}
