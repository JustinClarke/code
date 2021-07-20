import java.io.*;
import java.util.*;
public class Allocate
{	
	public static void main(String[] args)
	{		
		Vector exclude = new Vector();

		for (int i = 0; i < args.length; i++)
			exclude.add(args[i]);

		System.out.println("Timetabler 5000...");
		System.out.println();
		System.out.print("Days you don't want to attend uni (from command line arguments): ");

		for (int i = 0; i < exclude.size(); i++)
		{
			System.out.print(exclude.get(i) + " ");
		}

		if (exclude.size() == 0)
			System.out.print("None");

		System.out.println();
		System.out.println();

		UniClass[] classes = new UniClass[Keyboard.readInt("How many classes are you allocating for:")];

		for (int i = 0; i < classes.length; i++)
		{
			String clsName = Keyboard.readString("What is the name of the class? For example, HET108 LA2:");
			int clsCount = Keyboard.readInt("How many available time slots are there for this class:");
			int clsLength = Keyboard.readInt("What is the duration of the class:");

			classes[i] = new UniClass(clsName, clsCount, clsLength);

			System.out.println();
			System.out.println("Next class...");
		}

		System.out.println();
		System.out.print("Now that you have entered the subjects, you will be required to enter the available time slots for each subject. You will be asked for a day and a time and duration, For the day simply type the day in full, the time must be entered in 24 hour format 1630 or 0800, and the duration should be entered in hours, ie 1");
		System.out.println();

		for (int i = 0; i < classes.length; i++)
		{
			String day;
			String time;

			System.out.println("For " + classes[i].getName());

			for (int j = 0; j < classes[i].slotsCount(); j++)
			{
				System.out.println();
				System.out.println("Class: " + (j+1));
				day = Keyboard.readString("Day:");
				time = Keyboard.readString("Time:");
				classes[i].addTime(day, time);
			}

			System.out.println();
		}
		int trials = Keyboard.readInt("Allocate will attempt to randomly generate non-clashing timetables. How many would you like to generate:");
		System.out.println();

		try{
			makeTable(classes, exclude, "08:30", "23:30", trials, "timetables_manual.txt");
		}
		catch (IOException exp)
		{
			System.out.println(exp);
		}
	}

	public static boolean makeTable(UniClass[] classes, Vector exclude, String min_start, String max_finish, int n, String theFile) throws IOException
	{
		TimeTable[] table = new TimeTable[n];
		int k = 0;
		int slot_tries;
		int timetable_tries = 0;
		int tmp;
		int min;
		int max;
		boolean allocated = false;
		boolean abort = false;
		int slot;

		tmp = Integer.valueOf(min_start.replaceAll(":", "")); //remove ":" from string
		min = (tmp - 830) / 100;

		tmp = Integer.valueOf(max_finish.replaceAll(":", "")); //remove ":" from string
		max = (tmp - 830) / 100;

		do 
		{
			table[k] = new TimeTable();

			for (int i = 0; i < classes.length; i++)
			{
				slot_tries = 0;
				do
				{
					slot = (int)Math.floor(Math.random() * classes[i].slotsCount());
					allocated = table[k].addClass(classes[i], slot, exclude, min, max);
					slot_tries++;
				}
				while ((slot_tries < Math.pow(classes[i].slotsCount(), 3)) && !allocated);

				if (allocated == false)
				{
					timetable_tries++;
					if (timetable_tries > 1000)
						abort = true;

					break;
				}	
			}
			if (allocated == true)
			{
				timetable_tries = 0; //timetable generated, move onto next timetable
				k++;
			}		
		}
		while (k < n && !abort);

		if (abort)
		{
			return false; // timetable could not be generated
		}			

		String title;
		String startTime = "";
		String endTime = "";
		PrintWriter out = new PrintWriter(new FileWriter(theFile));

		for (int i = 0; i < table.length; i++)
		{
			out.println("             _______________________________________________________________________________");
			out.println("            |    Monday     |    Tuesday    |   Wednesday   |   Thursday    |    Friday     |");
			out.println("------------|---------------|---------------|---------------|---------------|---------------|");
			for (int y = table[i].getEarliestStartSlot(); y <= table[i].getLatestFinishSlot(); y++)
			{
				for (int x = 0; x < 5; x++)
				{
					title = table[i].getClassTitle(x, y);

					if (x == 0)
					{
						startTime = String.valueOf(y * 100 + 830);
						endTime = String.valueOf(y * 100 + 930);

						if (startTime.length() < 4)
							startTime = "0" + startTime;
						if (endTime.length() < 4)
							endTime = "0" + endTime;	

						out.print(specialPad(startTime + " - " + endTime, 13));
					}

					if (title == null)
						out.print(specialPad("", 16));
					else
					{
						if (title.equalsIgnoreCase("$CONT$"))
							out.print(specialPad("", 16));
						else
							out.print(specialPad(table[i].getClassTitle(x, y), 16));
					}
				}

				out.println();
				out.print("------------");

				if (y < table[i].getLatestFinishSlot())				
				{	
					for (int p = 0; p < 5; p++)
					{
						if ((table[i].getClassTitle(p, y + 1)) != null)
						{
							if ((table[i].getClassTitle(p, y + 1)).equalsIgnoreCase("$CONT$"))
								out.print("|               ");
							else
								out.print("|---------------");						
						}
						else
							out.print("|---------------");
					}
					out.println("|");
				}
				else
					out.println("|---------------|---------------|---------------|---------------|---------------|");
			}

			out.println();
			out.println(getStats(table[i]));
			out.println("*********************************************************************************************");;
			out.println();
			out.println();
		}
		out.close();
		return true;
	}

