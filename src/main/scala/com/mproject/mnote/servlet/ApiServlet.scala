package com.mproject.mnote.servlet

import com.mproject.mnote.auth.AuthenticationSupport
import org.scalatra.ScalatraServlet
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.json.JacksonJsonSupport
import com.mproject.mnote.db.{Repository, DB}
import java.util.UUID
import com.mproject.mnote.model.NoteModel

/**
 * Created with IntelliJ IDEA.
 * User: matu
 * Date: 2014/10/10
 * Time: 6:08
 */
class ApiServlet() extends ScalatraServlet with JacksonJsonSupport with AuthenticationSupport{

  protected implicit val jsonFormats: Formats = DefaultFormats.withBigDecimal

  before() {
    if(notAuthenticated)
      halt(403)
  }

  post("/list") {
    contentType = formats("json")
    DB.using(DB.getConnection()){implicit conn => {
      val repo = new Repository()
      repo.getNoteList(user.id)
    }}
  }

  case class NewId(id:String)

  post("/newid") {
    contentType = formats("json")
    NewId(UUID.randomUUID().toString)
  }

  post("/save") {
    contentType = formats("json")
    val changeList = parsedBody.extract[Seq[NoteModel]]

    val saveItem = changeList.map(note => note.copy(account = user.id))
    DB.using(DB.getConnection()){implicit conn => {
      val repo = new Repository()
      repo.saveNoteList(saveItem)
    }}
  }
}
