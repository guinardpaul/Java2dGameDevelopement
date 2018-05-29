package gameEngine.graphics;

/**
 * The Class Colours.
 */
public class Colours {

	/**
	 * Gets the Colours.
	 *
	 * @param colour1
	 *            the colour 1
	 * @param colour2
	 *            the colour 2
	 * @param colour3
	 *            the colour 3
	 * @param colour4
	 *            the colour 4
	 * @return the int
	 */
	public static int get(int colour1, int colour2, int colour3, int colour4) {
		return ((get(colour4) << 24) + (get(colour3) << 16) + (get(colour2) << 8) + (get(colour1)));
	}

	/**
	 * Gets the colour.
	 *
	 * @param colour
	 *            the colour
	 * @return the int
	 */
	private static int get(int colour) {
		if (colour < 0) {
			return 255;
		}
		int r = colour / 100 % 10;
		int g = colour / 10 % 10;
		int b = colour / 1 % 10;

		return (r * 36 + g * 6 + b);
	}

}
