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
package weather;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jakewharton.fliptables.FlipTable;

/**
 * @author debmalyajash
 *
 */
public class WeatherByCityCountry {

	/**
	 * 
	 * 
	 * @param args
	 * @throws IOException
	 * @throws MalformedURLException
	 */
	public static void main(String[] args) throws MalformedURLException, IOException {
		Properties properties = new Properties();
		properties.load(ClassLoader.getSystemResource("config.properties").openStream());

		if (args.length != 2) {

			getWeatherDataByCityNCountry(properties, "London", "uk");

		} else {
			getWeatherDataByCityNCountry(properties, args[0], args[1]);
		}

	}

	/**
	 * 
	 * @param properties
	 * @param city
	 * @param country
	 * @throws IOException
	 * @throws MalformedURLException
	 * @throws JSONException
	 */
	private static void getWeatherDataByCityNCountry(Properties properties, String city, String country)
			throws IOException, MalformedURLException, JSONException {
		String appId = properties.getProperty("openweathermap.apikey");
		String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "," + country + "&APPID=" + appId;
		String json = IOUtils.toString(new URL(url).openStream());
		JSONObject weatherData = new JSONObject(json);
		System.out.println(weatherData);
		printWeatherData(weatherData);
	}

	/**
	 * {"dt":1468558600,"coord":{"lon":-0.13,"lat":51.51},"weather":[{"icon":
	 * "02d","description":"clear sky"
	 * ,"main":"Clear","id":800}],"name":"London","cod":200,"main":{"temp":285.
	 * 94,"temp_min":285.94,"grnd_level":1032.38,"humidity":74,"pressure":1032.
	 * 38,"sea_level":1039.97,"temp_max":285.94},"clouds":{"all":8},"id":2643743
	 * ,"sys":{"country":"GB","sunrise":1468555296,"sunset":1468613447,"message"
	 * :0.0152},"base":"stations","wind":{"deg":283.5,"speed":1.51}}
	 * 
	 * @param weatherData
	 * @throws JSONException
	 */
	private static void printWeatherData(JSONObject weatherData) throws JSONException {
		JSONObject coordinates = weatherData.getJSONObject("coord");
		JSONArray weather = weatherData.getJSONArray("weather");
		JSONObject sys = weatherData.getJSONObject("sys");
		JSONObject wind = weatherData.getJSONObject("wind");
		JSONObject main = weatherData.getJSONObject("main");

		String dt = weatherData.get("dt").toString() ;
		Date date = new Date(Long.valueOf(dt));
		System.out.println(date+ " " + Long.valueOf(dt) + " " + dt);
		System.out.println(FlipTable.of(new String[] { "Name", "Value" },
				
				new String[][] { new String[] { "Date", weatherData.get("dt").toString() },
						new String[] { "Longitude",  coordinates.get("lon").toString() },
						new String[] { "Latitude",  String.valueOf(coordinates.get("lat")) },
						new String[] { "Description", (String) weather.getJSONObject(0).get("description") },
						new String[] { "Main", String.valueOf(weather.getJSONObject(0).get("main")) },
						new String[] { "Name", String.valueOf(weatherData.get("name")) },
						new String[] { "temp", String.valueOf(main.get("temp")) },
						new String[] { "temp_min", String.valueOf(main.get("temp_min")) },
						new String[] { "temp_max", String.valueOf(main.get("temp_max")) },
//						new String[] { "grnd_level", String.valueOf(main.get("grnd_level")) },
						new String[] { "humidity", String.valueOf(main.get("humidity")) },
						new String[] { "pressure", String.valueOf(main.get("pressure")) },
//						new String[] { "sea_level", String.valueOf(main.get("sea_level")) },
						new String[] { "Country", String.valueOf(sys.get("country")) },
						new String[] { "Sunrise", sys.get("sunrise").toString() },
						new String[] { "Sunset", sys.get("sunset").toString() },
						new String[] { "Wind deg", wind.get("deg").toString() },
						new String[] { "Wind speed", wind.get("speed").toString() }, }));
	}

}
