package com.bf.doc.similarity

import com.bf.doc.similarity.SimilarityUtil._

object SimilarityTest extends App{

  val docs = List[Doc](
    Doc(1, "This is a test of my will power. My luck is also good. power to me. "),
    Doc(2, "This is a test of my ability to acquire power"),
    Doc(3, "This is a test of my computer science skills"),
    Doc(4, "This is a test of my interest level in AI sdad adsdad fsdfsf f ssff to"),
    Doc(5, "This is a test of my interest level in AI sdad adsdad fsdfsf f sdsff alos"),
    Doc(6, "This is a test of my ability to acquire loss")
  )

  val solutionBuilder = new SolutionBuilder(docs)


  val importanceMap1: Map[String, Double] = solutionBuilder.documentIdTDocumentImportanceListMap(2).toMap
  val importanceMap2: Map[String, Double] = solutionBuilder.documentIdTDocumentImportanceListMap(6).toMap

  val similarity = SimilarityUtil.calculateDistance(importanceMap1, importanceMap2)

  println(s"Similarity: $similarity")

}
