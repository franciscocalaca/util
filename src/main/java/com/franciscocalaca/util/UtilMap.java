package com.franciscocalaca.util;

import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class UtilMap {

	@SuppressWarnings("rawtypes")
	public static Object getValueMap(Map<String, Object> map, String attr) {
		String[] attrarray = attr.split("\\.");
		Object iterate = map;
		for (String string : attrarray) {
			if(iterate instanceof Map) {
				iterate = ((Map) iterate).get(string);
				if(iterate == null) {
					return null;
				}
			}
		}
		return iterate;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String, Object> getMapFromJson(String json){
		try {
			ScriptEngineManager sem = new ScriptEngineManager();
			ScriptEngine engine = sem.getEngineByName("javascript");
			String script = "Java.asJSONCompatible(" + json + ")";
			Object result = engine.eval(script);
			Map contents = (Map) result;
			return contents;
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	
}
