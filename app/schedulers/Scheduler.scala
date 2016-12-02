package schedulers

import javax.inject._

import akka.actor.{ActorRef, ActorSystem}

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

/**
  * Created by tomoya.igarashi on 2016/11/29.
  */
class Scheduler @Inject()(val system: ActorSystem, @Named("message-queue-actor") val messageQueueActor: ActorRef)(implicit ec: ExecutionContext) {
  system.scheduler.schedule(
    0.microseconds, 5.seconds, messageQueueActor, "handle")
}

