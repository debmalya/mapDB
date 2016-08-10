/**
 * 
 */
package scrapper.sgx;

import model.StockDetails;

/**
 * @author debmalyajash
 *
 */
@FunctionalInterface
public interface Scrapper {
	StockDetails scrap(final String symbol);
}
