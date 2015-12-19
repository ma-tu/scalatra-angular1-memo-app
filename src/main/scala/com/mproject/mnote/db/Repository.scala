package com.mproject.mnote.db

import java.sql.Connection
import java.text.SimpleDateFormat
import com.mproject.mnote.model.NoteModel

/**
 * Created with IntelliJ IDEA.
 * User: matu
 * Date: 2014/10/23
 * Time: 22:56
 */
class Repository(implicit val conn: Connection) {

  def initDB() = {
    val initSqlList: Seq[Seq[String]] = Seq(
      Seq("DROP ALL OBJECTS"),
      Seq("CREATE TABLE account (account VARCHAR(100), password VARCHAR(256))"),
      Seq("INSERT INTO account (account,password) VALUES (?,?)","test","xp+ioxXJs4C2inMMu4edIQ=="),
      Seq("INSERT INTO account (account,password) VALUES (?,?)","test2","E9ZmmNjFP1qf37CVbvKIFg=="),
      Seq("CREATE TABLE notes (account VARCHAR(100), id VARCHAR(36), title VARCHAR(30), note VARCHAR, updated_on TIMESTAMP)"),
      Seq("INSERT INTO notes (account,id,title,note,updated_on) VALUES (?,?,?,?,sysdate)","test","d699cd7a-f5e6-4576-aa15-ddb75418c066","title2","test_text2"),
      Seq("INSERT INTO notes (account,id,title,note,updated_on) VALUES (?,?,?,?,sysdate)","test","ad5320a3-d45e-45a2-88c6-145623a082e3","title1","test_text1"),
      Seq("INSERT INTO notes (account,id,title,note,updated_on) VALUES (?,?,?,?,sysdate)","test2","3a9c67a5-d11f-4243-859d-ae37c3790d19","title1","test2_text1")
    )

    initSqlList.foreach(sql => DB.executeBindSql(conn, sql.head, sql.tail : _*))
  }

  def getNoteList(account:String):Seq[NoteModel] = {
    val sql = "SELECT account, id, title, note,FORMATDATETIME(updated_on,'yyyy/MM/dd HH:mm:ss') AS updated_on FROM notes WHERE account = ? ORDER by updated_on DESC"
    val result = DB.selectBindSql(conn, sql, account)

    val fmt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
    result.map(row => new NoteModel(row("ACCOUNT"),row("ID"),row("TITLE"),row("NOTE"),"",fmt.parse(row("UPDATED_ON")))).toSeq
  }

  def saveNoteList(changeList:Seq[NoteModel]) = {
    changeList.foreach(note => {
      val sql =
        note.action match{
          case "_new" => {
            Some(Seq("INSERT INTO notes (account,id,title,note,updated_on) values (?,?,?,?,sysdate)", note.account, note.id, note.title, note.note))
          }
          case "_upd" =>
            Some(Seq("UPDATE notes SET title=?, note=?, updated_on=sysdate WHERE account = ? AND id = ?", note.title, note.note, note.account, note.id))
          case "_del" =>
            Some(Seq("DELETE FROM notes WHERE account = ? AND id = ?", note.account, note.id))
          case _ => None
        }
      if (sql.isDefined){
        DB.executeBindSql(conn, sql.get.head, sql.get.tail : _*)
      }
    })
  }

  def getPasswordOfAccount(account:String) = {
    val sql = "SELECT password FROM account WHERE account = ?"
    val result: Seq[Map[String, String]] = DB.selectBindSql(conn, sql, account)
    if (result.size == 1)
      result(0).get("PASSWORD")
    else
      None
  }

  def insertAccount(account:String, password:String) = {
    val found = getPasswordOfAccount(account)
    if (found == None){
      val sql = "INSERT INTO account (account, password) values (?, ?)"
      DB.executeBindSql(conn, sql, account, password)
    }else{
      throw new Exception("Already exists account")
    }
  }

  def changePassword(account:String, password:String) = {
    val found = getPasswordOfAccount(account)
    if (found != None){
      val sql = "UPDATE account SET password = ? WHERE account = ?"
      DB.executeBindSql(conn, sql, password, account)
    }else{
      throw new Exception("Not Exist Account")
    }
  }

  def getUserTables: Seq[String] = {
    val sql = "select TABLE_NAME from information_schema.tables where table_type='TABLE'"
    val result = DB.selectSql(conn, sql)
    result.map(row => row("TABLE_NAME"))
  }

}
