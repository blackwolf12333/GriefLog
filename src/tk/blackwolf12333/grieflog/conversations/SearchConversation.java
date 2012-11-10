package tk.blackwolf12333.grieflog.conversations;

import java.util.ArrayList;

import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.conversations.ConversationAbandonedListener;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.MessagePrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;

import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.PlayerSession;
import tk.blackwolf12333.grieflog.callback.SearchCallback;
import tk.blackwolf12333.grieflog.utils.logging.Events;
import tk.blackwolf12333.grieflog.utils.searching.SearchTask;

public class SearchConversation extends BaseConversation implements ConversationAbandonedListener {

	public SearchConversation(GriefLog plugin, PlayerSession session) {
		super(plugin, session);
		this.conversationFactory.withFirstPrompt(new SearchIntroPrompt());
		this.conversationFactory.addConversationAbandonedListener(this);
		session.beginConversation(this.conversationFactory.buildConversation(session));
	}
	
	@Override
	public void conversationAbandoned(ConversationAbandonedEvent event) {
		if(event.gracefulExit()) {
			ArrayList<String> args = new ArrayList<String>();
			String player = event.getContext().getSessionData("player").toString();
			String eventName = event.getContext().getSessionData("event").toString();
			String world = event.getContext().getSessionData("world").toString();
			addToListIfNotNull(player, args);
			addToListIfNotNull(eventName, args);
			addToListIfNotNull(world, args);
			if((event.getContext().getForWhom() instanceof PlayerSession)) {
				new SearchTask(p, new SearchCallback(p), args);
			} else {
				event.getContext().getForWhom().sendRawMessage("Failed to send your search request, to solve this you could try to reconnect.");
			}
		} else {
			event.getContext().getForWhom().sendRawMessage("Exited");
		}
	}
	
	private class SearchIntroPrompt extends MessagePrompt {

		@Override
		public String getPromptText(ConversationContext context) {
			return "If you want to exit this just type #quit and hit enter at any time:D";
		}

		@Override
		protected Prompt getNextPrompt(ConversationContext context) {
			return new SearchPlayerPrompt();
		}
	}
	
	private class SearchPlayerPrompt extends StringPrompt {

		@Override
		public String getPromptText(ConversationContext context) {
			return "For which player do you want to search, if you don´t want to search for a player use \"null\".";
		}
		
		@Override
		public Prompt acceptInput(ConversationContext context, String input) {
			context.setSessionData("player", input);
			return new SearchEventPrompt();
		}
	}
	
	private class SearchEventPrompt extends StringPrompt {

		@Override
		public Prompt acceptInput(ConversationContext context, String input) {
			if(Events.getEvent(input) != null) {
				context.setSessionData("event", Events.getEvent(input).getEventName());
			} else {
				context.setSessionData("event", "null");
			}
			return new SearchWorldPrompt();
		}

		@Override
		public String getPromptText(ConversationContext context) {
			return "Which event do you want to search for, if you don't want to search an event use \"null\".";
		}
	}
	
	private class SearchWorldPrompt extends StringPrompt {

		@Override
		public Prompt acceptInput(ConversationContext context, String input) {
			context.setSessionData("world", input);
			return new SearchPrompt();
		}

		@Override
		public String getPromptText(ConversationContext arg0) {
			return "Which world do you want to search for, if you don't want to search for an world use \"null\".";
		}
	}
	
	private class SearchPrompt extends MessagePrompt {

		@Override
		public String getPromptText(ConversationContext context) {
			String player = context.getSessionData("player").toString();
			String event = context.getSessionData("event").toString();
			String world = context.getSessionData("world").toString();
			return "Now searching for " + player + " with event " + event + " in world " + world;
		}

		@Override
		protected Prompt getNextPrompt(ConversationContext context) {
			return Prompt.END_OF_CONVERSATION;
		}
	}
}
