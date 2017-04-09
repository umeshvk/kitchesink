name := "kitchensink"

version := "1.0"

scalaVersion := "2.11.7"

lazy val docSimilarity = project

lazy val p2 = project

lazy val root = (project in file(".")).
    aggregate(docSimilarity, p2)    
