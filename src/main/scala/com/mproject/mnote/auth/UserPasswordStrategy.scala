package com.mproject.mnote.auth

import javax.servlet.http.{HttpServletResponse, HttpServletRequest}

import com.mproject.mnote.model.LoginModel
import org.scalatra.ScalatraBase
import org.scalatra.auth.ScentryStrategy

/**
 * Created by matu on 2014/11/29.
 */
class UserPasswordStrategy (protected val app: ScalatraBase)(implicit request: HttpServletRequest, response: HttpServletResponse)
  extends ScentryStrategy[User] {

  override def authenticate()(implicit request: HttpServletRequest, response: HttpServletResponse): Option[User] = {
    val login = request.getAttribute("loginModel").asInstanceOf[LoginModel]
    Authentication.authenticate(login.account, login.password)
  }
}
