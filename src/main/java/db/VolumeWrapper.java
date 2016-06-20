package db;

import org.mapdb.volume.MappedFileVol;
import org.mapdb.volume.Volume;

/**
 * SortedTableMap is read-only and does not support updates. Changes should be
 * applied by creating new Map with Data Pump. Usually one places change into
 * 
 * @author debmalyajash
 *
 */
public class VolumeWrapper {

	private String volumeFileName;
	private Volume volume;
	
	public VolumeWrapper(final String fileName) {
		volumeFileName = fileName;
		
		// second parameter is whether readOnly or not?
		setVolume(MappedFileVol.FACTORY.makeVolume(volumeFileName, true));
	}

	public Volume getVolume() {
		return volume;
	}

	public void setVolume(Volume volume) {
		this.volume = volume;
	}
}
