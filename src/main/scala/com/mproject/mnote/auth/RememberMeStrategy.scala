package com.mproject.mnote.auth

import javax.servlet.http.{HttpServletResponse, HttpServletRequest}
import com.mproject.mnote.model.LoginModel
import org.scalatra.{CookieOptions, ScalatraBase}
import org.scalatra.auth.ScentryStrategy

/**
 * Created by matu on 2014/11/29.
 */
class RememberMeStrategy (protected val app: ScalatraBase)(implicit request: HttpServletRequest, response: HttpServletResponse)
  extends ScentryStrategy[User] {

  val COOKIE_KEY = "rememberMe"
  private val oneWeek = 7 * 24 * 3600

  override def name: String = "RememberMe"

  /***
    * Grab the value of the rememberMe cookie token.
    */
  private def tokenVal = {
    app.cookies.get(COOKIE_KEY) match {
      case Some(token) => token
      case None => ""
    }
  }

  /***
    * Determine whether the strategy should be run for the current request.
    */
  override def isValid(implicit request: HttpServletRequest):Boolean = {
    tokenVal != ""
  }

  /***
    * In a real application, we'd check the cookie's token value against a known hash, probably saved in a
    * datastore, to see if we should accept the cookie's token. Here, we'll just see if it's the one we set
    * earlier ("foobar") and accept it if so.
    */
  override def authenticate()(implicit request: HttpServletRequest, response: HttpServletResponse): Option[User] = {
    val token = tokenVal
    if (token != ""){
      val content = decodeCookie(token)
      if (content.isDefined){
        if (content.get.expiredDate < System.currentTimeMillis()) {
          None
        }else{
          Authentication.authenticate(content.get.account, content.get.password)
        }
      }else{
        None
      }
    }else{
      None
    }
  }

  /**
   * What should happen if the user is currently not authenticated?
   */
  override def unauthenticated()(implicit request: HttpServletRequest, response: HttpServletResponse) {
    app.cookies.delete(COOKIE_KEY)(CookieOptions(path = "/"))
    app.redirect("/login")
  }

  /***
    * After successfully authenticating with either the RememberMeStrategy, or the UserPasswordStrategy with the
    * "remember me" tickbox checked, we set a rememberMe cookie for later use.
    *
    * NB make sure you set a cookie path, or you risk getting weird problems because you've accidentally set
    * more than 1 cookie.
    */
  override def afterAuthenticate(winningStrategy: String, user: User)(implicit request: HttpServletRequest, response: HttpServletResponse) = {
    val login = request.getAttribute("loginModel").asInstanceOf[LoginModel]
    if ((login != null) && (login.rememberMe)){
      val content = CookieContent(login.account, login.password, System.currentTimeMillis() + oneWeek)
      val cookieVal = encodeCookie(content)
      app.cookies.set(COOKIE_KEY, cookieVal)(CookieOptions(maxAge = oneWeek, path = "/"))
    }
  }

  /**
   * Run this code before logout, to clean up any leftover database state and delete the rememberMe token cookie.
   */
  override def beforeLogout(user: User)(implicit request: HttpServletRequest, response: HttpServletResponse) = {
    app.cookies.delete(COOKIE_KEY)(CookieOptions(path = "/"))
  }

  case class CookieContent(account:String, password:String, expiredDate:Long)

  private def encodeCookie(content:CookieContent): String = {
    val passwordCipher = new PasswordAuthCipher()
    val encryptPassword = passwordCipher.encrypt(content.password)
    val cookieVal = List(content.account, encryptPassword, content.expiredDate.toString).mkString(":")
    val cookieCipher = new CookieAuthCipher()
    cookieCipher.encrypt(cookieVal)
  }

  private def decodeCookie(cookeiVal:String): Option[CookieContent] = {
    try{
      val cookieCipher = new CookieAuthCipher()
      val decryptCookie = cookieCipher.decrypt(cookeiVal)
      val contents = decryptCookie.split(":")
      if (contents.length == 3){
        val passwordCipher = new PasswordAuthCipher()
        val decryptPassword = passwordCipher.decrypt(contents(1))
        Some(CookieContent(contents(0),decryptPassword,contents(2).toLong))
      }else{
        None
      }
    }catch{
      case e:Exception => None
    }
  }
}
