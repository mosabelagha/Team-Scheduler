import java.util.ArrayList;


public class Player {
	String name;
	String level;
	ArrayList<Integer> times = new ArrayList<Integer>();
	boolean sorted = false;
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setLevel(String level)
	{
		this.level = level;
	}
	
	public void setTimes(ArrayList<Integer> times)
	{
		this.times = times;
	}
	
	public void setSorted(boolean val)
	{
		sorted = val;
	}
	
	public boolean isSorted()
	{
		return this.sorted;
	}

	@Override
	public String toString() {
		return name;
	}
}
