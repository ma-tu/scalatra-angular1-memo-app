package com.mproject.mnote.servlet

import com.mproject.mnote.auth.{AuthenticationSupport}
import com.mproject.mnote.model.{PasswordModel, RegisterModel, LoginModel}
import com.mproject.mnote.service.LoginApiService
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.ScalatraServlet
import org.scalatra.json.JacksonJsonSupport

/**
 * Created by matu on 2014/12/04.
 */
class LoginApiServlet() extends ScalatraServlet with JacksonJsonSupport with AuthenticationSupport{

  protected implicit val jsonFormats: Formats = DefaultFormats.withBigDecimal

  case class result(result:Boolean, message:String)

  post("/login") {
    contentType = formats("json")
    val login = parsedBody.extract[LoginModel]

    request.setAttribute("loginModel",login)
    scentry.authenticate()

    if (authenticated) {
      result(true, "")
    }else {
      result(false, "ログインに失敗しました")
    }
  }

  post("/register") {
    contentType = formats("json")
    val register = parsedBody.extract[RegisterModel]

    try{
      val service = new LoginApiService()
      service.register(register)
      result(true, "")
    }catch{
      case e:Exception =>
        result(false, "アカウントの登録に失敗しました")
    }
  }

  post("/changepassword") {
    contentType = formats("json")
    val newPassword = parsedBody.extract[PasswordModel]

    try{
      println(newPassword.password);
      val service = new LoginApiService()
      service.changePassword(user.id, newPassword.password)
      result(true, "")
    }catch{
      case e:Exception =>
        result(false, "パスワードの変更に失敗しました")
    }
  }
}
