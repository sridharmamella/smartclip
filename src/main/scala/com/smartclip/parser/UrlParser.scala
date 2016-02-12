package com.smartclip.parser

import com.netaporter.uri.Uri.parse

/**
  * Created by sridharmamella on 11-01-2016.
  */
object UrlParser {

  def paramValue(url: String, name: String): Option[String] = {

    parse(url).query.param(name)

  }
}
