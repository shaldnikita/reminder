import Dependencies._

ThisBuild / scalaVersion := "2.13.0"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "ru.shaldnikita"

lazy val usersService = (project in file("users"))
  .dependsOn(reminderWebCore)
  .configs(IntegrationTest)
  .settings(name := "users",
            Defaults.itSettings,
            libraryDependencies ++= (core ++ web ++ database ++ monitoring))

lazy val reminderWebCore = (project in file("reminder-web-core"))
  .configs(IntegrationTest)
  .settings(Defaults.itSettings,
            name := "reminder-web-core",
            libraryDependencies ++= web)
