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

package com.github.trugath.jdalvikvm;

import java.io.*;
import java.util.*;

import com.github.trugath.jdalvikvm.dvmtests.*;
import com.github.trugath.jdalvikvm.jvmtests.*;


import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import org.apache.commons.io.*;
import org.apache.commons.io.output.NullOutputStream;
import org.apache.commons.lang.SystemUtils;

@RunWith(JUnit4.class)
public class VirtualMachineTest {

	private static final String DX_JAR_FILE_PATH = "lib/dx.jar";

    private static final String TEST_CLASSES_OUTPUT_PATH = "target/scala-2.10/test-classes";
    private static final String SCOVERAGE_TEST_CLASSES_OUTPUT_PATH = "target/scala-2.10/scoverage-test-classes";

    @Test
    public void test0x2C() {
		runByClass(Test0x2C.class);

		assertEquals("0x1", getLine());
		assertEquals("0x10", getLine());
		assertEquals("0x100", getLine());
		assertEquals("0x1000", getLine());
		assertEquals("default", getLine());
	}

    @Test
	public void test0x52to0x5F() {
		runByClass(Test0x52to0x5F.class);

		assertNull(getLine());
	}

    @Test
	public void testTest0x14() {
		runByClass(Test0x14.class);

		assertEquals("" + 0x12345678, getLine());
		assertEquals("" + -0x12345678, getLine());
	}

    @Test
	public void testDouble() {
		runByClass(DoubleTest.class);

		assertEquals("1d + 2d = '3.0'", getLine());

		assertEquals("Values are NaN.", getLine());
		assertEquals("Values are NaN.", getLine());
		assertEquals("0d <= 0d", getLine());
		assertEquals("0d >= 0d", getLine());
		assertEquals("0d < 1d", getLine());
		assertEquals("1d > 0d", getLine());

		assertEquals("(int)1.5d = 1", getLine());
		assertEquals("(long)1.5d = 1", getLine());
		assertEquals("(float)1.5d = 1.5", getLine());
		assertEquals("(double)1 = 1.0", getLine());
		assertEquals("(double)1L = 1.0", getLine());
		assertEquals("(double)1f = 1.0", getLine());

		assertEquals("4.0 + 2.0 = 6.0", getLine());
		assertEquals("4.0 - 2.0 = 2.0", getLine());
		assertEquals("4.0 * 2.0 = 8.0", getLine());
		assertEquals("4.0 / 2.0 = 2.0", getLine());
		assertEquals("4.0 % 2.0 = 0.0", getLine());
		assertEquals("-(4.0) = -4.0", getLine());

		assertEquals("da[0] is '2.0'.", getLine());

		assertEquals("d0 = 0.0", getLine());
		assertEquals("d1 = 1.0", getLine());
		assertEquals("d2 = 2.0", getLine());
		assertEquals("d3 = 3.0", getLine());
		assertEquals("d4 = 4.0", getLine());

		assertEquals("0.0", getLine());
		assertEquals("1.0", getLine());
		assertEquals("2.0", getLine());
	}

    @Test
	public void testFloat() {
		runByClass(FloatTest.class);

		assertEquals("1f + 2f = '3.0'", getLine());

		assertEquals("Values are NaN.", getLine());
		assertEquals("Values are NaN.", getLine());
		assertEquals("0f <= 0f", getLine());
		assertEquals("0f >= 0f", getLine());
		assertEquals("0f < 1f", getLine());
		assertEquals("1f > 0f", getLine());

		assertEquals("(int)1.5f = 1", getLine());
		assertEquals("(long)1.5f = 1", getLine());
		assertEquals("(float)1 = 1.0", getLine());
		assertEquals("(float)1L = 1.0", getLine());

		assertEquals("4.0 + 2.0 = 6.0", getLine());
		assertEquals("4.0 - 2.0 = 2.0", getLine());
		assertEquals("4.0 * 2.0 = 8.0", getLine());
		assertEquals("4.0 / 2.0 = 2.0", getLine());
		assertEquals("4.0 % 2.0 = 0.0", getLine());
		assertEquals("-(4.0) = -4.0", getLine());

		assertEquals("fa[0] is '2.0'.", getLine());

		assertEquals("f0 = 0.0", getLine());
		assertEquals("f1 = 1.0", getLine());
		assertEquals("f2 = 2.0", getLine());
		assertEquals("f3 = 3.0", getLine());
		assertEquals("f4 = 4.0", getLine());

		assertEquals("0.0", getLine());
		assertEquals("1.0", getLine());
		assertEquals("2.0", getLine());
		assertEquals("3.0", getLine());
	}

