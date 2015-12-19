package com.mproject.mnote.config

import org.scalatest.{Matchers, FlatSpec}
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith

/**
 * Created with IntelliJ IDEA.
 * User: matu
 * Date: 2014/10/22
 * Time: 23:00
 */
@RunWith(classOf[JUnitRunner])
class ConfigTest extends FlatSpec with Matchers {

  val configPath = getClass.getResource("/configTest/test1.properties").getPath
  Config.init(configPath)
  var config = Config.getInstance()

  "read setting" should "just work" in {
    config.dbPath should be("testPath")
    config.dbUser should be("testUser")
    config.dbPassword should be("testPass")
  }

  "readString function" should "return setting value" in {
    config.readString("testKey","defaultVal") should be("testVal")
  }

  "file not found" should "return default value" in {
    Config.init("hoge")
    config.readString("testKey","defaultVal") should be("testVal")
  }

  "key not found" should "return default value" in {
    config.readString("hoge","defaultVal") should be("defaultVal")
  }

  "read backslash" should "just work" in {
    config.readString("backslash","defaultVal") should be("C:\\test")
  }

}
