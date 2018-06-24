package com.orange.plump.TriviaGameHelper.threads;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.orange.plump.TriviaGameHelper.App;

public class YahooSearchThread extends Thread {
	
	private final String searchTerm;
	public List<URLThread> urlThreads;
	
	public YahooSearchThread(String searchTerm) {
		this.searchTerm = searchTerm.replaceAll(" ", "+");
		urlThreads = new ArrayList<URLThread>();
	}
	
	public void run() {
		
		System.setProperty("http.agent", App.manager.USER_AGENT);
		Document doc = null;
		
		try {
			doc = Jsoup.connect(App.manager.GOOGLE_URL + this.searchTerm).userAgent(App.manager.USER_AGENT).get();
		} catch (Exception e) {}
		
		int amount = 0;
        if (doc != null && amount != 3) for (Element result : doc.select("h3.r a")){
        	
            final String title = result.text();
            final String url = result.attr("href");
            
            for (int i = 0; i < App.manager.options.size(); i++) {
				URLThread urlThread = new URLThread(title, url, App.manager.options.get(i), i);
				urlThreads.add(urlThread);
				urlThread.start();
			}
            amount++;
        }
	}
}