    @Test
	public void testInheritanceFromObject() {
		runByClass(InheritanceFromObjectTest.class, new Class[] { InheritanceFromObjectTest.Wait.class }, new String[] { InheritanceFromObjectTest.class.getName() + "$1" });

		assertEquals("toString", getLine());

		assertEquals("notified", getLine());
		assertEquals("interrupted", getLine());
	}

    @Test
	public void testThreadWaitWithTime() {
		runByClass(ThreadWaitWithTimeTest.class, new Class[0], new String[] { ThreadWaitWithTimeTest.class.getName() + "$1" });

		assertEquals("notified", getLine());
		assertEquals("timeout", getLine());
	}

    @Test
	public void testThreadNotifyAll() {
		runByClass(ThreadNotifyAllTest.class, new Class[0], new String[] { ThreadNotifyAllTest.class.getName() + "$1" });

		for (int i = 0; i < 10; i++) {
			assertEquals("" + i, getLine());
		}
	}

    @Test
	public void testThreadWait() {
		runByClass(ThreadWaitTest.class, new Class[0], new String[] { ThreadWaitTest.class.getName() + "$1" });

		assertEquals("notified", getLine());
		assertEquals("interrupted", getLine());
	}

    @Test
	public void testThreadReentrantSynchronizedMethod() {
		runByClass(ThreadReentrantSynchronizedMethodTest.class, new Class[0], new String[] { ThreadReentrantSynchronizedMethodTest.class.getName() + "$1" });

		assertEquals("1", getLine());
		assertEquals("2", getLine());
	}

    @Test
	public void testThreadSynchronizedMethod() {
		runByClass(ThreadSynchronizedMethodTest.class, new Class[0], new String[] { ThreadSynchronizedMethodTest.class.getName() + "$1" });

		assertEquals("1", getLine());
		assertEquals("2", getLine());
	}

    @Test
	public void testThreadReentrantSynchronizedBlock() {
		runByClass(ThreadReentrantSynchronizedBlockTest.class, new Class[0], new String[] { ThreadReentrantSynchronizedBlockTest.class.getName() + "$1" });

		assertEquals("1", getLine());
		assertEquals("2", getLine());
	}

    @Test
	public void testThreadSynchronized() {
		runByClass(ThreadSynchronizedBlockTest.class, new Class[0], new String[] { ThreadSynchronizedBlockTest.class.getName() + "$1" });

		assertEquals("1", getLine());
		assertEquals("2", getLine());
	}

    @Test
	public void testThreadInterrupt() {
		runByClass(ThreadInterruptTest.class, new Class[0], new String[] { ThreadInterruptTest.class.getName() + "$1" });

		assertEquals("sleep->interrupted", getLine());
		assertEquals("join->interrupted", getLine());
	}

    @Test
	public void testThreadSleep() {
		runByClass(ThreadSleepTest.class);

		assertEquals("slept", getLine());
	}

    @Test
	public void testThreadYield() {
		runByClass(ThreadYieldTest.class, new Class[0], new String[] { ThreadYieldTest.class.getName() + "$1" });

		assertEquals("new", getLine());
		assertEquals("main", getLine());
	}

    @Test
	public void testThreadMethods() {
		runByClass(ThreadMethodsTest.class, new Class[0], new String[] { ThreadMethodsTest.class.getName() + "$1" });

		assertEquals("activeCount = 2", getLine());
		assertEquals("newThreadName = new", getLine());
		assertEquals("mainThreadName = main", getLine());
	}

    @Test
	public void testSimpleThread() {
		runByClass(ThreadSimpleTest.class, new Class[0], new String[] { ThreadSimpleTest.class.getName() + "$1" });

		assertEquals("newThread", getLine());
		assertEquals("mainThread", getLine());
	}

    @Test
	public void testNativeInterface() {
		runByClass(NativeInterface.class, new Class[0], new String[] { NativeInterface.class.getName() + "$1" });

		assertEquals("message", getLine());
	}

