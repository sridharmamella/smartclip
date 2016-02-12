package com.smartclip.app

import com.smartclip.parser._
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


/**
  * Created by sridharmamella on 10-01-2016.
  */
object SmartClipApp {

  def main(args: Array[String]) {

    val inputDir = args(0)
    val outPutDir = args(1)
    //val inputDir = "/Users/sridharmamella/workspace/smartclip.log"
    //val inputDir="C:\\Users\\uma\\Desktop\\smartclip.txt"
    // val outPutDir = ""
    val parser = new LogParser
    var sc = buildSparkContext()
    val logData = sc.textFile(inputDir).cache()
    val logRecords = logData.map(x => parser.parseRecord(x))


    //logRecords.foreach(println)

    calculateUserLoginPerDay(logRecords, outPutDir)

    calculateUserLoginPerHour(logRecords, outPutDir)

    calculatedUserByMf(logRecords, outPutDir)

    calculatedUserByCne(logRecords, outPutDir)

  }

  def buildSparkContext(): SparkContext = {
    val conf = new SparkConf().setAppName("SmartClip")
  //  conf.setMaster("local")
    return new SparkContext(conf)
  }

  def calculateUserLoginPerDay(logRecords: RDD[Option[LogRecord]], opPath: String): Unit = {
    val uniqueUserLogsByDay = logRecords.map(y =>
      (LogParser.parseDatePart(y.get.dateTime),
        UrlParser.paramValue(y.get.request, "uid")
        )
    ).filter(x => x._1 != None).distinct()

    // uniqueUserLogsByDay.foreach(println)

    val userCountByDay = uniqueUserLogsByDay.map(z => (z._1.get, 1)).reduceByKey((p, q) => p + q)

    print("Unique user logins per day")
    if (opPath == "") {
      userCountByDay.foreach(println)
    } else {
      userCountByDay.saveAsTextFile(opPath + "_Daily")
    }
  }


  def calculatedUserByMf(logRecords: RDD[Option[LogRecord]], opPath: String): Unit = {


    val uniqueUserLogsByDay = logRecords.map(y =>
      (UrlParser.paramValue(y.get.request, "mf"),
        UrlParser.paramValue(y.get.request, "uid")
        )
    ).filter(x => x._1.exists(_.trim.nonEmpty)).distinct()

    uniqueUserLogsByDay.foreach(println)

    val userCountByDay = uniqueUserLogsByDay.map(z => (z._1.get, 1)).reduceByKey((p, q) => p + q)

    print("Unique user  per MF")
    if (opPath == "") {
      userCountByDay.foreach(println)
    } else {
      userCountByDay.saveAsTextFile(opPath + "_MF")
    }


  }


  def calculateUserLoginPerHour(logRecords: RDD[Option[LogRecord]], opPath: String): Unit = {
    val uniqueUserLogsPerHour = logRecords.map(y =>
      (LogParser.parseDateAndHourPart(y.get.dateTime),
        UrlParser.paramValue(y.get.request, "uid")
        )
    ).filter(x => x._1 != None).distinct()

    val userCountByPerHour = uniqueUserLogsPerHour.map(z => (z._1.get, 1)).reduceByKey((p, q) => p + q)

    print("Unique user logins per hour")
    if (opPath == "") {
      userCountByPerHour.foreach(println)
    }
    else {
      userCountByPerHour.saveAsTextFile(opPath + "_Hourly")
    }
  }

  def calculatedUserByCne(logRecords: RDD[Option[LogRecord]], opPath: String): Unit = {


    val uniqueUserLogsByDay = logRecords.map(y =>
      (UrlParser.paramValue(y.get.request, "site"),
        UrlParser.paramValue(y.get.request, "uid")
        )
    ).filter(x => x._1.exists(_.trim.nonEmpty)).distinct().map(tuple => (extractChannelName(tuple._1.get), 1))

    // uniqueUserLogsByDay.foreach(println)

    val userCountByDay = uniqueUserLogsByDay.reduceByKey((p, q) => p + q)

    print("Unique user per cne")
    if (opPath == "") {
      userCountByDay.foreach(println)
    } else {
      userCountByDay.saveAsTextFile(opPath + "_cne")
    }


  }

  def extractChannelName(url: String): String = {
    return url.split("\\.")(0)
  }

  def mapToChannelName(name: String): String = {
    if (name.toUpperCase().contains("DISNEY"))
      return "DISNEY"
    return name;
  }

  def printRecord(rec: Option[LogRecord]) {
    println(rec.get.clientIpAddress)
    println(rec.get.rfc1413ClientIdentity)
    println(rec.get.remoteUser)
    println(rec.get.dateTime)
    println(rec.get.request)
    println(rec.get.httpStatusCode)
    println(rec.get.bytesSent)
    println(rec.get.referer)
    println(rec.get.userAgent)
  }

}

