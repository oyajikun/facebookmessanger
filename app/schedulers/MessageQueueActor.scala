package schedulers

import akka.actor.Actor

import javax.inject.Inject
import json.Messaging._

import play.api.libs.json.Json
import play.api.Logger
import services.MessageQueue

/**
  * Created by tomoya.igarashi on 2016/11/30.
  */
class MessageQueueActor @Inject()(messageQueue: MessageQueue) extends Actor {

  override def receive = {
    case "handle" => {
      messageQueue.dequeue() match {
        case Some(m) =>
          val json = Json.toJson(m)
          Logger.debug(Json.prettyPrint(json))
        // TODO: retrieve message and response
        case _ =>
      }
    }
  }
}
