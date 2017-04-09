package com.bf.doc.similarity

import SimilarityUtil._

class SolutionBuilder(docs: List[Doc]) {

  val docCount = docs.size

  val docIdToRelFreqMap: Map[Int, Map[String, Double]] =
    getDocIdToRelativeFrequencyMap(docs)

  val wordToIDFMap: Map[String, Double] =
    getWordToIDFMap(docIdToRelFreqMap, docCount)

  val result: Map[Int, List[(String, Double)]] =
    getDocumentImportanceList(docIdToRelFreqMap, wordToIDFMap)

  displayImportanceList(result)

  val documentIdTDocumentImportanceListMap: Map[Int, List[(String, Double)]] =
    result.toMap

}
