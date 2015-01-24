package eden.sun.childrenguard.helper;


public class MapHelper {
	
	//http://api.tiles.mapbox.com/v4/{mapid}/{overlay}/{lon},{lat},{z}/{width}x{height}.{format}?access_token=<your access token>
	//private final static String MAPBOX_IMAGE_URL = "http://api.tiles.mapbox.com/v4/examples.map-zr0njcqy/pin-s-bus+f44(-73.99,40.70,13)/-83.99,40.70,13/500x400.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6IlhHVkZmaW8ifQ.hAMX5hSW-QnTeRCMAy9A8Q";
	private final static String MAPBOX_IMAGE_URL = "http://api.tiles.mapbox.com/v4/%1$s/pin-s-bus+f44(%2$s)/%2$s/500x400.png?access_token=%3$s";
	
	public static String getMapboxStaticMapUrl(Double lon, Double lat){
		String mapid = "examples.map-zr0njcqy";
		// eg. "-73.99,40.70,13";
		String lonlatz = lon + "," + lat + ",13"; 
		String accessToken = "pk.eyJ1IjoibWFwYm94IiwiYSI6IlhHVkZmaW8ifQ.hAMX5hSW-QnTeRCMAy9A8Q";
		String url = String.format(
				MAPBOX_IMAGE_URL,  
				mapid,
				lonlatz,
				accessToken);
		
		return url;
	}
}
