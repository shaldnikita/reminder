name := "users-service"

libraryDependencies ++= Seq(
  "org.scalamock" %% "scalamock" % "4.3.0" % "it" withSources () withJavadoc (),
  "org.scalatest" %% "scalatest" % "3.0.8" % "it" withSources () withJavadoc ()
)
