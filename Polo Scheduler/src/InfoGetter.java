import com.opencsv.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

//import java.util.*;

public class InfoGetter {
	
	public static void operations(String source) throws IOException
	{
		CSVReader reader = new CSVReader(new FileReader(source), ',', '"', 0);
		String[] data = reader.readNext();
		
		int numTimes = Integer.parseInt(data[12]);
		int numPlayers = Integer.parseInt(data[13]);
		
		ArrayList<Player> players = new ArrayList<Player>();
		ArrayList<TimeSlot> times = new ArrayList<TimeSlot>();
		
		/**
		 * Time Stuff
		 * 
		 * Creates a new TimeSlot
		 * 	Populates TimeSlot with it's time
		 * 	isFilled is already set to false in the Class file
		 */
		
		//for loop to add data from CSV file into TimeSlot
		for(int k = 0; k < numTimes; k++)	
		{			
			if(data != null)
			{
				TimeSlot t = new TimeSlot();
				t.setTimel(data[k+2]);
				times.add(t);
		//		System.out.println(times.get(k).time);
			}
		}
		
		System.out.println("Time Info Done\n");
		
		/**
		 * Player stuff
		 * 
		 * Creates a new player
		 * 	Populates player with Name and Level
		 * Adds player to the ArrayList of Players
		 */
		//Go through all the players
		for(int i = 0; i<numPlayers; i++)
		{
			//create a new player
			Player p = new Player();
			
			//read in the player data from the CSV
			String[] myData = reader.readNext();
			//System.out.println(myData[0]);
			
			//for loop to add data from CSV file into Player object, then add that player to the array
			if(myData[0] != null)	
			{
				//populate Player preferences
				ArrayList<Integer> playertimes = new ArrayList<Integer>();
				for(int k = 0; k < numTimes; k++)	
				{
					playertimes.add(Integer.parseInt(myData[k+2]));
				}
				
				p.setName(myData[0]);		//Name
				p.setLevel(myData[1]);		//Level
				p.setTimes(playertimes);	//Time preferences
				players.add(p);				//Add player to list of players
			}
		}
		reader.close();
		System.out.println("Player Info Done\n");
		
		/**
		 * Sort the players, then group each set of them into the 3 levels
		 */
		sortPlayers(players, times, numTimes);
	}
	
	public static void sortPlayers(ArrayList<Player> players, ArrayList<TimeSlot> times, int numTimes) throws IOException
	{
		/**
		 * PART 1
		 * Start by getting appropriate data and splitting our data
		 */
		CSVReader reader = new CSVReader(new FileReader("Player Info.csv"), ',', '"', 0);
		String[] data = reader.readNext();
		
		int numBeginner = Integer.parseInt(data[14]);
		int numIntermediate = Integer.parseInt(data[15]);
		int numAdvanced = Integer.parseInt(data[16]);
		

		 //Sort players by their level - putting them into separate lists
		
		ArrayList<Player> beginner = filter(players, "Beginner");	
		ArrayList<Player> intermediate = filter(players, "Intermediate");
		ArrayList<Player> advanced = filter(players, "Advanced");
		
		reader.close();
		 
		//Check for errors 
		if(beginner.size() != numBeginner)
			throw new RuntimeException("Beginner Mismatch");
		if(intermediate.size() != numIntermediate)
			throw new RuntimeException("Int. Mismatch");
		if(advanced.size() != numAdvanced)
			throw new RuntimeException("Adv. Mismatch");
		
		System.out.println("Initial Sorting Done\n");
		
		/**
		 * PART 2
		 * Sort players from each separate list into appropriate TimeSlots
		 * 	See which TimeSlot works best for each group by number of people that are available
		 * 	Find which people prefer that time over anything else
		 * 	
		 */
	
		/**
		 * @TODO
		 * run different iterations. whichever one gives the smallest number of conflicts will be
		 * the order the algorithm is run in
		 */
		
		ArrayList<Integer> sortings = new ArrayList<Integer>();

		//0
		sortings.add(sortTimes(beginner, times) + sortTimes(intermediate, times) + sortTimes(advanced, times));
		writeToFile(times, players, "Option 1.csv");
		for(TimeSlot t : times)
			t.reset();
		//1
		sortings.add(sortTimes(beginner, times) + sortTimes(advanced, times) + sortTimes(intermediate, times));
		writeToFile(times, players, "Option 2.csv");
		for(TimeSlot t : times)
			t.reset();
		//2
		sortings.add(sortTimes(intermediate, times) + sortTimes(beginner, times) + sortTimes(advanced, times));
		writeToFile(times, players, "Option 3.csv");
		for(TimeSlot t : times)
			t.reset();
		//3
		sortings.add(sortTimes(intermediate, times) + sortTimes(advanced, times) + sortTimes(beginner, times));
		writeToFile(times, players, "Option 4.csv");
		for(TimeSlot t : times)
			t.reset();
		//4
		sortings.add(sortTimes(advanced, times) + sortTimes(beginner, times) + sortTimes(intermediate, times));
		writeToFile(times, players, "Option 5.csv");
		for(TimeSlot t : times)
			t.reset();
		//5
		sortings.add(sortTimes(advanced, times) + sortTimes(intermediate, times) + sortTimes(beginner, times));
		writeToFile(times, players, "Option 6.csv");
		for(TimeSlot t : times)
			t.reset();
		
		int minval = Collections.min(sortings);
		int minidx = sortings.indexOf(minval);
		
		switch(minidx)
		{
		case 0:
			sortings.add(sortTimes(beginner, times) + sortTimes(intermediate, times) + sortTimes(advanced, times));
			break;
		case 1:
			sortings.add(sortTimes(beginner, times) + sortTimes(advanced, times) + sortTimes(intermediate, times));
			break;
		case 2:
			sortings.add(sortTimes(intermediate, times) + sortTimes(beginner, times) + sortTimes(advanced, times));
			break;
		case 3:
			sortings.add(sortTimes(intermediate, times) + sortTimes(advanced, times) + sortTimes(beginner, times));
			break;	
		case 4:
			sortings.add(sortTimes(advanced, times) + sortTimes(beginner, times) + sortTimes(intermediate, times));
			break;
		case 5:
			sortings.add(sortTimes(advanced, times) + sortTimes(intermediate, times) + sortTimes(beginner, times));
			break;
		}
		
		writeToFile(times, players, "Recommended.csv");
	}
	
