package tk.blackwolf12333.grieflog.conversations;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.conversations.BooleanPrompt;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.conversations.ConversationAbandonedListener;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.MessagePrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.conversations.ValidatingPrompt;

import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.PlayerSession;
import tk.blackwolf12333.grieflog.callback.RollbackCallback;
import tk.blackwolf12333.grieflog.utils.logging.Events;
import tk.blackwolf12333.grieflog.utils.searching.SearchTask;

public class RollbackConversation extends BaseConversation implements ConversationAbandonedListener {

	public RollbackConversation(GriefLog plugin, PlayerSession session) {
		super(plugin, session);
		this.conversationFactory.addConversationAbandonedListener(this);
		this.conversationFactory.withFirstPrompt(new RollbackIntroPrompt());
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
				new SearchTask(p, new RollbackCallback(p), args);
			} else {
				event.getContext().getForWhom().sendRawMessage("Failed to send your rollback request, to solve this you could try to reconnect.");
			}
		} else {
			event.getContext().getForWhom().sendRawMessage("Exited!");
		}
	}
	
	private class RollbackIntroPrompt extends MessagePrompt {

		@Override
		public String getPromptText(ConversationContext arg0) {
			return "If you want to exit this just type #quit and hit enter at any time:D";
		}

		@Override
		protected Prompt getNextPrompt(ConversationContext arg0) {
			return new RollbackPlayerPrompt();
		}
		
	}
	
	private class RollbackPlayerPrompt extends StringPrompt {

		@Override
		public String getPromptText(ConversationContext context) {
			return "Which player do you want to rollback, if you don´t want to rollback a player use \"null\".";
		}
		
		@Override
		public Prompt acceptInput(ConversationContext context, String input) {
			context.setSessionData("player", input);
			return new RollbackEventPrompt();
		}
	}
	
	private class RollbackEventPrompt extends StringPrompt {
		@Override
		public String getPromptText(ConversationContext context) {
			return "Which event do you want to rollback, if you don´t want to rollback an event use \"null\".";
		}
		
		@Override
		public Prompt acceptInput(ConversationContext context, String input) {
			if(Events.getEvent(input) != null) {
				context.setSessionData("event", Events.getEvent(input).getEventName());
			} else {
				context.setSessionData("event", "null");
			}
			
			return new RollbackWorldPrompt();
		}
	}
	
	private class RollbackWorldPrompt extends StringPrompt {
		@Override
		public String getPromptText(ConversationContext context) {
			return "In which world do you want to rollback, if you don´t want to rollback in a world use \"null\".";
		}
		
		@Override
		public Prompt acceptInput(ConversationContext context, String input) {
			context.setSessionData("world", input);
			return Prompt.END_OF_CONVERSATION; //new RollbackExcludeTypesQuestionPrompt();
		}
	}
	
	@SuppressWarnings("unused") // TODO: implement exluded types
	private class RollbackExcludeTypesQuestionPrompt extends BooleanPrompt {
		@Override
		public String getPromptText(ConversationContext context) {
			return "Do you want to exclude block types from rolling back?";
		}
		
		@Override
		protected Prompt acceptValidatedInput(ConversationContext arg0, boolean input) {
			if(input) {
				return new RollbackExcludeTypesPrompt();
			}
			return Prompt.END_OF_CONVERSATION;
		}
	}
	
	private class RollbackExcludeTypesPrompt extends ValidatingPrompt {

		ArrayList<String> types = new ArrayList<String>();
		
		public RollbackExcludeTypesPrompt() {
			for(Material m : Material.values()) {
				types.add(m.toString().replace("_", ""));
			}
		}
		
		@Override
		public String getPromptText(ConversationContext context) {
			return "Which type of blocks do you want to exclude from rollback?";
		}

		@Override
		protected Prompt acceptValidatedInput(ConversationContext context, String input) {
			context.setSessionData("exlude", input);
			return Prompt.END_OF_CONVERSATION;
		}

		@Override
		protected boolean isInputValid(ConversationContext context, String input) {
			if(input.split(", ").length == 0) {
				for(String s : input.split(", ")) {
					for(String m : types) {
						if(s.equals(m.toLowerCase())) {
							return true;
						}
					}
					return types.contains(s);
				}
				return types.contains(input);
			} else {
				for(String s : types) {
					if(s.toLowerCase().equals(input)) {
						return true;
					} else {
						return types.contains(s);
					}
				}
				return types.contains(input);
			}
		}
	}
}
