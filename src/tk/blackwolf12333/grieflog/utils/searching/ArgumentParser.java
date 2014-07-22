package tk.blackwolf12333.grieflog.utils.searching;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;
import java.lang.Exception;

import org.bukkit.Bukkit;

import tk.blackwolf12333.grieflog.utils.logging.Events;
import tk.blackwolf12333.grieflog.utils.GriefLogException;
import tk.blackwolf12333.grieflog.utils.UUIDApi;

public class ArgumentParser implements Serializable {

	private static final long serialVersionUID = -1852087723338527110L;
	public boolean eventNullError = false;
	public boolean argsNullError = false;
	
	public UUID player;
	public String world;
	public String event;
	public String blockFilter;
	public String time;
	public boolean worldedit;
	
	public ArgumentParser(String[] args) throws GriefLogException {
		if(args != null) {
			ArrayList<String> arguments = new ArrayList<String>();
			
			for(int i = 0; i < args.length; i++) {
				if(i >= 1) {
					arguments.add(args[i]);
				}
			}
			parse(arguments);
		}
	}
	
	public void parse(ArrayList<String> args) throws GriefLogException {
		try {
			if(args.size() == 1) {
				if(args.get(0).equalsIgnoreCase("we")) {
					this.worldedit = true;
					return; // this is not possible!
				} else {
					parse1Argument(args.get(0));
				}
			} else if(args.size() == 2) {
				if(args.get(0).equalsIgnoreCase("we")) {
					this.worldedit = true;
					parse1Argument(args.get(1));
				} else {
					parse2Arguments(args.get(0), args.get(1));
				}
			} else if(args.size() == 3) {
				if(args.get(0).equalsIgnoreCase("we")) {
					this.worldedit = true;
					parse2Arguments(args.get(1), args.get(2));
				} else {
					parse3Arguments(args.get(0), args.get(1), args.get(2));
				}
			} else if(args.size() == 4) {
				if(args.get(0).equalsIgnoreCase("we")) {
					this.worldedit = true;
					parse3Arguments(args.get(1), args.get(2), args.get(3));
				} else {
					parse4Arguments(args.get(0), args.get(1), args.get(2), args.get(3));
				}
			} else if(args.size() == 5) {
				if(args.get(0).equalsIgnoreCase("we")) {
					this.worldedit = true;
					parse4Arguments(args.get(1), args.get(2), args.get(3), args.get(4));
				} else {
					parse5Arguments(args.get(0), args.get(1), args.get(2), args.get(3), args.get(5));
				}
			}
		} catch(Exception e) {
			String errorMessage = "Error parsing an argument of this command: ";
			for(String s : args) {
				errorMessage += s + " ";
			}
			throw new GriefLogException(errorMessage, e);
		}
	}
	
	public void checkArgument(char identifier, String arg) {
		switch(identifier) {
		case 'p':
			player = UUID.fromString(UUIDApi.getUUIDAsString(arg));
			break;
		case 'e':
			event = getEventFromAlias(arg);
			break;
		case 'w':
			world = arg;
			break;
		case 'b':
			blockFilter = arg;
			break;
		case 't':
			time = arg;
			break;
		default:
			break;	
		}
	}
	
	private void parse1Argument(String arg) {
		String[] split1 = arg.split(":");
		char ch1 = split1[0].charAt(0);
		
		checkArgument(ch1, split1[1]);
	}
	
	private void parse2Arguments(String arg, String arg1) {
		String[] split1 = arg.split(":");
		String[] split2 = arg1.split(":");
		char ch1 = split1[0].charAt(0);
		char ch2 = split2[0].charAt(0);
		
		checkArgument(ch1, split1[1]);
		checkArgument(ch2, split2[1]);
	}
	
	private void parse3Arguments(String arg, String arg1, String arg2) {
		String[] split1 = arg.split(":");
		String[] split2 = arg1.split(":");
		String[] split3 = arg2.split(":");
		char ch1 = split1[0].charAt(0);
		char ch2 = split2[0].charAt(0);
		char ch3 = split3[0].charAt(0);
		
		checkArgument(ch1, split1[1]);
		checkArgument(ch2, split2[1]);
		checkArgument(ch3, split3[1]);
	}
	
	private void parse4Arguments(String arg, String arg1, String arg2, String arg3) {
		String[] split1 = arg.split(":");
		String[] split2 = arg1.split(":");
		String[] split3 = arg2.split(":");
		String[] split4 = arg3.split(":");
		char ch1 = split1[0].charAt(0);
		char ch2 = split2[0].charAt(0);
		char ch3 = split3[0].charAt(0);
		char ch4 = split4[0].charAt(0);
		
		checkArgument(ch1, split1[1]);
		checkArgument(ch2, split2[1]);
		checkArgument(ch3, split3[1]);
		checkArgument(ch4, split4[1]);
	}
	
	private void parse5Arguments(String arg, String arg1, String arg2, String arg3, String arg4) {
		String[] split1 = arg.split(":");
		String[] split2 = arg1.split(":");
		String[] split3 = arg2.split(":");
		String[] split4 = arg3.split(":");
		String[] split5 = arg4.split(":");
		char ch1 = split1[0].charAt(0);
		char ch2 = split2[0].charAt(0);
		char ch3 = split3[0].charAt(0);
		char ch4 = split4[0].charAt(0);
		char ch5 = split5[0].charAt(0);
		
		checkArgument(ch1, split1[1]);
		checkArgument(ch2, split2[1]);
		checkArgument(ch3, split3[1]);
		checkArgument(ch4, split4[1]);
		checkArgument(ch5, split5[1]);
	}
	
	public String getEventFromAlias(String alias) {
		return Events.getEvent(alias).getEventName();
	}
}
