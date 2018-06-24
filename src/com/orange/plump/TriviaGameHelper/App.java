package com.orange.plump.TriviaGameHelper;

public class App {

	public static String NAME = "Trivia Game Helper", CREATOR = "PlumpOrange", LANGUAGE = "English";

	public static final float VERSION = 1.0f, BUILD = 0.1f;

	public static String[] TRANSLATED_ARRAY;

	public static HelperManager manager;

	public static void main(String[] args) {
		start(args);
	}

	private static void start(String[] args) {
		boolean isLanguageArg = false;
		for (String arg : args)
			if (arg.equalsIgnoreCase("-l"))
				isLanguageArg = true;
			else if (isLanguageArg)
				parseLanguageArg(arg);

		grabTranslation();
		manager = new HelperManager();

		System.out.println(App.NAME + " v" + App.VERSION + "(" + App.BUILD + ")" + " By : " + App.CREATOR + "\n\n");

		manager.start();
	}

	private static void parseLanguageArg(String arg) {
		arg = arg.toLowerCase();

		if (arg.equals("english") || arg.equals("eng"))
			App.LANGUAGE = "English";
		else if (arg.equals("spanish") || arg.equals("spa"))
			App.LANGUAGE = "Spanish";
		else if (arg.equals("french") || arg.equals("fre"))
			App.LANGUAGE = "French";
		else if (arg.equals("german") || arg.equals("ger"))
			App.LANGUAGE = "German";
		else if (arg.equals("dutch") || arg.equals("dut"))
			App.LANGUAGE = "Dutch";
	}

	private static void grabTranslation() {
		if (App.LANGUAGE.equals("English"))
			App.TRANSLATED_ARRAY = Translate.ENGLISH;
		else if (App.LANGUAGE.equals("Spanish"))
			App.TRANSLATED_ARRAY = Translate.SPANISH;
		else if (App.LANGUAGE.equals("French"))
			App.TRANSLATED_ARRAY = Translate.FRENCH;
		else if (App.LANGUAGE.equals("German"))
			App.TRANSLATED_ARRAY = Translate.GERMAN;
		else if (App.LANGUAGE.equals("Dutch"))
			App.TRANSLATED_ARRAY = Translate.DUTCH;
	}

	public static void sayTranslation(int pos, String s) {
		System.out.println(App.TRANSLATED_ARRAY[pos].replace("{NUM}", s));
	}

	public static void sayTranslation(int pos) {
		System.out.println(App.TRANSLATED_ARRAY[pos]);
	}
}
