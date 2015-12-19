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
class RepositoryTest extends FlatSpec with Matchers {
  implicit val config: Config = Stub.testConfig()

  "test" should "test" in {
    DB.using(DB.getConnection()){implicit conn => {
      val repo = new Repository()
      repo.insertAccount("__test__","__password__")
    }}
  }
   "Repository init" should "just work" in {
     DB.using(DB.getConnection()){implicit conn => {
       val repo = new Repository()
       repo.initDB()
     }}
   }
 }
