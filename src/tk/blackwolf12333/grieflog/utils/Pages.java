package tk.blackwolf12333.grieflog.utils;

import tk.blackwolf12333.grieflog.api.IPages;

public class Pages implements IPages {

	public static String[][] pages;
	
	public static String[] getPage(int page) {
		
		if(page >= pages.length) {
			String[] ret = {"No Page Found Here"};
			return ret;
		} else {
			String[] ret = pages[page];
			return ret;
		}
	}
	
	public String[][] makePages(String text) {
		String[] split = text.split(System.getProperty("line.separator"));
		
		int totalPages = (split.length / 9) + 1;
		
		pages = new String[totalPages][9];
		
		for(int j = 0; j < totalPages; j++) {
			for(int i = 0; i < 9; i++) {
				pages[j][i] = split[i*j];
			}
		}
		
		return pages;
	}
	
	public String[][] getPages() {
		return pages;
	}
}
