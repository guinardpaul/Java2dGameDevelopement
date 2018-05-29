package gameEngine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

/**
 * The Input Handler.
 */
public class InputHandler implements KeyListener {

	/**
	 * Instantiates a new input handler.
	 *
	 * @param game
	 *            the game
	 */
	public InputHandler(Game game) {
		game.addKeyListener(this);
	}

	/**
	 * The Class Key.
	 */
	public class Key {

		/** The pressed. */
		private boolean pressed = false;

		/** The num times pressed. */
		private int numTimesPressed = 0;

		/**
		 * Checks if is pressed.
		 *
		 * @return true, if is pressed
		 */
		public boolean isPressed() {
			return pressed;
		}

		/**
		 * Gets the num times pressed.
		 *
		 * @return the num times pressed
		 */
		public int getNumTimesPressed() {
			return numTimesPressed;
		}

		/**
		 * Toggle.
		 *
		 * @param isPressed
		 *            the is pressed
		 */
		public void toggle(boolean isPressed) {
			pressed = isPressed;
			if (isPressed) {
				numTimesPressed++;
			}
		}
	}

	/** The keys. */
	public List<Key> keys = new ArrayList<>();

	/** The up. */
	public Key up = new Key();

	/** The down. */
	public Key down = new Key();

	/** The left. */
	public Key left = new Key();

	/** The right. */
	public Key right = new Key();

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		toggleKey(e.getKeyCode(), true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		toggleKey(e.getKeyCode(), false);
	}

	/**
	 * Toggle key.
	 *
	 * @param keyCode
	 *            the key code
	 * @param isPressed
	 *            the is pressed
	 */
	public void toggleKey(int keyCode, boolean isPressed) {
		if (keyCode == KeyEvent.VK_Z || keyCode == KeyEvent.VK_UP) {
			up.toggle(isPressed);
		}
		if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
			down.toggle(isPressed);
		}
		if (keyCode == KeyEvent.VK_Q || keyCode == KeyEvent.VK_LEFT) {
			left.toggle(isPressed);
		}
		if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
			right.toggle(isPressed);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyTyped(KeyEvent e) {
	}

}
