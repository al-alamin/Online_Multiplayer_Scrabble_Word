import java.io.Serializable;
import java.util.ArrayList;

import Database.DataBase;


public class ScarableObject implements Serializable{
	public CoOrdinate coOrdinate=new CoOrdinate();
	public String word=new String("not set");
	public int playerNumber=-1;
	public int wordScore=0;
	public int resultOfChallenge=0;
//	public ArrayList<Integer> playerScoreList=new ArrayList<>();
	public ArrayList<PlayerInformation> playerInfoList=new ArrayList<PlayerInformation>();
	
	
	
	public void setPlayerInfoList(ArrayList<PlayerInformation> a,ArrayList<PlayerInformation> b)
	{
		playerInfoList.clear();
		playerInfoList.addAll(b);
		playerInfoList.addAll(a);
	}
//	
//	public void addPlayerInfoList(ArrayList<PlayerInformation> a)
//	{
//		
//		playerInfoList.addAll(a);
//	}
//	
	
//	public void setScoreList(ArrayList<Integer> a)
//	{
//		playerScoreList.clear();
//		playerScoreList.addAll(a);
//	}
//	
	
	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
	}
	
	public int getPlayerNumber() {
		return playerNumber;
	}
	
	
	
	public String toString()
	{
		return "scrableWord object: word is "+word+" coOrd is "+coOrdinate;
	}
	
	public void setWord(String word) {
		this.word = word;
	}
	
	public void setCoOrdinate(CoOrdinate coOrdinate) {
		this.coOrdinate = coOrdinate;
	}
	
	public String getWord() {
		return word;
	}
	
	public CoOrdinate getCoOrdinate() {
		return coOrdinate;
	}
	
	
}
