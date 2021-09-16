package com.franciscocalaca.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CSVUtil {

	private String delimiter;
	
	private Scanner scan;

	private String actualLine;
	
	private String [] actualData;
	
	private Map<String, Integer> indexMap = new HashMap<>();
	
	private String[] columns;
	
	public CSVUtil(String delimiter, InputStream is, String charset, boolean hasTitle) {
		this.delimiter = delimiter;
		this.scan = new Scanner(is, charset);
		if(hasTitle) {
			this.columns = getData();
			for (int i = 0; i < this.columns.length; i++) {
				indexMap.put(this.columns[i], i);
			}
		}
	}
	
	public boolean hasNext() {
		return scan.hasNext();
	}
	
	public String [] getData() {
		String patternStr = String.format("(?:%s|\\n|^)(\"(?:(?:\"\")*[^\"]*)*\"|[^\"%s\\n]*|(?:\\n|$))", delimiter, delimiter);
		Pattern pattern = Pattern.compile(patternStr);
		
		
		this.actualLine = delimiter + scan.nextLine();
//		line = line.replace("\"", "");
		Matcher matcher = pattern.matcher(this.actualLine);
		List<String> allMatches = new ArrayList<String>();
		while(matcher.find()) {
			allMatches.add(matcher.group());
//			System.out.println(matcher.start());
		}
		String [] data = new String[allMatches.size()];
		for (int i = 0; i < allMatches.size(); i++) {
			String value = allMatches.get(i).trim();
			if(value.startsWith(";")) {
				value = value.substring(1);
			}
			value = value.replace("\"", "");
			allMatches.set(i, value);
			value = value.replaceAll("[^\\x00-\\xFF]+", "");
			data[i] = value.trim();
		}
		this.actualData = data;
		return data;
	}

	public Map<String, String> getDataAsMap(){
		String [] data = getData();
		Map<String, String> result = new HashMap<>();
		for (int i = 0; i < data.length; i++) {
			String key = columns[i].trim();
			result.put(key.replaceAll("[^a-zA-Z0-9 -]", ""), data[i]);
		}
		return result;
	}
	
	public String getLine() {
		return this.actualLine;
	}

	public String getActualLine() {
		return actualLine;
	}

	public String[] getActualData() {
		return actualData;
	}


}
