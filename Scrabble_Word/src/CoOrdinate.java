

import java.io.Serializable;

public class CoOrdinate implements Serializable {

	public int startRow=-1;
	int startCol=-1;
	int endRow=-1;
	int endCol=-1;
	
	int natureOfWord;
	Boolean isNatureOfWordSet=false;
	//left to right 1 //r to l 2  //u to d 3  //d to u 4
	
	
	
	public void findNatureOfWord()
	{
		System.out.println("in findnature of word function sr: "+startRow+" sc "+startCol+" endr "+endRow+" endc "+endCol);
		if (startRow==endRow)
		{
			if (startCol<endCol)
			{
				natureOfWord=1;
				
			}
			else{
				natureOfWord=2;
			}
		}
		else{
			if(startRow<endRow)
			{
				natureOfWord=3;
			}
			else{
				natureOfWord=4;
			}
		}
		isNatureOfWordSet=true;
	}
	
	public String toString()
	{
		return "coOrdinate Object: startX is "+startRow+" startY is "+startCol+" endX is "+endRow+" endY is "+endCol;
		
	}
	
	
	
}
