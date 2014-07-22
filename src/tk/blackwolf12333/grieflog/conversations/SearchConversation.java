package tk.blackwolf12333.grieflog.conversations;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.conversations.ConversationAbandonedListener;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.conversations.ConversationPrefix;
import org.bukkit.conversations.MessagePrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;

import tk.blackwolf12333.grieflog.utils.searching.SearchTask;
import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.PlayerSession;
import tk.blackwolf12333.grieflog.callback.SearchCallback;
import tk.blackwolf12333.grieflog.utils.logging.Events;
import tk.blackwolf12333.grieflog.utils.searching.ArgumentParser;
import tk.blackwolf12333.grieflog.utils.GriefLogException;
import tk.blackwolf12333.grieflog.utils.UUIDApi;

public class SearchConversation implements ConversationAbandonedListener {

	boolean rollback;
	boolean worldedit;
	GriefLog plugin;
	PlayerSession session;
	ConversationFactory conversationFactory;
	
	public SearchConversation(GriefLog plugin, PlayerSession session, boolean rollback, boolean worldedit) {
		this.plugin = plugin;
		this.session = session;
		this.conversationFactory = new ConversationFactory(plugin);
		this.conversationFactory.withFirstPrompt(new SearchIntroPrompt());
		this.conversationFactory.withPrefix(new GriefLogPrefix());
		this.conversationFactory.withModality(true);
		this.conversationFactory.addConversationAbandonedListener(this);
		this.conversationFactory.withEscapeSequence("#quit");
		this.rollback = rollback;
		this.worldedit = worldedit;
		session.beginConversation(this.conversationFactory.buildConversation(session));
	}
	
	private class GriefLogPrefix implements ConversationPrefix {
		@Override
		public String getPrefix(ConversationContext context) {
			return ChatColor.GOLD + "[GriefLog] " + ChatColor.BLUE;
		}
	}
	
	@Override
	public void conversationAbandoned(ConversationAbandonedEvent event) {
		if(event.gracefulExit()) {
			ArgumentParser parser = fillParser(event);
			if((event.getContext().getForWhom() instanceof PlayerSession)) {
				if(rollback) {
					new SearchTask(session, new SearchCallback(SearchCallback.Type.ROLLBACK), parser);
				} else {
					new SearchTask(session, new SearchCallback(SearchCallback.Type.SEARCH), parser);
				}
			} else {
				event.getContext().getForWhom().sendRawMessage("Failed to send your search request, to solve this you could try to reconnect.");
			}
		} else {
			event.getContext().getForWhom().sendRawMessage("Exited");
		}
	}
	
	private ArgumentParser fillParser(ConversationAbandonedEvent e) {
		ArgumentParser parser = null;
		try {
			parser = new ArgumentParser(null);
		} catch(GriefLogException exception) {
			exception.printStackTrace();
		}
		Object player = e.getContext().getSessionData("player");
		Object event = e.getContext().getSessionData("event");
		Object world = e.getContext().getSessionData("world");
		Object time = e.getContext().getSessionData("time");
		
		if(player != null) {
			parser.player = UUID.fromString(
				UUIDApi.getUUIDAsString(player.toString()));
		}
		if(event != null) {
			parser.event = event.toString();
		}
		if(world != null) {
			parser.world = world.toString();
		}
		if(time != null) {
			parser.time = time.toString();
		}
		parser.worldedit = this.worldedit;
		return parser;
	}

	private class SearchIntroPrompt extends MessagePrompt {

		@Override
		public String getPromptText(ConversationContext context) {
			return 	"If you want to exit this just type #quit and hit enter at any time.\n" +
					"If you want to skip an option you can use #next, if you want to go back to a previous question do #prev.";
		}

		@Override
		protected Prompt getNextPrompt(ConversationContext context) {
			return new SearchPlayerPrompt();
		}
	}
	
	private class SearchPlayerPrompt extends StringPrompt {

		@Override
		public String getPromptText(ConversationContext context) {
			return "For which player do you want to search?";
		}
		
		@Override
		public Prompt acceptInput(ConversationContext context, String input) {
			if(input.equalsIgnoreCase("#next")) {
				context.setSessionData("player", null);
				return new SearchEventPrompt();
			} else if(input.equalsIgnoreCase("#prev")) {
				return new SearchIntroPrompt();
			}
			context.setSessionData("player", input);
			return new SearchEventPrompt();
		}
	}
	
	private class SearchEventPrompt extends StringPrompt {

		@Override
		public Prompt acceptInput(ConversationContext context, String input) {
			if(input.equalsIgnoreCase("#next")) {
				context.setSessionData("event", null);
				return new SearchWorldPrompt();
			} else if(input.equalsIgnoreCase("#prev")) {
				return new SearchPlayerPrompt();
			}
			if(Events.getEvent(input) != null) {
				context.setSessionData("event", Events.getEvent(input).getEventName());
			} else {
				context.setSessionData("event", null);
			}
			return new SearchWorldPrompt();
		}

		@Override
		public String getPromptText(ConversationContext context) {
			return "Which event do you want to search for?";
		}
	}
	
	private class SearchWorldPrompt extends StringPrompt {

		@Override
		public Prompt acceptInput(ConversationContext context, String input) {
			if(input.equalsIgnoreCase("#next")) {
				context.setSessionData("world", null);
				return new SearchTimePrompt();
			} else if(input.equalsIgnoreCase("#prev")) {
				return new SearchEventPrompt();
			}
			context.setSessionData("world", input);
			return new SearchTimePrompt();
		}

		@Override
		public String getPromptText(ConversationContext arg0) {
			return "Which world do you want to search for?";
		}
	}
	
	private class SearchTimePrompt extends StringPrompt {
		@Override
		public String getPromptText(ConversationContext arg0) {
			return "How far back would you like the search to go?";
		}
		
		@Override
		public Prompt acceptInput(ConversationContext context, String input) {
			if(input.equalsIgnoreCase("#next")) {
				context.setSessionData("time", null);
				return Prompt.END_OF_CONVERSATION;
			} else if(input.equalsIgnoreCase("#prev")) {
				return new SearchWorldPrompt();
			}
			context.setSessionData("time", input);
			return Prompt.END_OF_CONVERSATION;
		}
	}
}
