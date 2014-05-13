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

public class InnerClassTest {
	public static class InnerStatic {
		String print(String message) {
			System.out.println(message);
			return "result";
		}
	}

	public class InnerInstance {
		int sum(int i, int j) {
			return i + j;
		}
	}

	public static void main(String[] args) {
		testInnerStaticClass();
		testInnerClass();
		testAnonymousClass();
	}

	private static void testAnonymousClass() {
		Interface in = new Interface() {
			public String print(String in) {
				System.out.println(in);
				return "out";
			}
		};
		String out = in.print("in");
		System.out.println(out);
	}

	private static void testInnerClass() {
		System.out.println("1 + 2 = " + new InnerClassTest().new InnerInstance().sum(1, 2));
	}

	private static void testInnerStaticClass() {
		InnerStatic test = new InnerStatic();
		String result = test.print("message");
		System.out.println(result);
	}
}