    @Test
	public void testInnerClass() {
		runByClass(InnerClassTest.class, new Class[] { InnerClassTest.InnerStatic.class, InnerClassTest.InnerInstance.class, Interface.class }, new String[] { InnerClassTest.class.getName() + "$1" });

		assertEquals("message", getLine());
		assertEquals("result", getLine());

		assertEquals("1 + 2 = 3", getLine());

		assertEquals("in", getLine());
		assertEquals("out", getLine());
	}

    @Test
	public void testUserDefinedInterface() {
		runByClass(UserDefinedInterfaceTest.class, new Class[] { Interface.class, Implementation.class });

		assertEquals("message", getLine());
		assertEquals("result", getLine());
	}

    @Test
	public void testInheritance() {
		runByClass(InheritanceTest.class, new Class[] { ParentClass.class, ChildClass.class });

		assertEquals("parent", getLine());
		assertEquals("child_hidden", getLine());
		assertEquals("parent_hidden", getLine());

		assertEquals("parent", getLine());
		assertEquals("child_hidden", getLine());
	}

    @Test
	public void testInstance() {
		runByClass(InstanceTest.class);
		assertEquals("public_method", getLine());
		assertEquals("protected_method", getLine());
		assertEquals("package_method", getLine());
		assertEquals("private_method", getLine());
		assertEquals("get_field", getLine());
		assertEquals("set_field", getLine());
	}

    @Test
	public void testOtherClass() {
		runByClass(OtherClassTest.class, new Class[] { OtherClass.class });
		assertEquals("method", getLine());
		assertEquals("get_field", getLine());
		assertEquals("set_field", getLine());
	}

    @Test
	public void testBug23() throws Exception {
		long start = System.currentTimeMillis();
		runByClass(Bug23Test.class);
		long end = System.currentTimeMillis();
		long result = Long.parseLong(getLine());
		assertTrue(start <= result && result <= end);
	}

    @Test
	public void testBug22() throws Exception {
		runByClass(Bug22Test.class);
		assertEquals("test", getLine());
	}

    @Test
	public void testBug17And20() {
		runByClass(Bug17And20Test.class);
		assertEquals("ClassCastException occured.", getLine());
		assertEquals("The null can be casted.", getLine());
	}

    @Test
	public void testBug16() {
		runByClass(Bug16Test.class);
		assertEquals("l = 1, j = 1", getLine());
	}

    @Test
	public void testBug13() {
		runByClass(Bug13Test.class);
	}

    @Test
	public void testBug12() {
		runByClass(Bug12Test.class);
		assertEquals("test", getLine());
		assertEquals("hello, world", getLine());
	}

    @Test
	public void testBug10And11() {
		runByClass(Bug10And11Test.class);
		assertEquals("java.lang.RuntimeException", getLine());
	}

    @Test
	public void testBug9() {
		runByClass(Bug9Test.class);
		assertEquals("SOFTKEY_SK2", getLine());
	}

    @Test
	public void testInterface() {
		runByClass(InterfaceTest.class);
		assertEquals("1", getLine());
		assertEquals("2", getLine());
	}

    @Test
	public void testArrayLength() {
		runByClass(ArrayLengthTest.class);
		String[] array = new String[] { "boolean", "byte", "short", "int", "long", "Object", "String", "Vector" };
		for (int i = 0; i < array.length; i++) {
			assertEquals("new " + array[i] + "[" + i + "].length = " + i, getLine());
			assertEquals("new " + array[i] + "[" + i + "][].length = " + i, getLine());
			assertEquals("new " + array[i] + "[" + i + "][" + i + "].length = " + i, getLine());
			assertEquals("new " + array[i] + "[" + i + "][][].length = " + i, getLine());
			assertEquals("new " + array[i] + "[" + i + "][" + i + "][].length = " + i, getLine());
			assertEquals("new " + array[i] + "[" + i + "][" + i + "][" + i + "].length = " + i, getLine());
		}
	}

    @Test
	public void testCallMethods() {
		runByClass(CallMethodsTest.class);
		assertEquals("��Ȃ�", getLine());

		assertEquals("false", getLine());
		assertEquals("true", getLine());

		assertEquals("true", getLine());
		assertEquals("false", getLine());

		assertEquals("a", getLine());
		assertEquals("b", getLine());

		assertEquals("1", getLine());
		assertEquals("2", getLine());

		assertEquals("2", getLine());
		assertEquals("3", getLine());

		assertEquals("3", getLine());
		assertEquals("4", getLine());

		assertEquals("4", getLine());
		assertEquals("5", getLine());

		assertEquals("�e�X�g", getLine());
		assertEquals("�e�X�g�e�X�g", getLine());

		assertEquals("false,a,1,2,3,4,�e�X�g", getLine());
		assertEquals("�e�X�g,4,3,2,1,a,false", getLine());

		assertEquals("1,2,3", getLine());
	}

