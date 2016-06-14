package scrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class In {

	private URL urlToRead;

	public In(URL url) {
		urlToRead = url;
	}

	public String readAll() {
		StringBuilder all = new StringBuilder();
		try(BufferedReader in = new BufferedReader(new InputStreamReader(urlToRead.openStream()))) {
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				all.append(inputLine);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return all.toString();
	}
}
