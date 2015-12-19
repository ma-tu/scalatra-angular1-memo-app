package com.mproject.mnote.servlet

import javax.servlet.http.HttpServletRequest

import com.mproject.mnote.auth.AuthenticationSupport
import org.scalatra.ScalatraServlet
import com.mproject.mnote.db.{Repository, DB}
import org.scalatra.scalate.ScalateSupport

/**
 * Created with IntelliJ IDEA.
 * User: matu
 * Date: 2014/10/10
 * Time: 6:08
 */
class MNoteScalatraServlet() extends ScalatraServlet with ScalateSupport with AuthenticationSupport{

  get("/") {
    if(notAuthenticated)
      redirect("/login")

    contentType = "text/html"
    ssp("main.ssp", "user" -> user.id)
  }

  get("/login") {
    if(notAuthenticated) {
      scentry.authenticate("RememberMe")
    }

    if (authenticated) {
      redirectMainPage(request)
    }

    contentType = "text/html"
    ssp("login.ssp")
  }

  get("/logout") {
    scentry.logout()
    redirectLoginPage()
  }

  private def redirectLoginPage() = redirect("/login")

  private def redirectMainPage(request:HttpServletRequest) = {
    println(request.getContextPath() + "/")
    halt(status = 302, headers = Map("Location" -> (request.getContextPath() + "/")))
  }

  get("/init") {
    DB.using(DB.getConnection()){implicit conn => {
      val repo = new Repository()
      repo.initDB()
    }}
    <p>Initialized.</p>
  }

}
