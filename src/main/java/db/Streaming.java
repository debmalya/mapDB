package db;

import java.util.Iterator;
import java.util.Map;

import org.mapdb.DB;

public class Streaming {
	
	public Map createMap(DB db,Iterator streamingData){
		return db.treeMap("streaming_map").createFrom(streamingData);
		
	}

}
