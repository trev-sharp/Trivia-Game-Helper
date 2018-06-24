package com.orange.plump.TriviaGameHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.orange.plump.TriviaGameHelper.threads.GoogleSearchThread;
import com.orange.plump.TriviaGameHelper.threads.ThreadCounterThread;
import com.orange.plump.TriviaGameHelper.threads.YahooSearchThread;

public class HelperManager {
	
	public final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36";
	public final String GOOGLE_URL = "https://google.com/search?q=";
	public final String YAHOO_URL = "https://search.yahoo.com/search?p=";
	
	public List<String> options;
	public int[] optionPossibility;
	
	public String question;
	public boolean notCase = false;
	
	public final Scanner scan = new Scanner(System.in);
	
	public ThreadCounterThread threadCounter;
	public int timeSpent = 0;
	public final int timeout = 8;
	
	public List<GoogleSearchThread> gThreads;
	public List<YahooSearchThread> yThreads;
	
	public long totalTime = 0, startTime = 0;
	
	public HelperManager() {
		 threadCounter = new ThreadCounterThread();
		 gThreads = new ArrayList<GoogleSearchThread>();
		 gThreads.add(null);
		 yThreads = new ArrayList<YahooSearchThread>();
		 yThreads.add(null);
		 options = new ArrayList<String>();
	}

	public void start() {
		totalTime = 0;
		
		timeSpent = 0;
		notCase = false;
		threadCounter = new ThreadCounterThread();
		this.options.clear();
		this.optionPossibility = new int[3];
		
		App.sayTranslation(0);
		this.question = questionParser(scan.nextLine());
		
		for (int i = 0; i < 3; i++) {
			App.sayTranslation(1, String.valueOf(i + 1));
			this.options.add(scan.nextLine());
		}
		
		
		App.sayTranslation(2);
		startTime = System.nanoTime();
		GoogleSearchThread googleThread = new GoogleSearchThread(question);
		gThreads.set(0, googleThread);
		googleThread.start();
		
		YahooSearchThread yahooThread = new YahooSearchThread(question);
		yThreads.set(0, yahooThread);
		yahooThread.start();
		
		threadCounter.start();
	}
	
	public void guess() {
		
		if (!notCase) {
			int highestPos = 0;
			for (int i = 0; i < optionPossibility.length; i++) {
				if (optionPossibility[i] > optionPossibility[highestPos]) highestPos = i;
				System.out.println(options.get(i) + " > " + optionPossibility[i]);
			}
		} else {
			int LowestPos = 0;
			for (int i = 0; i < optionPossibility.length; i++) {
				if (optionPossibility[i] < optionPossibility[LowestPos]) LowestPos = i;
				System.out.println(options.get(i) + " > " + optionPossibility[i]);
			}
		}
		
		System.out.println("Time Spent So Far: " + (totalTime / 1000000000) + "\n");
	}
	
	public void finalGuess() {
		
		if (!notCase) {
			int highestPos = 0;
			for (int i = 0; i < optionPossibility.length; i++) {
				if (optionPossibility[i] > optionPossibility[highestPos]) highestPos = i;
			}
			System.out.println("\n\n\n");
			App.sayTranslation(3);
			System.out.println("	" + options.get(highestPos));
		} else {
			int LowestPos = 0;
			for (int i = 0; i < optionPossibility.length; i++) {
				if (optionPossibility[i] < optionPossibility[LowestPos]) LowestPos = i;
			}
			System.out.println("\n\n\n");
			App.sayTranslation(3);
			System.out.println("	" + options.get(LowestPos));
		}
		
		System.out.println("Time Spent: " + (totalTime / 1000000000) + "\n");
	}
	
	
	private String questionParser(String question) {
		
		if (question.contains("NOT")) {
			notCase = true;
			question = question.replace("NOT ", "");
		}
		
		if(question.toLowerCase().contains("which of these ")) {
			question = question.toLowerCase().replace("which of these ", "What");
			if (question.contains(" is ")) question = question.replace(" is ", " are ");
		} else if (question.toLowerCase().contains("which ")) question = question.replace("which", "what");
		
		if(question.contains(", ") && (!question.toLowerCase().startsWith("of") || !question.toLowerCase().startsWith("of") || question.split(", ").length == 3)) {
			question = question.split(", ")[1];
		}
		
		if(question.toLowerCase().startsWith("an ") && question.toLowerCase().contains("is ") && question.toLowerCase().contains("what")) {
			question = question.split("is ")[0] + "is what?";
		}
		if (question.contains("\"")) question = question.replaceAll("\"", "");
		
		if (question.toLowerCase().contains(" most likely ")) question = question.replace(" most likely ", " ");
		
		if (question.toLowerCase().contains(" has ")) question = question.replace(" has ", " ");
		
		return question;
	}
}
