/**
 * 
 */
package csv.download;

import java.util.List;

/**
 * @author debmalyajash
 *
 */
@FunctionalInterface
public interface Downloader {
	List<String[]> download(String symbol,String suffix);
}
