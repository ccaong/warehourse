package com.ccaong.warehousingmanager.util;

import android.os.PowerManager;
import android.os.PowerManager.WakeLock;

/**
 * 唤醒作用类
 * 
 * @author Administrator
 * 
 */
public class AndroidWakeLock {
	WakeLock wakelock;
	PowerManager pmr;

	public AndroidWakeLock(PowerManager pm) {

		// PowerManager pm = (PowerManager)
		// getSystemService(Context.POWER_SERVICE);
		pmr = pm;
	}

	public void WakeLock() {
		if (wakelock == null) {
			wakelock = pmr.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, this
					.getClass().getCanonicalName());
		}
		wakelock.acquire();
	}

	public void ReleaseWakeLock() {
		if (wakelock != null && wakelock.isHeld()) {
			wakelock.release();
			wakelock = null;
		}
	}
}
