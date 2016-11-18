package controllers

import javax.inject._

import play.api.Logger
import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class FacebookMessangerController @Inject() extends Controller {

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def webhook = Action { request =>
    Logger.debug(request.queryString.toString)
    (request.getQueryString("hub.mode"), request.getQueryString("hub.verify_token")) match {
      case (Some("subscribe"), Some("mychatbot")) =>
        Logger.debug("Validating webhook")
        Ok(request.getQueryString("hub.challenge").getOrElse(""))
      case _ =>
        Logger.error("Failed validation. Make sure the validation tokens match.")
        Forbidden
    }
  }

  def receivedMessage = Action(parse.json) { request =>
    Logger.debug(request.body.toString)
    Ok
  }
}
