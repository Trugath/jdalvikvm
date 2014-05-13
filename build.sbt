name := "jdalvikvm"

organization := "com.github.trugath"

version := "0.8"

scalaVersion := "2.11.0"

scalacOptions := Seq("-unchecked", "-deprecation", "-feature", "-encoding", "UTF-8")

javacOptions := Seq("-encoding", "UTF-8")

libraryDependencies += "junit" % "junit" % "4.11" % "test"

libraryDependencies += "com.novocode" % "junit-interface" % "0.11-RC1" % "test"

libraryDependencies += "commons-io" % "commons-io" % "2.4" % "test"

libraryDependencies += "commons-lang" % "commons-lang" % "2.6" % "test"