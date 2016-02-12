package com.smartclip.parser

/**
  * Created by sridharmamella on 10-01-2016.
  */
object SampleRecord {

  val data =
    """
93.196.113.27 - - [12/Nov/2015:04:20:01 +0000] "GET /?site=sport1.hbbtv.x.de.smartclip&uag=Opera%2F9.80%20XZXLinux%20armv7l%3A%3A%20%20HbbTV%2F1.1.1%20XZX%3A%3A%20Sony%3A%3A%20KDL55W805B%3A%3A%20PKG2.601EUA%3A%3A%202014%3A%3AZXZ%3A%3A%20ZXZ%20Presto%2F2.12.407%20Version%2F12.50&int=0&uid=df23f9e1-90ba-4dfe-8dd4-c9994fb2c6b7&opt=0&ses=d8a96ae0-7b13-4471-983c-d2ef2d8d45f7&uxt=1447301999118&uxt0=&mf=Sony&pp=&yod=2014&scz=&l=en%3A%3Ager%2Cger%3A%3Ager%2Cger&hv=1.1.1&cne=SPORT1&cy=DEU&tpt=133.33.900&rnd=652141113 HTTP/1.1" 200 244 "http://cdn.hbbtv.smartclip.net/loader.html?channel=sport1.hbbtv.x.de.smartclip" "Opera/9.80 (Linux armv7l;  HbbTV/1.1.1 (; Sony; KDL55W805B; PKG2.601EUA; 2014;); ) Presto/2.12.407 Version/12.50"
89.166.165.223 - - [21/Jul/2009:02:48:12 -0700] "GET /favicon.ico HTTP/1.1" 404 970 "-" "Mozilla/5.0 (Windows; U; Windows NT 5.1; de; rv:1.9.0.11) Gecko/2009060215 Firefox/3.0.11"
94.102.63.11 - - [21/Jul/2009:02:48:13 -0700] "GET / HTTP/1.1" 200 18209 "http://www.developer.com/net/vb/article.php/3683331" "Mozilla/4.0 (compatible; MSIE 5.01; Windows NT 5.0)"
124.30.7.162 - - [21/Jul/2009:02:48:13 -0700] "GET /images/tline3.gif HTTP/1.1" 200 79 "http://www.devdaily.com/java/edu/pj/pj010004/pj010004.shtml" "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.0.11) Gecko/2009060215 Firefox/3.0.11 GTB5"
122.165.54.17 - - [21/Jul/2009:02:48:12 -0700] "GET /java/java_oo/ HTTP/1.1" 200 32579 "http://www.google.co.in/search?hl=en&q=OO+with+java+standalone+example&btnG=Search&meta=&aq=f&oq=" "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.0.7) Gecko/2009021910 Firefox/3.0.7"
217.32.108.226 - - [21/Jul/2009:02:48:13 -0700] "GET /blog/post/perl/checking-testing-perl-module-in-inc-include-path/ HTTP/1.1" 200 18417 "http://www.devdaily.com/blog/post/perl/perl-error-cant-locate-module-in-inc/" "Mozilla/5.0 (X11; U; SunOS i86pc; en-US; rv:1.7) Gecko/20070606"
122.165.54.17 - - [21/Jul/2009:02:48:15 -0700] "GET /java/java_oo/java_oo.css HTTP/1.1" 200 1235 "http://www.devdaily.com/java/java_oo/" "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.0.7) Gecko/2009021910 Firefox/3.0.7"
    """.split("\n").filter(_ != "")

  val badRecord =
    """
66.249.70.10 - - [23/Feb/2014:03:21:59 -0700] "GET /blog/post/java/how-load-multiple-spring-context-files-standalone/ HTTP/1.0" 301 - "-" "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)"
    """.split("\n").filter(_ != "")


}
