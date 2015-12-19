package com.mproject.mnote.listner

import javax.servlet.{ServletContext, ServletContextEvent, ServletContextListener}

/**
 * Created with IntelliJ IDEA.
 * User: matu
 * Date: 2014/11/22
 * Time: 22:31
 */
class InitializationListener extends ServletContextListener {
  override def contextInitialized(event: ServletContextEvent): Unit = {
    val context: ServletContext = event.getServletContext
  }

  override def contextDestroyed(event: ServletContextEvent): Unit = {}
}
