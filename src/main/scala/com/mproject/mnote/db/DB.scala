package com.mproject.mnote.db

import java.sql._
import scala.util.control.Exception._
import com.mproject.mnote.config.Config

/**
 * Created with IntelliJ IDEA.
 * User: matu
 * Date: 2014/10/15
 * Time: 22:55
 */
object DB {
  def getConnection(): Connection = {
    val config = Config.getInstance()
    Class.forName("org.h2.Driver")
    DriverManager.getConnection("jdbc:h2:" + config.dbPath, config.dbUser, config.dbPassword)
  }

  def selectSql(conn:Connection, sql:String) : Seq[Map[String,String]] = {
    using(conn.createStatement){ st =>
      using(st.executeQuery(sql)){ rs =>
        val rows = new collection.mutable.ListBuffer[Map[String,String]]
        while(rs.next()) {
          rows +=
            (1 to rs.getMetaData.getColumnCount).map(n =>
              (rs.getMetaData.getColumnName(n), rs.getString(n))
            ).toMap
        }
        rows
      }
    }
  }

  def selectBindSql(conn:Connection, sql:String, params: Any*) : Seq[Map[String,String]] = {
    using(conn.prepareStatement(sql)){ st =>
      setParams(st, params: _*)
      using(st.executeQuery()){ rs =>
        val rows = new collection.mutable.ListBuffer[Map[String,String]]
        while(rs.next()) {
          rows +=
            (1 to rs.getMetaData.getColumnCount).map(n =>
              (rs.getMetaData.getColumnName(n), rs.getString(n))
            ).toMap
        }
        rows
      }
    }
  }

  def executeSql(conn:Connection, sql:String): Int = {
    using(conn.createStatement){ st =>
      st.executeUpdate(sql)
    }
  }

  def executeBindSql(conn:Connection, sql:String, params: Any*): Int = {
    using(conn.prepareStatement(sql)){ st =>
      setParams(st, params: _*)
      st.executeUpdate()
    }
  }

  private def setParams(st: PreparedStatement, params: Any*): Unit = {
    params.zipWithIndex.foreach { case (param, i) =>
      param match {
        case x: String  => st.setString(i + 1, x)
        case x: Int     => st.setInt(i + 1, x)
      }
    }
  }

  def using[A <% { def close() }, B](resource: A)(func: A => B): B =
    try
      func(resource)
    finally {
      if(resource != null){
        ignoring(classOf[Throwable]) {
          resource.close()
        }
      }
    }
}
