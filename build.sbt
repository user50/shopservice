name := "TestApp"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
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
  "org.hibernate" % "hibernate" % "3.2.6.ga",
  "log4j" % "log4j" % "1.2.9",
  "dom4j" % "dom4j" % "1.6.1",
  "org.slf4j" % "slf4j-log4j12" % "1.5.2",
  "javax.transaction" % "jta" % "1.1",
  "com.ibm.icu" % "icu4j" % "2.6.1",
  "commons-logging" % "commons-logging" % "1.0.4",
  "commons-collections" % "commons-collections" % "3.1",
  "cglib" % "cglib-nodep" % "2.1_3"
)

play.Project.playJavaSettings