	public static String specialPad(String text, int padding)
	{
		String result="";
		int c = padding - text.length() - 1;
		for (int i = 0; i < c; i++)
		{
			result += " ";
		}

		if (c < 0)
			text = text.substring(0, padding - 1);

		return text + result + "|";
	}

	public static String getStats(TimeTable tbl)
	{	
		final String[] DAYS = {"Mon", "Tue", "Wed", "Thu", "Fri"};
		String report = "";
		int diff;
		int min;
		int max;
		int sum = 0;;

		for (int x = 0; x < 5; x++)		
		{
			min = TimeTable.end_slot;
			max = -1;
			diff = 0;

			for (int y = 0; y < TimeTable.end_slot; y++)	
			{
				if (tbl.getClassTitle(x, y) != null)
				{
					if (min > y)
						min = y;
					if (max < y)
						max = y;
				}
			}
			diff = max - min + 1;	

			if (min == TimeTable.end_slot && max == -1)
				report += DAYS[x] + " - " + 0 + " hrs. ";
			else
			{
				report += DAYS[x] + " - " + diff + " hrs. ";
				sum += diff;
			}
		}

		report += " TOTAL HRS: " + sum;
		return report;
	}
}

class TimeTable
{
	public final static int end_slot = 15;
	private String[][] table = new String[5][end_slot];

	public TimeTable(){}

	public boolean addClass(UniClass cls, int index, Vector excl, int min_slot, int max_slot)
	{
		String name = cls.getName();
		String dayName =  (cls.getTimeSlot(index).getDayName()).substring(0, 3);
		int day = cls.getTimeSlot(index).getDay();
		int slot = cls.getTimeSlot(index).getSlot();
		int dur = cls.getDuration();

		for (int a = 0; a < excl.size(); a++)
		{
			if (((String)excl.get(a)).equalsIgnoreCase(dayName))
			{
				return false;
			}
		}

		if ((slot < min_slot) || ((slot + dur) > max_slot))
			return false;

		for (int i = 0; i < dur; i++)
		{
			if (!(table[day][slot + i] == null))
				return false;
		}

		// No collisions to add in the new class
		for (int i = 0; i < dur; i++)
		{
			if (i == 0)
				table[day][slot] = name;
			else
				table[day][slot + i] = "$CONT$";
		}

		return true;
	}

	public int getEarliestStartSlot()
	{
		int ret = end_slot;
		for (int x = 0; x < 5; x++)
		{
			for (int y = 0; y < end_slot; y++)
			{
				if (!(table[x][y] == null))
					if (ret > y)
						ret = y;
			}
		}
		return ret;
	}

	public int getLatestFinishSlot()
	{
		int ret = 0;
		for (int x = 0; x < 5; x++)
		{
			for (int y = 0; y < end_slot; y++)
			{
				if (!(table[x][y] == null))
				{
					if (ret < y)
						ret = y;
				}
			}
		}
		return ret;
	}

	public String getClassTitle(int x, int y)
	{
		return table[x][y];
	}
}

class UniClass
{
	private	String name;
	private int duration;
	private TimeSlot[] times;

	public UniClass(String n, int c, int dur)
	{
		name = n;
		times = new TimeSlot[c];
		duration = dur;
	}

	public String getName()
	{
		return name;
	}

	public int getDuration()
	{
		return duration;
	}

	public TimeSlot[] getTimes()
	{
		return times;
	}

	public TimeSlot getTimeSlot(int index)
	{
		return times[index];
	}

	public boolean addTime(String day, int slot)
	{
		boolean done = false;
		for (int i = 0; i < times.length; i++)
		{
			if (times[i] == null)
			{
				times[i] = new TimeSlot(day, slot);
				done = true;
				break;
			}
		}

		return done;
	}

	public boolean addTime(String day, String time)
	{
		int slot;
		int tmp;
		boolean done = false;

		tmp = Integer.valueOf(time.replaceAll(":", "")); //remove ":" from string
		slot = (tmp - 830) / 100;

		for (int i = 0; i < times.length; i++)
		{
			if (times[i] == null)
			{
				times[i] = new TimeSlot(day, slot);
				done = true;
				break;
			}
		}

		return done;
	}

	public void clearTimes()
	{
		for (int i = 0; i < times.length; i++)
		{
			times[i] = null;
		}
	}

	public int slotsCount()
	{
		return times.length;
	}

	public int usedSlots()
	{
		int count = 0;
		for (int i = 0; i < times.length; i++)
		{
			if (!(times[i] == null))
			{
				count++;
			}
		}

		return count;
	}
}

class TimeSlot
{
	private int day;
	private int slot;
	private final String[] DAYS = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};

	public TimeSlot(String d, int pd)
	{
		if (d.equalsIgnoreCase("Mon"))
			day = 0;
		else if (d.equalsIgnoreCase("Tue"))
			day = 1;
		else if (d.equalsIgnoreCase("Wed"))
			day = 2;
		else if (d.equalsIgnoreCase("Thu"))
			day = 3;
		else if (d.equalsIgnoreCase("Fri"))
			day = 4;

		slot = pd;
	}

	public String getDayName()
	{
		return DAYS[day];
	}

	public int getDay()
	{
		return day;
	}

	public int getSlot()
	{
		return slot;
	}
} 