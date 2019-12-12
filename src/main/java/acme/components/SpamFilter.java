
package acme.components;

public class SpamFilter {

	// Business methods -------------------------------------------------------

	public static boolean spamFilter(final String text, final String spam, final Double threshold) {
		boolean result = false;

		Double contador = 0.0, thresholdNumber = threshold, resultado;

		String[] spamText = spam.split(",");
		String[] splitedText = text.split("\\s+");

		for (String wordFromSpam : spamText) {
			for (String wordFromText : splitedText) {
				if (wordFromText.equals(wordFromSpam)) {
					contador += 1;
				}
			}
		}

		resultado = contador / splitedText.length;

		if (thresholdNumber <= resultado) {
			result = true;
		}

		return result;
	}

}
