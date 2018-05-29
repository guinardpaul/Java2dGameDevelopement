package gameEngine.graphics;

/**
 * The Class Font.
 */
public class Font {

	/** The chars. */
	private static String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ      " + "0123456789.,:;'\"!?$%()-=+/      ";

	/**
	 * Render.
	 *
	 * @param msg
	 *            the msg
	 * @param screen
	 *            the screen
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param colour
	 *            the colour
	 * @param scale
	 *            the scale
	 */
	public static void render(String msg, Screen screen, int x, int y, int colour, int scale) {
		msg = msg.toUpperCase();

		for (int i = 0; i < msg.length(); i++) {
			int charIndex = chars.indexOf(msg.charAt(i));
			if (charIndex >= 0) {
				screen.render(x + (i * 8), y, charIndex + 30 * 32, colour, 0x00, scale);
			}
		}
	}
}
