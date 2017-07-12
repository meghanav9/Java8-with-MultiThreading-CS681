import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebCrawler implements Runnable {
	private static int count = 0;
	static URL website = null;
	private String url;
	
	public WebCrawler(String url){
		this.url = url;
	}
	@Override
	public void run() {
		Set<String> visitedUrls = new HashSet<>();
		try {
			crawl(url, visitedUrls);
		} catch (IOException e) {
		}

	}

	private static void crawl(String url, Set<String> visited) throws IOException {

		// check whether already visited
		if (url.isEmpty() || visited.contains(url)) {
			return;
		}

		// exceptions
		visited.add(url);
		Document doc;
		try {
			doc = Jsoup.connect(url).timeout(10000).get();
		}catch (HttpStatusException e) {
			System.out.println("error (status=" + e.getStatusCode()+ ") fetching URL: " + url);
			return;
		}catch (UnsupportedMimeTypeException e) {
			System.out.println("Unsupported Mime type. "+ url);
			return;
		} catch (MalformedURLException e) {
			System.out.println("Unsupported for URL: " + url);
			return;
		} catch (IOException e) {
			System.out.println("Timed out URL: " + url);
			return;
		}

		// Crawling the url for html links
		Elements linkname = doc.select("a[href]");
		System.out.println("\nCrawling ..." + url);
		System.out.println("Links:");
		for (Element link : linkname) {
			if (count == 5) {
				return;
			}
			printout(" <%s> (%s)", link.attr("abs:href"), trim(link.text(), 10));
			count++;
			String urll = link.attr("abs:href");

			// indexing the crawled html files
			for (int i = 0; i < 5; i++) {
				website = new URL(urll);
				ReadableByteChannel readbc = Channels.newChannel(website
						.openStream());
				try (FileOutputStream fos = new FileOutputStream(link.text()
						+ ".html")) {
					fos.getChannel().transferFrom(readbc, 0, Integer.MAX_VALUE);
				}
			}
		}

	}

	private static void printout(String msg, Object... args) {
		System.out.println(String.format(msg, args));
	}

	private static String trim(String s, int width) {
		if (s.length() > width)
			return s.substring(0, width - 1) + ".";
		else
			return s;
	}

}
