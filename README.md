jdalvikvm
=========

[![Build Status](https://travis-ci.org/Trugath/jdalvikvm.svg?branch=master)](https://travis-ci.org/Trugath/jdalvikvm)
[![Project Status](http://stillmaintained.com/Trugath/jdalvikvm.png)](http://stillmaintained.com/Trugath/jdalvikvm)

Fork of android-dalvik-vm-on-java (https://code.google.com/p/android-dalvik-vm-on-java)

'Pure' Java implementation of the Dalvik Virtual Machine.
Allows you to load and execute dex files using the JVM

Usage
=====

See: [jdalvikvm-sample](https://github.com/Trugath/jdalvikvm-sample)
```Java
  final byte[] dexByteCode = ... Loaded from dex file ...;
  final String absoluteMainClassName = "tutorial1.HelloWorld";
  final VirtualMachine vm = new VirtualMachine();
  vm.load(dexByteCode);
  vm.run(absoluteMainClassName, new String[0]);
```
Building
========

Repository is setup using SBT and should build and test with SBT

Testing
=======

lib\dx.jar is included for testing the vm and is not required for the use of this DVM
