import Dependencies._

ThisBuild / scalaVersion := "2.13.0"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "ru.shaldnikita"

lazy val usersService = (project in file("users-service"))
  .dependsOn(reminderCore, reminderWebCore)
  .configs(IntegrationTest)
  .settings(name := "users-service",
            Defaults.itSettings,
            libraryDependencies ++= (core ++ web ++ database))

lazy val reminderWebCore = (project in file("reminder-web-core"))
  .dependsOn(reminderCore)
  .configs(IntegrationTest)
  .settings(Defaults.itSettings,
            name := "reminder-web-core",
            libraryDependencies ++= web)

lazy val reminderCore = (project in file("reminder-core"))
  .configs(IntegrationTest)
  .settings(Defaults.itSettings)
  .settings(name := "reminder-core", libraryDependencies ++= core)
