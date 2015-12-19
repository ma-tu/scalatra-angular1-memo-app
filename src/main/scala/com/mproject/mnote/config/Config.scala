package com.mproject.mnote.config

import org.apache.commons.configuration.{ConfigurationException, PropertiesConfiguration}
import org.slf4j.LoggerFactory

/**
 * Created with IntelliJ IDEA.
 * User: matu
 * Date: 2014/10/22
 * Time: 21:29
 */
object Config {
  private val logger = LoggerFactory.getLogger(classOf[Config])
  private var instance: Config = null

  def init(path:String) = {
    instance = new Config(getConfiguration(path))
  }
  def getInstance() = instance

  private def getConfiguration(path:String): Option[PropertiesConfiguration] = {
    try{
      val config = new PropertiesConfiguration(path)
      Some(config)
    }catch{
      case e:ConfigurationException => {
        logger.error("Failed loading the properties file",e)
        None
      }
    }
  }
}

class Config private(underlying:Option[PropertiesConfiguration]) {

  val dbPath = readString("db.path","~/test")
  val dbUser = readString("db.user","sa")
  val dbPassword = readString("db.password","")

  private[config] def readString(key:String,default:String) = {
    underlying match{
      case Some(config) =>
        if (config.containsKey(key))
          config.getString(key)
        else
          default
      case None => default
    }
  }
}
