import java.io.Serializable;



public class PlayerInformation implements Comparable<PlayerInformation>, Serializable{
	
	public String playerName="No Name";
	public int score=0;
	
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
	@Override
	public String toString() {
		
		return "n: "+playerName+" s: "+score;
	}
	
	

}
