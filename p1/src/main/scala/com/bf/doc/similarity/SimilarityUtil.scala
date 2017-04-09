package com.bf.doc.similarity

import SimilarityUtil._





object SimilarityUtil {
  case class Doc(id: Int, str: String)
  case class DocFreq(id: Int, wordCount: Int, freq: Map[String, Double])
  def calculateDistance(importanceMap1: Map[String, Double], importanceMap2: Map[String, Double]): Double = {
    val dp = getDotProduct(importanceMap1, importanceMap2)
    val length1 = getImportanceVectorLength(importanceMap1)
    val length2 = getImportanceVectorLength(importanceMap1)
    dp/(length1 * length2)
  }

  def getImportanceVectorLength(importanceMap: Map[String, Double]): Double = {

    val sumOfSq =
      importanceMap.map {
        t =>
          t._2 * t._2
      }.sum
    val dist = Math.sqrt(sumOfSq)

    dist
  }

  def getDotProduct(importanceMap1: Map[String, Double], importanceMap2: Map[String, Double]): Double = {
    importanceMap1.map {
      t =>
        val word = t._1
        val importance = t._2
        val importance2Option = importanceMap2.get(word)
        val prod = if (importance2Option.isEmpty) 0.0 else importance2Option.get * importance
        prod

    }.sum
  }

  def getWordToIDFMap(docToRelFrequencyMap: Map[Int, Map[String, Double]], docCount: Int): Map[String, Double] = {
    val list: List[(String, Int)] =
      docToRelFrequencyMap.toList.flatMap {
        t =>
          val did = t._1
          val wordToRelFreqMap = t._2
          wordToRelFreqMap.map{
            t1 =>
              (t1._1, 1)
          }

      }

    val wordToDocCountMap: Map[String, Double] =
      list
        .groupBy(t => t._1)
        .map{
        g =>
          val ratio: Double = (docCount/g._2.size)
          val ratioLog: Double = Math.log10(ratio)
          (g._1, ratio)
      }
        .toMap

    wordToDocCountMap
  }

  def getDocumentImportanceList(docIdToRelFreqMap: Map[Int, Map[String, Double]], wordToIDFMap: Map[String, Double]) = {
    val result =
      docIdToRelFreqMap.map {
        t =>
          val did = t._1
          val sortedImportanceList: List[(String, Double)] = t._2.toList.map {
            t2 =>
              val word = t2._1
              val relFreq = t2._2
              val idf = wordToIDFMap(word)
              val importance = relFreq * idf
              (word, importance)
          }.sortWith {
            (a, b) =>
              a._2 > b._2
          }

          (did, sortedImportanceList)
      }

    result
  }

  def getWordToRelativeFrequencyMap(doc: Doc): Map[String, Double] = {
    val words: Array[String] = doc.str.split("""[\s.]+""").map(_.toLowerCase)
    val map = words.groupBy(w => w).map {
      t =>
        val w = t._1
        val count = t._2.size
        (w, count)
    }
    val docWordCount = map.map(_._2).sum
    val wordToRelativeFrequencyMap =
      map.map {
        t =>
          val w = t._1
          val relFreq = t._2.toDouble/docWordCount
          (w, relFreq)
      }
    wordToRelativeFrequencyMap
  }

  def getDocIdToRelativeFrequencyMap(docs: List[Doc]) = {

    val docIdToRelFreqMap: Map[Int, Map[String, Double]] =
      docs.map {
        doc =>
          val did = doc.id
          val relFreqMap = getWordToRelativeFrequencyMap(doc)
          (did, relFreqMap)
      }.toMap

    docIdToRelFreqMap
  }

  def displayImportanceList(result: Map[Int, List[(String, Double)]]) = {

    result.foreach {
      r =>
        val il = r._2.mkString("\n\t\t", "\n\t\t", "")
        println(s"DocId: ${r._1}, \n\timportanceList: $il")
    }
  }
}