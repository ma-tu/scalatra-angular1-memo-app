import com.mproject.mnote.config.Config
import com.mproject.mnote.servlet.{LoginApiServlet, MNoteScalatraServlet, ApiServlet}
import javax.servlet.ServletContext
import org.scalatra.LifeCycle

/**
 * Created with IntelliJ IDEA.
 * User: matu
 * Date: 2014/10/25
 * Time: 6:14
 */
class ScalatraBootstrap extends LifeCycle {

  override def init(context: ServletContext) {
    val configPath = System.getProperty("catalina.base","") + "/conf/mnote.properties"
    Config.init(configPath)

    context mount (new MNoteScalatraServlet(), "/*")
    context mount (new ApiServlet(), "/api/*")
    context mount (new LoginApiServlet(), "/api/login/*")
  }
}
