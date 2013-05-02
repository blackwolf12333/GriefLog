package tk.blackwolf12333.grieflog.utils.csv;

import tk.blackwolf12333.grieflog.data.BaseData;

import com.googlecode.jcsv.reader.CSVEntryParser;

public class CSVDataParser implements CSVEntryParser<BaseData> {
	@Override
	public BaseData parseEntry(String... data) {
		return BaseData.loadFromString(arrayToString(data));
	}
	
	public String arrayToString(String... data) {
		String ret = new String();
		for(int i = 0; i < data.length; i++) {
			ret += data[i] + " ";
		}
		return ret.trim();
	}
}
