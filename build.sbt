import sbt.Keys.{dependencyOverrides, incOptions}
import sbt.Project.projectToRef

name := """scalajs-validation"""
version := "0.2.0-beta.1"


lazy val scalaJsProjects = Seq(sharedJs)


lazy val root = (project in file(".")).enablePlugins(PlayScala)
    .settings(commonSettings)
  .settings(
    libraryDependencies ++= {
      val ngVersion = "2.0.0-rc.4"
      Seq(
        cache,

        //angular2 dependencies
        "org.webjars.npm" % "angular__common" % ngVersion,
        "org.webjars.npm" % "angular__compiler" % ngVersion,
        "org.webjars.npm" % "angular__core" % ngVersion,
        "org.webjars.npm" % "angular__platform-browser-dynamic" % ngVersion,
        "org.webjars.npm" % "angular__platform-browser" % ngVersion,
        "org.webjars.npm" % "angular__http" % ngVersion,
        "org.webjars.npm" % "systemjs" % "0.19.31",
        "org.webjars.npm" % "todomvc-common" % "1.0.2",
        "org.webjars.npm" % "rxjs" % "5.0.0-beta.9",
        "org.webjars.npm" % "es6-promise" % "3.1.2",
        "org.webjars.npm" % "es6-shim" % "0.35.1",
        "org.webjars.npm" % "reflect-metadata" % "0.1.3",
        "org.webjars.npm" % "zone.js" % "0.6.12",
        "org.webjars.npm" % "core-js" % "2.4.0",
        "org.webjars.npm" % "symbol-observable" % "1.0.1",

        "org.webjars.npm" % "typescript" % "2.0.0-dev.20160707",

        //tslint dependency
        "org.webjars.npm" % "tslint-eslint-rules" % "1.2.0",
        "org.webjars.npm" % "codelyzer" % "0.0.19",
        "org.webjars.npm" % "types__jasmine" % "2.2.26-alpha" % "test"

        //test
        //  "org.webjars.npm" % "jasmine-core" % "2.4.1"
      )
    },
    dependencyOverrides += "org.webjars.npm" % "minimatch" % "3.0.0",

    // use the webjars npm directory (target/web/node_modules ) for resolution of module imports of angular2/core etc
    resolveFromWebjarsNodeModulesDir := true,

    // use the combined tslint and eslint rules plus ng2 lint rules
    (rulesDirectories in tslint) := Some(List(
      tslintEslintRulesDir.value //,    disable codelyzer until it supports ts 2.0
      //  ng2LintRulesDir.valuej
    )),
    scalaJSProjects := Seq(sharedJs)


  ).aggregate(projectToRef(sharedJs))
  .dependsOn(sharedJvm)


//lazy val client = (project in file("client"))
//  .settings(commonSettings)
//  .settings(
//  persistLauncher := true,
//  persistLauncher in Test := false,
//  libraryDependencies ++= Seq(
//    "org.scala-js" %%% "scalajs-dom" % "0.8.0"
//  )
//).enablePlugins(ScalaJSPlugin, ScalaJSPlay).
//  dependsOn(sharedJs)

lazy val shared = (crossProject.crossType(CrossType.Pure) in file("shared")).
  settings(commonSettings:_*)
  .jsConfigure(_ enablePlugins ScalaJSPlay)
  .enablePlugins(ScalaJSPlugin)


lazy val sharedJvm = shared.jvm
lazy val sharedJs = shared.js

lazy val commonSettings = Seq(
  scalaVersion := "2.11.8",
  incOptions := incOptions.value.withNameHashing(true),
  updateOptions := updateOptions.value.withCachedResolution(cachedResoluton = true)

)