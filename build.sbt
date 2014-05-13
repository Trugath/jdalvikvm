import de.johoop.jacoco4sbt._
import JacocoPlugin._

name := "jdalvikvm"

version := "0.8"

scalaVersion := "2.10.4"

scalacOptions := Seq("-unchecked", "-deprecation", "-feature", "-encoding", "UTF-8")

javacOptions := Seq("-encoding", "UTF-8")

jacoco.settings

libraryDependencies += "junit" % "junit" % "4.10" % "test"

libraryDependencies += "com.novocode" % "junit-interface" % "0.11-RC1" % "test"

libraryDependencies += "commons-io" % "commons-io" % "2.4" % "test"

libraryDependencies += "commons-lang" % "commons-lang" % "2.6" % "test"