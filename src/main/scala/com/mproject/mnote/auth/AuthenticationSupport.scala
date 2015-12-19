package com.mproject.mnote.auth

import org.scalatra.ScalatraBase
import org.scalatra.auth.{ScentryConfig, ScentrySupport}

/**
 * Created by matu on 2014/11/29.
 */
trait AuthenticationSupport extends ScalatraBase with ScentrySupport[User] {
  self: ScalatraBase =>

  def authenticated = isAuthenticated
  def notAuthenticated = !isAuthenticated

  protected def fromSession = { case id: String => User(id)  }
  protected def toSession   = { case usr: User => usr.id }

  protected val scentryConfig = new ScentryConfig {}.asInstanceOf[ScentryConfiguration]

  /**
   * If an unauthenticated user attempts to access a route which is protected by Scentry,
   * run the unauthenticated() method on the UserPasswordStrategy.
   */
  override protected def configureScentry = {
    scentry.unauthenticated {
      scentry.strategies("UserPassword").unauthenticated()
    }
  }

  /**
   * Register auth strategies with Scentry. Any controller with this trait mixed in will attempt to
   * progressively use all registered strategies to log the user in, falling back if necessary.
   */
  override protected def registerAuthStrategies = {
    scentry.register("UserPassword", app => new UserPasswordStrategy(app))
    scentry.register("RememberMe", app => new RememberMeStrategy(app))
  }

}