package practice;

public class PlayerInformation implements Comparable<PlayerInformation> {
	
	public String playerName="No Name";
	public int score=0;
	static int n=0;
	public PlayerInformation(String s,int n)
	{
		playerName=s;
		score=n;
	}

	@Override
	public int compareTo(PlayerInformation o) {
		
		
		
		return -1*(score-o.score);
		
	//	return playerName.compareTo(o.playerName);
		
		
	}
	
	

}
