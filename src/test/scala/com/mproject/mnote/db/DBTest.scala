package com.mproject.mnote.db

import org.scalatest.{Matchers, FlatSpec}
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import com.mproject.mnote.config.Config
import com.mproject.mnote.fixtures.Stub

/**
 * Created with IntelliJ IDEA.
 * User: matu
 * Date: 2014/10/15
 * Time: 23:17
 */

@RunWith(classOf[JUnitRunner])
class DBTest extends FlatSpec with Matchers {

  implicit val config: Config = Stub.testConfig()

  "selectSql" should "just work" in {
    DB.using(DB.getConnection()){implicit conn => {
      val result = DB.selectSql(conn, "SELECT 1 FROM DUAL")
      result.head("1") should be("1")
    }}
  }
}
