package services

import javax.inject.Singleton

import json.facebookmessanger.Messaging

import scala.collection.mutable.Queue
import scala.util.control.Exception._

/**
  * Created by tomoya.igarashi on 2016/11/26.
  */
trait MessageQueue {
  def enqueue(t: Messaging): Unit

  def dequeueAll(): Seq[Messaging]

  def length(): Int
}

@Singleton
class FacebookMessengerMessageQueue extends MessageQueue {
  private val queue = new Queue[Messaging]

  override def enqueue(t: Messaging): Unit = {
    queue.enqueue(t)
  }

  override def dequeueAll(): Seq[Messaging] = {
    queue.dequeueAll(_ => true)
  }

  override def length(): Int = queue.length
}
