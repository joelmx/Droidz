package com.joelgil.droidz;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class MainThread extends Thread {

	// flag to hold game state
	private boolean running;
	private SurfaceHolder surfaceHolder;
	private MainGamePanel gamePanel;
	private static final String TAG = MainThread.class.getSimpleName();

	public MainThread(SurfaceHolder surfaceHolder, MainGamePanel gamePanel) {
		super();
		this.surfaceHolder = surfaceHolder;
		this.gamePanel = gamePanel;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	@Override
	public void run() {
		Canvas canvas;
		Log.d(TAG, "Starting game loop");
		while (running) {
			canvas = null;
			// try locking the canvas for exclusive pixel editing
			// in the surface
			try {
				canvas = this.surfaceHolder.lockCanvas();
				synchronized (surfaceHolder) {
					// update game state
					this.gamePanel.update();
					// render state to the screen
					// draws the canvas on the panel
					this.gamePanel.render(canvas);
				}
			} finally {
				// in case of an exception the surface is not left in
				// an inconsistent state
				if (canvas != null) {
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}	// end finally
		}
	}


}
