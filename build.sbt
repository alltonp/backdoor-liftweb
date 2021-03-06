import scala.util.Try


name := "backdoor-liftweb"

organization := "im.mange"

version := Try(sys.env("TRAVIS_BUILD_NUMBER")).map("0.0." + _).getOrElse("1.0-SNAPSHOT")

scalaVersion:= "2.11.7"

//crossScalaVersions := Seq("2.10.4"/*, "2.11.0"*/)

resolvers ++= Seq(
  "Sonatype OSS Releases" at "http://oss.sonatype.org/content/repositories/releases/"
)

libraryDependencies ++= Seq(
  "net.liftweb" %% "lift-webkit" % "[2.6.1,2.7.0]" % "provided",
  "com.twitter"       %% "chill"                  % "0.5.2",
  "io.shaka"          %% "naive-http"             % "48" //72
)

net.virtualvoid.sbt.graph.Plugin.graphSettings

sonatypeSettings

publishTo <<= version { project_version ⇒
  val nexus = "https://oss.sonatype.org/"
  if (project_version.trim.endsWith("SNAPSHOT"))
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

publishMavenStyle := true

publishArtifact in Test := false

homepage := Some(url("https://github.com/alltonp/backdoor-liftweb"))

licenses +=("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0.html"))

credentials += Credentials("Sonatype Nexus Repository Manager", "oss.sonatype.org", System.getenv("SONATYPE_USER"), System.getenv("SONATYPE_PASSWORD"))

pomExtra :=
    <scm>
      <url>git@github.com:alltonp/backdoor-liftweb.git</url>
      <connection>scm:git:git@github.com:alltonp/backdoor-liftweb.git</connection>
    </scm>
    <developers>
      <developer>
        <id>alltonp</id>
      </developer>
    </developers>

