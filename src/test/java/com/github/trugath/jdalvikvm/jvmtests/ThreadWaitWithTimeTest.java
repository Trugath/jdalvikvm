/*
 * Developed by Koji Hisano <koji.hisano@eflow.jp>
 *
 * Copyright (C) 2009 eflow Inc. <http://www.eflow.jp/en/>
 *
 * This file is a part of Android Dalvik VM on Java.
 * http://code.google.com/p/android-dalvik-vm-on-java/
 *
 * This project is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This project is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.github.trugath.jdalvikvm.jvmtests;

public class ThreadWaitWithTimeTest {
	public static void main(String[] args) {
		final Object wait = new Object();
		java.lang.Thread thread = new java.lang.Thread(new Runnable() {
			public void run() {
				synchronized (wait) {
					wait.notify();
				}
			}
		});
		long start = System.currentTimeMillis();
		synchronized (wait) {
			thread.start();
			try {
				wait.wait(10000);
			} catch (InterruptedException e) {
				System.out.println("interrupted");
			}
		}
		if (System.currentTimeMillis() - start < 10000) {
			System.out.println("notified");
		}
		synchronized (wait) {
			try {
				wait.wait(1000, 1000);
			} catch (InterruptedException e) {
				System.out.println("interrupted");
			}
		}
		System.out.println("timeout");
	}
}
