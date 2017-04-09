name := "kitchensink"

version := "1.0"

scalaVersion := "2.11.7"

lazy val p1 = project

lazy val p2 = project

lazy val root = (project in file(".")).
    aggregate(p1, p2)    
