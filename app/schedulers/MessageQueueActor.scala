package schedulers

import akka.actor.Actor
import javax.inject.Inject

import json.Messaging
import json.Messaging._
import play.api.{Configuration, Logger}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.Json
import play.api.libs.ws._
import services.MessageQueue

import scala.concurrent.Future
import scala.util.{Failure, Success}


/**
  * Created by tomoya.igarashi on 2016/11/30.
  */
class MessageQueueActor @Inject()(messageQueue: MessageQueue, wsClient: WSClient, configuration: Configuration) extends Actor {

  override def receive = {
    case "handle" => {
      val futureSendMessages = for {
        messaging <- messageQueue.dequeueAll()
      } yield {
        sendMessage(messaging)
      }
      Future.sequence(futureSendMessages).onComplete {
        case Success(xs) => {
          xs.foreach { res =>
            Logger.debug(res.toString)
          }
        }
        case Failure(e) => Logger.error("Failed to send message", e)
      }
    }
  }

  private def sendMessage(messaging: Messaging) = {
    val json = Json.toJson(messaging)
    Logger.debug(Json.prettyPrint(json))
    val pageAccessToken = configuration.getString("facebookmessanger.pageAccessToken").getOrElse("unknown_page_access_token")
    val recipient = Json.obj("id" -> messaging.sender.id)
    val message = Json.obj("text" -> messaging.message.text)
    val data = Json.obj(
      "recipient" -> recipient,
      "message" -> message
    )
    val url = "https://graph.facebook.com/v2.6/me/messages"
    wsClient.url(url)
      .withQueryString("access_token" -> pageAccessToken)
      .post(data)
  }
}
