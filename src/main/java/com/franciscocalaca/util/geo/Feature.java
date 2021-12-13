package com.franciscocalaca.util.geo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Feature {

	private EnumTypeFeature type;
	
	private Map<String, Object> properties = new HashMap<>();
	
	private List<Coordinate> coordinates = new ArrayList<>();
	
	public Feature(EnumTypeFeature type) {
		this.type = type;
	}
	
	public void setMarkerColor(String color) {
		properties.put("marker-color", color);
	}
	
	public void setMarkerSymbol(String symbol) {
		properties.put("marker-symbol", symbol);
	}
	
	public void addCoord(BigDecimal latitude, BigDecimal longitude) {
		Coordinate coord = new Coordinate();
		coord.setLatitude(latitude);
		coord.setLongitude(longitude);
		this.coordinates.add(coord);
	}
	
	public Map<String, Object> getMap(){
		Map<String, Object> feature = new HashMap<>();

		feature.put("type", "Feature");
		feature.put("properties", properties);
		
		Map<String, Object> geometry = new HashMap<String, Object>();
		feature.put("geometry", geometry);
		geometry.put("type", type.toString());
		
		if(type == EnumTypeFeature.Point) {
			if(!coordinates.isEmpty()) {
				List<BigDecimal> coordinatesList = new ArrayList<>();
				geometry.put("coordinates", coordinatesList);
				coordinatesList.add(coordinates.get(0).getLatitude());
				coordinatesList.add(coordinates.get(0).getLongitude());
			}
			
		}else {
			List<List<BigDecimal>> coordinatesList = new ArrayList<>();
			geometry.put("coordinates", coordinatesList);
			
			for(Coordinate coord : coordinates) {
				List<BigDecimal> coordList = new ArrayList<BigDecimal>();
				coordList.add(coord.getLatitude());
				coordList.add(coord.getLongitude());
				coordinatesList.add(coordList);
			}
		}
		return feature;
	}

	public EnumTypeFeature getType() {
		return type;
	}

	public void setType(EnumTypeFeature type) {
		this.type = type;
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

	public List<Coordinate> getCoordinates() {
		return coordinates;
	}
}
