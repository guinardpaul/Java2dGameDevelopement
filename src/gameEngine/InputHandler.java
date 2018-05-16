package gameEngine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class InputHandler implements KeyListener {

	public InputHandler(Game game) {
		game.addKeyListener(this);
	}

	public class Key {
		private boolean pressed = false;
		private int numTimesPressed = 0;

		public boolean isPressed() {
			return pressed;
		}

		public int getNumTimesPressed() {
			return numTimesPressed;
		}

		public void toggle(boolean isPressed) {
			pressed = isPressed;
			if (isPressed) {
				numTimesPressed++;
			}
		}
	}

	public List<Key> keys = new ArrayList<>();
	public Key up = new Key();
	public Key down = new Key();
	public Key left = new Key();
	public Key right = new Key();

	@Override
	public void keyPressed(KeyEvent e) {
		toggleKey(e.getKeyCode(), true);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		toggleKey(e.getKeyCode(), false);
	}

	public void toggleKey(int keyCode, boolean isPressed) {
		if (keyCode == KeyEvent.VK_Z) {
			up.toggle(isPressed);
		}
		if (keyCode == KeyEvent.VK_S) {
			down.toggle(isPressed);
		}
		if (keyCode == KeyEvent.VK_Q) {
			left.toggle(isPressed);
		}
		if (keyCode == KeyEvent.VK_D) {
			right.toggle(isPressed);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

}
