package com.franciscocalaca.util.geo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeoJson {

	private List<Feature> features = new ArrayList<>();
	
	public Map<String, Object> getMap(){
		Map<String, Object> geojson = new HashMap<>();
		geojson.put("type", "FeatureCollection");
		
		List<Map<String, Object>> featuresList = new ArrayList<>();
		geojson.put("features", featuresList);

		for(Feature feature : features) {
			featuresList.add(feature.getMap());
		}
		
		return geojson;
	}

	public void addFeature(Feature feature) {
		this.features.add(feature);
	}

	public List<Feature> getFeatures() {
		return features;
	}
}
