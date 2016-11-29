package services

import json.Messaging

import scala.collection.mutable.Queue
import scala.util.control.Exception._

/**
  * Created by tomoya.igarashi on 2016/11/26.
  */
trait MessageQueue {
  def enqueue(t: Messaging): Unit

  def dequeue(): Option[Messaging]
}

class FacebookMessengerMessageQueue extends MessageQueue {
  private val queue = new Queue[Messaging]

  override def enqueue(t: Messaging): Unit = {
    queue.enqueue(t)
  }

  override def dequeue(): Option[Messaging] = {
    allCatch opt queue.dequeue()
  }
}
