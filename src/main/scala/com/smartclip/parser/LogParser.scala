package com.smartclip.parser

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.regex.{Matcher, Pattern}

import scala.util.control.Exception._

/**
  * A sample record:
  * 94.102.63.11 - - [21/Jul/2009:02:48:13 -0700] "GET / HTTP/1.1" 200 18209 "http://acme.com/foo.php" "Mozilla/4.0 (compatible; MSIE 5.01; Windows NT 5.0)"
  *
  * I put this code in the 'class' so (a) the pattern could be pre-compiled and (b) the user can create
  * multiple instances of this parser, in case they want to work in a multi-threaded way.
  * I don't know that this is necessary, but I think it is for this use case.
  *
  */

@SerialVersionUID(100L)
class LogParser extends Serializable {

  private val ddd = "\\d{1,3}"
  // at least 1 but not more than 3 times (possessive)
  private val ip = s"($ddd\\.$ddd\\.$ddd\\.$ddd)?"
  // like `123.456.7.89`
  private val client = "(\\S+)"
  // '\S' is 'non-whitespace character'
  private val user = "(\\S+)"
  private val dateTime = "(\\[.+?\\])"
  // like `[21/Jul/2009:02:48:13 -0700]`
  private val request = "\"(.*?)\""
  // any number of any character, reluctant
  private val status = "(\\d{3})"
  private val bytes = "(\\S+)"
  // this can be a "-"
  private val referer = "\"(.*?)\""
  private val agent = "\"(.*?)\""
  private val regex = s"$ip $client $user $dateTime $request $status $bytes $referer $agent"
  private val p = Pattern.compile(regex)

  /**
    * note: group(0) is the entire record that was matched (skip it)
    * @param record Assumed to be an Apache access log combined record.
    * @return An LogRecord instance wrapped in an Option.
    */
  def parseRecord(record: String): Option[LogRecord] = {
    val matcher = p.matcher(record)
    if (matcher.find) {
      Some(buildLogRecord(matcher))
    } else {
      None
    }
  }

  /**
    * Same as parseRecord, but returns a "Null Object" version of an LogRecord
    * rather than an Option.
    *
    * @param record Assumed to be an Apache access log combined record.
    * @return An LogRecord instance. This will be a "Null Object" version of an
    *         LogRecord if the parsing process fails. All fields in the Null Object
    *         will be empty strings.
    */
  def parseRecordReturningNullObjectOnFailure(record: String): LogRecord = {
    val matcher = p.matcher(record)
    if (matcher.find) {
      buildLogRecord(matcher)
    } else {
      LogParser.nullObjectLogRecord
    }
  }

  private def buildLogRecord(matcher: Matcher) = {
    LogRecord(
      matcher.group(1),
      matcher.group(2),
      matcher.group(3),
      matcher.group(4),
      matcher.group(5),
      matcher.group(6),
      matcher.group(7),
      matcher.group(8),
      matcher.group(9))
  }
}

/**
  * A sample record:
  * 94.102.63.11 - - [21/Jul/2009:02:48:13 -0700] "GET / HTTP/1.1" 200 18209 "http://acme.com/foo.php" "Mozilla/4.0 (compatible; MSIE 5.01; Windows NT 5.0)"
  */
object LogParser {

  val nullObjectLogRecord = LogRecord("", "", "", "", "", "", "", "", "")

  /**
    * @return A Tuple3(requestType, uri, httpVersion). requestType is GET, POST, etc.
    *
    *         Returns a Tuple3 of three blank strings if the method fails.
    */
  def parseRequestField(request: String): Option[Tuple3[String, String, String]] = {
    val arr = request.split(" ")
    if (arr.size == 3) Some((arr(0), arr(1), arr(2))) else None
  }


  def parseDateField(field: String): Option[java.util.Date] = {
    val dateRegex = "\\[(.*?) .+]"
    val datePattern = Pattern.compile(dateRegex)
    val dateMatcher = datePattern.matcher(field)
    if (dateMatcher.find) {
      val dateString = dateMatcher.group(1)
      println("***** DATE STRING" + dateString)
      // HH is 0-23; kk is 1-24
      val dateFormat = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss", Locale.ENGLISH)
      allCatch.opt(dateFormat.parse(dateString)) // return Option[Date]
    } else {
      None
    }
  }

  def parseDatePart(field: String): Option[java.util.Date] = {
    val dateRegex = "\\[(.*?) .+]"
    val datePattern = Pattern.compile(dateRegex)
    val dateMatcher = datePattern.matcher(field)
    if (dateMatcher.find) {
      val dateString = dateMatcher.group(1)
      //println("***** DATE STRING" + dateString)
      // HH is 0-23; kk is 1-24
      val dateFormat = new SimpleDateFormat("dd/MMM/yyyy", Locale.ENGLISH)
      allCatch.opt(dateFormat.parse(dateString)) // return Option[Date]
    } else {
      None
    }
  }

  def parseDateAndHourPart(field: String): Option[java.util.Date] = {
    val dateRegex = "\\[(.*?) .+]"
    val datePattern = Pattern.compile(dateRegex)
    val dateMatcher = datePattern.matcher(field)
    if (dateMatcher.find) {
      val dateString = dateMatcher.group(1)
      //println("***** DATE STRING" + dateString)
      // HH is 0-23; kk is 1-24
      val dateFormat = new SimpleDateFormat("dd/MMM/yyyy:HH", Locale.ENGLISH)
      allCatch.opt(dateFormat.parse(dateString)) // return Option[Date]
    } else {
      None
    }
  }

}



