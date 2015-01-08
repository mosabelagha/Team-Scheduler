import java.util.ArrayList;

public class TimeSlot implements Comparable<TimeSlot>{
	String time;
	ArrayList<Player> players = new ArrayList<Player>();
	String level = "None";
	boolean filled = false;		//filled slot
	int rank;
	
	//sets the level restriction of the time slot
	public void setLevel(String level)
	{
		this.level = level;
	}
	
	//sets the time description of the TimeSlot
	public void setTimel(String time)
	{
		this.time = time;
	}
	
	// adds a player to the current time slot 
	// level of time slot must be set first, before this function can be called
	public void addPlayer(Player p)
	{
//		if(p.level != level)
//		{
//			if(level != "None")
//				throw new RuntimeException("Mismatched level with player!!\n" + "Player: " + p.name);
//			else
//				throw new RuntimeException("TimeSlot level not yet set...");
//		}
		if(!isFilled())
			players.add(p);
	}
	
	public void clearPlayers()
	{
		for(Player p : this.players)
		{
			p.setSorted(false);
		}
		this.players.removeAll(players);
	}
	
	public void clearLevel()
	{
		this.level = "None";
	}
	
	public void reset()
	{
		clearPlayers();
		clearLevel();
	}
	public boolean isFilled()
	{
		if(this.players.size() == 6)
			filled = true;
		
		else if(this.players.size() > 6 || this.players.size()<0)
		{
			System.out.println("Invalid");
			filled = true;
		}
		else
			filled = false;
		
		return filled;
	}
	
	public void setRank(int rank)
	{
		this.rank = rank;
	}

	@Override
	public int compareTo(TimeSlot otherTime) 
	{
		return rank - otherTime.rank;
	}

	@Override
	public String toString() {
		return time;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((level == null) ? 0 : level.hashCode());
		result = prime * result + rank;
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TimeSlot other = (TimeSlot) obj;
		if (level == null) {
			if (other.level != null)
				return false;
		} else if (!level.equals(other.level))
			return false;
		if (rank != other.rank)
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		return true;
	}
	
}