    @Test
	public void testCallSuperClassMethod() {
		runByClass(CallSuperClassMethodTest.class);
		assertEquals("java.lang.String", getLine());
	}

    @Test
	public void testCreateException() {
		runByClass(CreateExceptionTest.class);
		assertEquals("��O������", getLine());
	}

    @Test
	public void testInstanceOf() {
		runByClass(InstanceOfTest.class);
		assertEquals("The str is a String.", getLine());
		assertEquals("The str is a Object.", getLine());
		assertEquals("The null is not a Object.", getLine());
	}

    @Test
	public void testParentException() {
		runByClass(ParentExceptionTest.class);
		assertEquals("catch�u���b�N�ʉ�", getLine());
		assertEquals("finally�u���b�N�ʉ�", getLine());
	}

    @Test
	public void testClassMethodExceptionInCalledMethod() {
		runByClass(ClassMethodExceptionInCalledMethodTest.class);
		assertEquals("catch�u���b�N�ʉ�", getLine());
		assertEquals("finally�u���b�N�ʉ�", getLine());
	}

    @Test
	public void testConstructorExceptionInCalledMethod() {
		runByClass(ConstructorExceptionInCalledMethodTest.class);
		assertEquals("catch�u���b�N�ʉ�", getLine());
		assertEquals("finally�u���b�N�ʉ�", getLine());
	}

    @Test
	public void testInstanceMethodExceptionInCalledMethod() {
		runByClass(InstanceMethodExceptionInCalledMethodTest.class);
		assertEquals("catch�u���b�N�ʉ�", getLine());
		assertEquals("finally�u���b�N�ʉ�", getLine());
	}

    @Test
	public void testClassMethodException() {
		runByClass(ClassMethodExceptionTest.class);
		assertEquals("catch�u���b�N�ʉ�", getLine());
		assertEquals("finally�u���b�N�ʉ�", getLine());
	}

    @Test
	public void testConstructorException() {
		runByClass(ConstructorExceptionTest.class);
		assertEquals("catch�u���b�N�ʉ�", getLine());
		assertEquals("finally�u���b�N�ʉ�", getLine());
	}

    @Test
	public void testInstanceMethodException() {
		runByClass(InstanceMethodExceptionTest.class);
		assertEquals("catch�u���b�N�ʉ�", getLine());
		assertEquals("finally�u���b�N�ʉ�", getLine());
	}

    @Test
	public void testStaticFields() {
		runByClass(StaticFieldsTest.class);
		assertNull(getLine());
	}

    @Test
	public void testArray() {
		runByClass(ArrayTest.class);
		assertEquals("��������", getLine());
		assertEquals("<NUM VAL=1>", getLine());
		assertEquals("<NUM VAL=2>", getLine());
		assertEquals("<NUM VAL=3>", getLine());
		assertEquals("<NUM VAL=4>", getLine());
		assertEquals("<NUM VAL=5>", getLine());
		assertEquals("�Ă���", getLine());

		assertEquals("��������", getLine());
		assertEquals("<NUM VAL=1>", getLine());
		assertEquals("<NUM VAL=2>", getLine());
		assertEquals("<NUM VAL=3>", getLine());
		assertEquals("<NUM VAL=4>", getLine());
		assertEquals("<NUM VAL=5>", getLine());
		assertEquals("�Ă���", getLine());

		assertEquals("��������", getLine());
		assertEquals("<NUM VAL=1>", getLine());
		assertEquals("<NUM VAL=2>", getLine());
		assertEquals("<NUM VAL=3>", getLine());
		assertEquals("<NUM VAL=4>", getLine());
		assertEquals("<NUM VAL=5>", getLine());
		assertEquals("�Ă���", getLine());
	}

