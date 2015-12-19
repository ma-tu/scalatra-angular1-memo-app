package com.mproject.mnote.fixtures

import com.mproject.mnote.config.Config

/**
 * Created with IntelliJ IDEA.
 * User: matu
 * Date: 2014/10/24
 * Time: 21:44
 * To change this template use File | Settings | File Templates.
 */
object Stub {
  def testConfig() = {
    val configPath = getClass.getResource("/config.properties").getPath
    Config.init(configPath)
    Config.getInstance()
  }
}
