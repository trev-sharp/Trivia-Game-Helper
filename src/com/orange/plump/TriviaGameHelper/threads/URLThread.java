package com.orange.plump.TriviaGameHelper.threads;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import com.orange.plump.TriviaGameHelper.*;

public class URLThread extends Thread {
	
	private final String title, url, option;
	private final int i;
	
	public URLThread(String title, String url, String option, int i) {
		this.title = title.toLowerCase();
		this.url = url.toLowerCase();
		this.option = option.toLowerCase();
		this.i = i;
	}
	
	public void run() {
		//---HTML Data Grab---
		HelperManager man = App.manager;
		
		String text = "";
		try {
			
			URL urlWeb = new URL(this.url);
			URLConnection connection = urlWeb.openConnection();
			InputStream is = connection.getInputStream();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String s = "";
			
			while((s = br.readLine()) != null) text+= s;
			
		} catch (Exception e) {
			
		}
		text = text.toLowerCase();
		//------------
		//---Variable Grab---
		List<String> allWords = new ArrayList<String>();
		String[] optionWords = option.split(" ");
			
		
		for (String quPart: man.question.split(" "))
			if (!allWords.contains(quPart)) allWords.add(quPart);
		for (int i = 0; i < optionWords.length; i++) {
			String opPart = optionWords[i];
			if (!allWords.contains(opPart)) allWords.add(opPart);
			else optionWords[i] = "";
		}
		
		//------------
		//---Checking Process---
		
		if (title.contains(option)) man.optionPossibility[i]++;
			
		if (url.contains(option)) man.optionPossibility[i]++;
			
		if (text.contains(option)) man.optionPossibility[i]++;
			
		for (String s : optionWords) {
				
			if (man.totalTime / 1000000000 >= man.timeout) break;
				
			if (!s.equalsIgnoreCase("The") && !s.equalsIgnoreCase("That") && !s.equalsIgnoreCase("Your") && s.toCharArray().length > 2)
				if (text.contains(s)) man.optionPossibility[i]++;
		}
		//------------
		if (man.gThreads.get(0).urlThreads.contains(this)) man.gThreads.get(0).urlThreads.remove(this);	
		if (man.yThreads.get(0).urlThreads.contains(this)) man.yThreads.get(0).urlThreads.remove(this);
	}
}
