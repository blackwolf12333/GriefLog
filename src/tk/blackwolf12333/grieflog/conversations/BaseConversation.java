package tk.blackwolf12333.grieflog.conversations;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.conversations.ConversationPrefix;
import org.bukkit.conversations.ExactMatchConversationCanceller;

import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.PlayerSession;

public class BaseConversation {

	protected ConversationFactory conversationFactory;
	GriefLog plugin;
	PlayerSession p;
	
	public BaseConversation(GriefLog plugin, PlayerSession session) {
		this.plugin = plugin;
		this.p = session;
		this.conversationFactory = new ConversationFactory(plugin)
        .withModality(true)
        .withPrefix(new GriefLogPrefix())
        .withConversationCanceller(new ExactMatchConversationCanceller("#quit"));
	}
	
	private class GriefLogPrefix implements ConversationPrefix {
		@Override
		public String getPrefix(ConversationContext context) {
			return ChatColor.GOLD + "[GriefLog] " + ChatColor.BLUE;
		}
	}
	
	protected void addToListIfNotNull(String value, ArrayList<String> list) {
		if(!(value == null)) {
			if(!value.equals("null")) {
				list.add(value);
			}
		}
	}
}