    @Test
	public void testOperatorsForLongType() {
		runByClass(OperatorsForLongType.class);
		long value = 8;
		assertEquals("<NUM VAL=" + (value + 2) + ">", getLine());
		assertEquals("<NUM VAL=" + (value - 2) + ">", getLine());
		assertEquals("<NUM VAL=" + (value * 2) + ">", getLine());
		assertEquals("<NUM VAL=" + (value / 2) + ">", getLine());
		assertEquals("<NUM VAL=" + (value % 2) + ">", getLine());
		assertEquals("<NUM VAL=" + (-value) + ">", getLine());
		assertEquals("<NUM VAL=" + (value << 2) + ">", getLine());
		assertEquals("<NUM VAL=" + (value >> 2) + ">", getLine());
		assertEquals("<NUM VAL=" + (value >>> 2) + ">", getLine());
		assertEquals("<NUM VAL=" + (value & 2) + ">", getLine());
		assertEquals("<NUM VAL=" + (value | 2) + ">", getLine());
		assertEquals("<NUM VAL=" + (value ^ 2) + ">", getLine());
		value++;
		assertEquals("<NUM VAL=" + value + ">", getLine());
	}

    @Test
	public void testLongConstants() {
		runByClass(LongConstantsTest.class);
		for (int i = 1; i <= 3; i++) {
			assertEquals("<NUM VAL=" + i + ">", getLine());
		}
	}

    @Test
	public void testWideByteCode() {
		runByClass(WideByteCodeTest.class);
		assertEquals("<NUM VAL=1000>", getLine());
	}

    @Test
	public void testCast() {
		runByClass(CastTest.class);
		assertEquals("<NUM VAL=-1>", getLine());
		assertEquals("<NUM VAL=-1>", getLine());
		assertEquals("<NUM VAL=65535>", getLine());
		assertEquals("<NUM VAL=-1>", getLine());
		assertEquals("<NUM VAL=-1>", getLine());
	}

    @Test
	public void testConditions() {
		runByClass(ConditionsTest.class);

		assertEquals("����܂�", getLine());
		assertEquals("��������", getLine());
		assertEquals("��������", getLine());
		assertEquals("����܂�", getLine());
		assertEquals("��������", getLine());
		assertEquals("����܂�", getLine());

		assertEquals("��������", getLine());
		assertEquals("����܂�", getLine());
		assertEquals("����܂�", getLine());
		assertEquals("��������", getLine());
		assertEquals("��������", getLine());
		assertEquals("����܂�", getLine());

		assertEquals("����܂�", getLine());
		assertEquals("��������", getLine());

		assertEquals("��������", getLine());
		assertEquals("����܂�", getLine());
	}

    @Test
	public void testOperatorsForIntType() {
		runByClass(OperatorsForIntTypeTest.class);
		int value = 8;
		assertEquals("<NUM VAL=" + (value + 2) + ">", getLine());
		assertEquals("<NUM VAL=" + (value - 2) + ">", getLine());
		assertEquals("<NUM VAL=" + (value * 2) + ">", getLine());
		assertEquals("<NUM VAL=" + (value / 2) + ">", getLine());
		assertEquals("<NUM VAL=" + (value % 2) + ">", getLine());
		assertEquals("<NUM VAL=" + (-value) + ">", getLine());
		assertEquals("<NUM VAL=" + (value << 2) + ">", getLine());
		assertEquals("<NUM VAL=" + (value >> 2) + ">", getLine());
		assertEquals("<NUM VAL=" + (value >>> 2) + ">", getLine());
		assertEquals("<NUM VAL=" + (value & 2) + ">", getLine());
		assertEquals("<NUM VAL=" + (value | 2) + ">", getLine());
		assertEquals("<NUM VAL=" + (value ^ 2) + ">", getLine());
		value++;
		assertEquals("<NUM VAL=" + value + ">", getLine());
	}

    @Test
	public void testPopByteCode() {
		runByClass(PopByteCodeTest.class);
		assertEquals("�Ă���", getLine());
	}

    @Test
	public void testAloadAstoreByteCode() {
		runByClass(AloadAstoreByteCodeTest.class);
		assertEquals("<NUM VAL=4321>", getLine());
	}

    @Test
	public void testIloadIstoreByteCode() {
		runByClass(IloadIstoreByteCodeTest.class);
		assertEquals("<NUM VAL=4321>", getLine());
	}

    @Test
	public void testIntConstants() {
		runByClass(IntConstantsTest.class);
		for (int i = -1; i <= 6; i++) {
			assertEquals("<NUM VAL=" + i + ">", getLine());
		}
		assertEquals("<NUM VAL=" + 0x100 + ">", getLine());
		assertEquals("<NUM VAL=" + 0x10000 + ">", getLine());
	}