	public static ArrayList<Player> filter(ArrayList<Player> players, String keyword)
	{
		ArrayList<Player> newList = new ArrayList<Player>();
		
		for(Player p : players)
		{
			if(p.level.equalsIgnoreCase(keyword))
			{
				newList.add(p);
			}
		}
		return newList;
	}
	
	public static int sortTimes(ArrayList<Player> players, ArrayList<TimeSlot> times)
	{
		/**
		 * Sort the times by the number of people that are available 
		 */
		if(players == null || times == null)
			return 0;
		
		System.out.println("Now sorting level: " + players.get(0).level);
		
		ArrayList<TimeSlot> sortedTimes = new ArrayList<TimeSlot>();
		int i=0;
		for(TimeSlot t : times)
		{
			if(t.level.equalsIgnoreCase(players.get(0).level) || t.level.equalsIgnoreCase("None"))
			{
				int counter = 0;	//number of people available at the current timeslot
				for(Player p : players)
				{
					if(p.times.get(i) > 0)
						counter++;
				}
				t.setRank(counter);
//				System.out.println( t.time + ": " + t.rank);
				sortedTimes.add(t);
				
				i++;
			}
		}
//		System.out.println("Unsorted: " + sortedTimes.toString());
		//sort the list in descending order
		Collections.sort(sortedTimes);
		Collections.reverse(sortedTimes);
//		System.out.println("Sorted: " + sortedTimes.toString());
		
		/**
		 * Assign Players their time slots
		 * 	Put potential candidates 
		 */
		for(TimeSlot t : sortedTimes)
		{
			int idx = times.indexOf(t);	//the index of the time slot in the original configuration of times (for player preference lookup)
			for (Player p : players)
			{
				if(p.times.get(idx) == 3 && !p.isSorted() && !t.isFilled() && (t.level.equalsIgnoreCase(p.level) || t.level.equalsIgnoreCase("None")))
				{
					p.setSorted(true);
					t.addPlayer(p);
				}
				
				else if(p.times.get(idx) == 2 && !p.isSorted() && !t.isFilled() && (t.level.equalsIgnoreCase(p.level) || t.level.equalsIgnoreCase("None")))
				{
					p.setSorted(true);
					t.addPlayer(p);
				}
				
				else if(p.times.get(idx) == 1 && !p.isSorted() && !t.isFilled() && (t.level.equalsIgnoreCase(p.level) || t.level.equalsIgnoreCase("None")))
				{
					p.setSorted(true);
					t.addPlayer(p);
				}
			}

			//Clear the time slot if the number of people in it is too little
			if(t.players.size() < 2)
			{
				t.clearPlayers();
				t.clearLevel();
			}
			
			else
			{
				t.setLevel(players.get(0).level);
			}
			
			if(t.isFilled() || !t.level.equalsIgnoreCase("None"))
				System.out.println(t.time + ": " + t.players);
		}
		int unsorted =0;
		for(Player p : players)
			if(!p.isSorted())
			{
				unsorted++;
				System.out.println("No time slot: " + p.name);
			}
		
		System.out.println();
		
		return unsorted;
	}

	public static void writeToFile(ArrayList<TimeSlot> times, ArrayList<Player> players, String filename) throws IOException
	{
		String output = filename;
		
		CSVWriter writer = new CSVWriter(new FileWriter("output/" + output));	
		
		String[] header = {"Time", "Level"};
		writer.writeNext(header);
		
		for(TimeSlot t : times)
		{
			String[] playerList = new String[t.players.size() + 2];
			playerList[0] = t.time;
			playerList[1] = t.level;
			int i = 2;
			for(Player p : t.players)
			{
				playerList[i] = p.name;	
				i++;
			}
			writer.writeNext(playerList);
		}
		String[] blank = {""};
		writer.writeNext(blank);
		
		ArrayList<String> unsortedList = new ArrayList<String>();
		unsortedList.add("Unsorted");
		
		for(Player p : players)
		{
			if(!p.isSorted())
				unsortedList.add(p.name + " (" + p.level + ")");
		}
		
		if(unsortedList.size() == 1)
			unsortedList.add("None");
		
		String[] unsorted = unsortedList.toArray(new String[unsortedList.size()]);
		writer.writeNext(unsorted);
		
		writer.close();
	}
	
	
}