package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

public class StockServer {

	private static final Logger LOGGER = Logger.getLogger(StockServer.class);

	public static void serve() {
		try {
			HttpServer httpServer = HttpServer.create(new InetSocketAddress(8081), 0);

			// root context
			httpServer.createContext("/", (HttpExchange he) -> {
				final URI requestURI = he.getRequestURI();
				try (final OutputStream out = he.getResponseBody()) {
					final String path = requestURI.getPath();
					if (path.length() < 2) {
						he.sendResponseHeaders(404, 0);
						return;
					}
					final URL resource = StockServer.class.getResource(path);
					if (resource == null) {
						he.sendResponseHeaders(404, 0);
						return;
					}

					if (path.endsWith("html")) {
						he.getResponseHeaders().add("content-type", "text/html");
					}
					if (path.endsWith("js")) {
						he.getResponseHeaders().add("content-type", "text/javascript");
					}
					final String content = new BufferedReader(
							new InputStreamReader(resource.openStream(), StandardCharsets.UTF_8)).lines()
									.collect(Collectors.joining("\n"));

					he.sendResponseHeaders(200, content.getBytes().length);
					out.write(content.getBytes());
					out.flush();
				}
				he.close();
			});

			httpServer.createContext("/api/stocks", (HttpExchange he) -> {
				final String path = "/contracts/stocks.json";
				final URL resource = StockServer.class.getResource(path);

				try (final OutputStream out = he.getResponseBody()) {
					final String content = new BufferedReader(
							new InputStreamReader(resource.openStream(), StandardCharsets.UTF_8)).lines()
									.collect(Collectors.joining("\n"));

					he.getResponseHeaders().add("content-type", "application/json; charset=utf-8");
					he.sendResponseHeaders(200, content.getBytes().length);
					out.write(content.getBytes());
					out.flush();
				}
				he.close();
			});

			httpServer.createContext("/js/", (HttpExchange he) -> {
				final String path = he.getRequestURI().getPath();
				final URL resource = StockServer.class.getResource(path);

				try (final OutputStream out = he.getResponseBody()) {
					final String content = new BufferedReader(
							new InputStreamReader(resource.openStream(), StandardCharsets.UTF_8)).lines()
									.collect(Collectors.joining("\n"));
					he.getResponseHeaders().add("content-type", "application/javascript; charset=utf-8");
					he.sendResponseHeaders(200, content.getBytes().length);
					out.write(content.getBytes());
					out.flush();
				}
				he.close();
			});

			httpServer.setExecutor(null);
			httpServer.start();

			LOGGER.debug("Server started");

		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

}
