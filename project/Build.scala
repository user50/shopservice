import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

  val appName         = "testApp"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    javaCore,
    javaJdbc,
    javaEbean,
    "mysql" % "mysql-connector-java" % "5.1.18",
    "postgresql" % "postgresql" % "8.4-702.jdbc4",
    "com.google.inject" % "guice" % "3.0",
    "com.google.inject.extensions" % "guice-assistedinject" % "3.0",
    "com.zaxxer" % "HikariCP" % "1.1.8",
    "org.mockito" % "mockito-all" % "1.8.4",
    "javax.mail" % "mail" % "1.4"

  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here
  )

}
