package tk.blackwolf12333.grieflog.utils;

import java.util.ArrayList;


public class ArgumentParser {

	ArrayList<String> result = new ArrayList<String>();
	public boolean error = false;
	
	public String player;
	public String world;
	public String event;
	
	public ArgumentParser(String[] args) {
		if(args == null) {
			error = true;
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
	
	public ArrayList<String> getResult() {
		return result;
	}
	
	public void parse(ArrayList<String> args) {
		if(error) {
			return;
		}
		if(args.size() == 1) {
			String[] split1 = args.get(0).split(":");
			char ch1 = split1[0].charAt(0);
			
			switch(ch1) {
			case 'p':
				result.add(split1[1]);
				player = split1[1];
				break;
			case 'e':
				result.add(getEventFromAlias(split1[1]));
				event = split1[1];
				break;
			case 'w':
				result.add(split1[1]);
				world = split1[1];
				break;
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
				result.add(split1[1]);
				player = split1[1];
				break;
			case 'e':
				result.add(getEventFromAlias(split1[1]));
				event = split1[1];
				break;
			case 'w':
				result.add(split1[1]);
				world = split1[1];
				break;
			default:
				break;	
			}
			
			switch(ch2) {
			case 'p':
				result.add(split2[1]);
				player = split2[1];
				break;
			case 'e':
				result.add(getEventFromAlias(split2[1]));
				event = split2[1];
				break;
			case 'w':
				result.add(split2[1]);
				world = split2[1];
				break;
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
				result.add(split1[1]);
				player = split1[1];
				break;
			case 'e':
				result.add(getEventFromAlias(split1[1]));
				event = split1[1];
				break;
			case 'w':
				result.add(split1[1]);
				world = split1[1];
				break;
			default:
				break;	
			}
			
			switch(ch2) {
			case 'p':
				result.add(split2[1]);
				player = split2[1];
				break;
			case 'e':
				result.add(getEventFromAlias(split2[1]));
				event = split2[1];
				break;
			case 'w':
				result.add(split2[1]);
				world = split2[1];
				break;
			default:
				break;	
			}
			
			switch(ch3) {
			case 'p':
				result.add(split3[1]);
				player = split3[1];
				break;
			case 'e':
				result.add(getEventFromAlias(split3[1]));
				event = split3[1];
				break;
			case 'w':
				result.add(split3[1]);
				world = split3[1];
				break;
			default:
				break;	
			}
		}
	}
	
	public String getEventFromAlias(String alias) {
		for(Events event : Events.values()) {
			for(String a : event.getAlias()) {
				if(alias.equalsIgnoreCase(a)) {
					return event.getEventName();
				}
			}
		}
		return null;
	}
}
