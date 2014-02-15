package tk.blackwolf12333.grieflog.utils.searching;

import org.junit.Test;

import junit.framework.TestCase;

public class ArgumentParserTest extends TestCase {
	@Test
	public void testCheckArgument() {
		ArgumentParser parser = new ArgumentParser(null);
		parser.checkArgument('p', "blackwolf12333");
		TestCase.assertEquals("blackwolf12333", parser.player);
		parser.checkArgument('e', "break");
		TestCase.assertEquals("[BLOCK_BREAK]", parser.event);
		parser.checkArgument('w', "world");
		TestCase.assertEquals("world", parser.world);
		parser.checkArgument('b', "AIR");
		TestCase.assertEquals("AIR", parser.blockFilter);
		parser.checkArgument('t', "2h");
		TestCase.assertEquals("2h", parser.time);
	}
}
