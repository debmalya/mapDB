package db;
/**
 * This is a generalized MapWrapper. Every wrapper has a map with name.
 * This class abstracts that generalization.
 * 
 * @author debmalyajash
 *
 */
public abstract class MapWrapper {
	private String mapName;

	public String getMapName() {
		return mapName;
	}

	public void setMapName(String mapName) {
		this.mapName = mapName;
	}
	
}
