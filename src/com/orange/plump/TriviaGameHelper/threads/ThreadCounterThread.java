package com.orange.plump.TriviaGameHelper.threads;

import com.orange.plump.TriviaGameHelper.App;

public class ThreadCounterThread extends Thread {
	
	@SuppressWarnings("deprecation")
	public void run() {
		while(true) {
			try {
				App.manager.totalTime = System.nanoTime() - App.manager.startTime;
				App.manager.guess();
				if (Thread.activeCount() == 2 || App.manager.totalTime / 1000000000 >= App.manager.timeout) {
					for (GoogleSearchThread g : App.manager.gThreads) for (URLThread u : g.urlThreads)
						try {
							u.stop();
						} catch (Exception e) {
							
						}
					for (YahooSearchThread y : App.manager.yThreads) for (URLThread u : y.urlThreads)
						try {
							u.stop();
						} catch (Exception e) {
							
						}
					App.manager.finalGuess();
					App.manager.start();
					return;
				}
				try {
					sleep(500);
				} catch (InterruptedException e) {
					
				}
				App.manager.timeSpent++;
			} catch (Exception e) {

			}
		}
	}
}
