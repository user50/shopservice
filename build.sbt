name := "TestApp"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  "mysql" % "mysql-connector-java" % "5.1.18",
  "postgresql" % "postgresql" % "8.4-702.jdbc4",
  "com.google.inject" % "guice" % "3.0",
  "com.google.inject.extensions" % "guice-assistedinject" % "3.0",
  "com.zaxxer" % "HikariCP" % "1.1.8",
  "org.mockito" % "mockito-all" % "1.8.4",
  "javax.mail" % "mail" % "1.4",
  "org.jsoup" % "jsoup" % "1.7.2",
  "org.mongodb" % "mongo-java-driver" % "2.10.1",
  "org.hibernate" % "hibernate-entitymanager" % "4.3.6.Final",
  "net.snaq" % "dbpool" % "5.1",
  "commons-dbcp" % "commons-dbcp" % "1.4"
)

play.Project.playJavaSettings
