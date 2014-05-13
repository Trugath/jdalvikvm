logLevel in Global := Level.Debug

resolvers += Classpaths.sbtPluginReleases

addSbtPlugin("org.scoverage" % "sbt-scoverage" % "0.98.4")

addSbtPlugin("org.scoverage" %% "sbt-coveralls" % "0.98.0")