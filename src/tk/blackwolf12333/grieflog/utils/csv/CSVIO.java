package tk.blackwolf12333.grieflog.utils.csv;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import tk.blackwolf12333.grieflog.data.BaseData;

import com.googlecode.jcsv.reader.CSVReader;
import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;
import com.googlecode.jcsv.writer.CSVWriter;
import com.googlecode.jcsv.writer.internal.CSVWriterBuilder;

public class CSVIO {

	CSVWriter<String[]> csvWriter;
	CSVReader<BaseData> csvReader;
	
	public void write(File file, String[] data) {
		try {
			csvWriter = CSVWriterBuilder.newDefaultWriter(new FileWriter(file, true));
			this.csvWriter.write(data);
			this.csvWriter.flush();
			this.csvWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<BaseData> read(File file) {
		List<BaseData> ret = null;
		try {
			csvReader = new CSVReaderBuilder<BaseData>(new FileReader(file)).entryParser(new CSVDataParser()).build();
			ret = csvReader.readAll();
			csvReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}
}
