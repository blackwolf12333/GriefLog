package tk.blackwolf12333.grieflog.utils.searching;

import java.util.ArrayList;

import tk.blackwolf12333.grieflog.utils.logging.Events;

public class ArgumentParser {

	public boolean argsNullError = false;
	public boolean eventNullError = false;
	
	public String player;
	public String world;
	public String event;
	public String blockFilter;
	
	public ArgumentParser(String[] args) {
		if(args == null) {
			argsNullError = true;
			return;
		}
		ArrayList<String> arguments = new ArrayList<String>();
		
		for(int i = 0; i < args.length; i++) {
			if(i >= 1) {
				arguments.add(args[i]);
			}
		}
		parse(arguments);
	}
	
	public ArgumentParser(ArrayList<String> args) {
		parse(args);
	}
	
	public void parse(ArrayList<String> args) {
		if(argsNullError) {
			return;
		}
		try {
			if(args.size() == 1) {
				String[] split1 = args.get(0).split(":");
				char ch1 = split1[0].charAt(0);
				
				switch(ch1) {
				case 'p':
					player = split1[1];
					break;
				case 'e':
					event = split1[1];
					break;
				case 'w':
					world = split1[1];
					break;
				case 'b':
					blockFilter = split1[1];
				default:
					break;	
				}
			} else if(args.size() == 2) {
				String[] split1 = args.get(0).split(":");
				String[] split2 = args.get(1).split(":");
				char ch1 = split1[0].charAt(0);
				char ch2 = split2[0].charAt(0);
				
				switch(ch1) {
				case 'p':
					player = split1[1];
					break;
				case 'e':
					event = split1[1];
					break;
				case 'w':
					world = split1[1];
					break;
				case 'b':
					blockFilter = split1[1];
				default:
					break;	
				}
				
				switch(ch2) {
				case 'p':
					player = split2[1];
					break;
				case 'e':
					event = split2[1];
					break;
				case 'w':
					world = split2[1];
					break;
				case 'b':
					blockFilter = split2[1];
				default:
					break;
				}
			} else if(args.size() == 3) {
				String[] split1 = args.get(0).split(":");
				String[] split2 = args.get(1).split(":");
				String[] split3 = args.get(2).split(":");
				char ch1 = split1[0].charAt(0);
				char ch2 = split2[0].charAt(0);
				char ch3 = split3[0].charAt(0);
				
				switch(ch1) {
				case 'p':
					player = split1[1];
					break;
				case 'e':
					event = split1[1];
					break;
				case 'w':
					world = split1[1];
					break;
				case 'b':
					blockFilter = split1[1];
				default:
					break;	
				}
				
				switch(ch2) {
				case 'p':
					player = split2[1];
					break;
				case 'e':
					event = split2[1];
					break;
				case 'w':
					world = split2[1];
					break;
				case 'b':
					blockFilter = split2[1];
				default:
					break;	
				}
				
				switch(ch3) {
				case 'p':
					player = split3[1];
					break;
				case 'e':
					event = split3[1];
					break;
				case 'w':
					world = split3[1];
					break;
				case 'b':
					blockFilter = split3[1];
				default:
					break;	
				}
			} else if(args.size() == 4) {
				String[] split1 = args.get(0).split(":");
				String[] split2 = args.get(1).split(":");
				String[] split3 = args.get(2).split(":");
				String[] split4 = args.get(3).split(":");
				char ch1 = split1[0].charAt(0);
				char ch2 = split2[0].charAt(0);
				char ch3 = split3[0].charAt(0);
				char ch4 = split4[0].charAt(0);
				
				switch(ch1) {
				case 'p':
					player = split1[1];
					break;
				case 'e':
					event = split1[1];
					break;
				case 'w':
					world = split1[1];
					break;
				case 'b':
					blockFilter = split1[1];
				default:
					break;	
				}
				
				switch(ch2) {
				case 'p':
					player = split2[1];
					break;
				case 'e':
					event = split2[1];
					break;
				case 'w':
					world = split2[1];
					break;
				case 'b':
					blockFilter = split2[1];
				default:
					break;	
				}
				
				switch(ch3) {
				case 'p':
					player = split3[1];
					break;
				case 'e':
					event = split3[1];
					break;
				case 'w':
					world = split3[1];
					break;
				case 'b':
					blockFilter = split3[1];
				default:
					break;
				}
				
				switch(ch4) {
				case 'p':
					player = split4[1];
					break;
				case 'e':
					event = split4[1];
					break;
				case 'w':
					world = split4[1];
					break;
				case 'b':
					blockFilter = split4[1];
				default:
					break;
				}
			}
		} catch(NullPointerException e) {
			eventNullError = true;
		} catch(ArrayIndexOutOfBoundsException e) {
			argsNullError = true;
		}
		
	}
	
	public String getEventFromAlias(String alias) {
		return Events.getEvent(alias).getEventName();
	}
}