    @Test
	public void testNull() {
		runByClass(NullTest.class);
		assertEquals("�Ă���", getLine());
		assertEquals(null, getLine());
	}

    @Test
	public void testHello() {
		runByClass(HelloTest.class);
		assertEquals("����ɂ���", getLine());
	}

	private List lines = new ArrayList();

	private void runByClass(Class mainClass) {
		runByClass(mainClass, new Class[0]);
	}

	private void runByClass(Class mainClass, Class[] relatedClasses) {
		runByClass(mainClass, relatedClasses, new String[0]);
	}

	private void runByClass(Class mainClass, Class[] relatedClasses, String[] relatedClassNames) {
		String[] files = new String[1 + relatedClasses.length + relatedClassNames.length];
		files[0] = toClassFilePath(mainClass);
		for (int i = 0; i < relatedClasses.length; i++) {
			files[1 + i] = toClassFilePath(relatedClasses[i]);
		}
		for (int i = 0; i < relatedClassNames.length; i++) {
			files[1 + relatedClasses.length + i] = toClassFilePath(relatedClassNames[i]);
		}
		byte[] dexFileContent = compile(getClassFilesRootDirectory(), files);
		run(dexFileContent, mainClass.getName(), false);
	}

	private File getClassFilesRootDirectory() {
        if(new File(TEST_CLASSES_OUTPUT_PATH).exists())
            return new File(TEST_CLASSES_OUTPUT_PATH).getAbsoluteFile();
        else
            return new File(SCOVERAGE_TEST_CLASSES_OUTPUT_PATH).getAbsoluteFile();
	}

	private String toClassFilePath(Class clazz) {
		return toClassFilePath(clazz.getName());
	}

	private String toClassFilePath(String className) {
		return className.replace(".", File.separator) + ".class";
	}

	private byte[] compile(File classesDirectory, String[] files) {
		String[] cmdarray = new String[files.length + 5];
		cmdarray[0] = "java";
		cmdarray[1] = "-jar";
		cmdarray[2] = new File(DX_JAR_FILE_PATH).getAbsolutePath();
		cmdarray[3] = "--dex";
		String dexFilePath = new File(SystemUtils.JAVA_IO_TMPDIR, FilenameUtils.getBaseName(files[0]) + ".dex").getAbsolutePath();
		cmdarray[4] = "--output=" + dexFilePath;
		System.arraycopy(files, 0, cmdarray, 5, files.length);

		try {
			Process process = Runtime.getRuntime().exec(cmdarray, null, classesDirectory);
			process.waitFor();
		} catch (Exception e) {
			throw new IllegalStateException("Compiling failed.", e);
		}

		FileInputStream in = null;
		try {
			in = new FileInputStream(dexFilePath);
			return IOUtils.toByteArray(in);
		} catch (IOException e) {
			throw new IllegalStateException("Loading " + dexFilePath + " failed.", e);
		} finally {
			IOUtils.closeQuietly(in);
		}
	}

	private VirtualMachine run(final byte[] dexFileContent, final String mainClassName, final boolean runInNewThread) {
		lines.clear();

		final PrintStream oldErr = System.err;
		final PrintStream newOutAndErr = new PrintStream(new NullOutputStream()) {
			public synchronized void println(Object o) {
				println("" + o);
			}

			public void println(int x) {
				println("" + x);
			}

			public void println(long l) {
				println("" + l);
			}

			public void println(float f) {
				println("" + f);
			}

			public void println(double d) {
				println("" + d);
			}

			public synchronized void println(String x) {
				lines.add(x);
			}
		};
		System.setOut(newOutAndErr);
		System.setErr(newOutAndErr);

		final VirtualMachine vm = new VirtualMachine() {
			protected void error(Throwable e) {
				System.setErr(oldErr);
				e.printStackTrace();
				System.setErr(newOutAndErr);
			}
		};
		vm.load(dexFileContent);
		if (runInNewThread) {
			new java.lang.Thread() {
				public void run() {
					vm.run(mainClassName, new String[0]);
				}
			}.start();
		} else {
			vm.run(mainClassName, new String[0]);
		}

		return vm;
	}

	private String getLine() {
		if (lines.isEmpty()) {
			return null;
		}
		return (String)lines.remove(0);
	}
}
