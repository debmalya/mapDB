/**
 * Copyright 2015-2016 Debmalya Jash
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package model;

/**
 * @author debmalyajash
 *
 */
public class Company {
	/**
	 * CIK (Central Index Key) number of the company.
	 */
	private String cik;
	
	/**
	 * Standard industrial classification code
	 */
	private String sic;
	
	/**
	 * Stock symbol.
	 */
	private String symbol;
}