package controllers

import javax.inject._

import json.Messaging
import play.api.Logger
import play.api.data.validation.ValidationError
import play.api.libs.json._
import play.api.mvc._
import services.MessageQueue

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class FacebookMessangerController @Inject() (messageQueue: MessageQueue) extends Controller {
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
    Logger.debug(Json.prettyPrint(request.body))

    val jsResult = request.body.validate[json.Data]
    Logger.debug(jsResult.toString)
    val data = jsResult.get
    for {
      entry <- data.entry
      messaging <- entry.messaging
    } {
      messageQueue.enqueue(messaging)
    }

    Ok
  }
}
