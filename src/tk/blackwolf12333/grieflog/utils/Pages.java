package tk.blackwolf12333.grieflog.utils;

import java.util.ArrayList;

public class Pages {

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
	
	public String[][] makePages(ArrayList<String> text) {
		String[] split = new String[text.size()];
		
		for(int i = 0; i < split.length; i++) {
			split[i] = text.get(i);
		}
		
		int totalPages = (split.length / 9) + 1;
		
		pages = new String[totalPages][9];
		
		for(int j = 0; j < totalPages; j++) {
			if(j == 0) {
				for(int i = 0; i < 9; i++) {
					pages[j][i] = split[i];
				}
			} else {
				for(int i = 0; i < 9; i++) {
					pages[j][i] = split[i * j];
				}
			}
		}
		
		return pages;
	}
	
	public String[][] getPages() {
		return pages;
	}
}
