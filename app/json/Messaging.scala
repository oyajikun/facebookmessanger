package json

import play.api.libs.json._
import play.api.libs.functional.syntax._

/**
  * Created by tomoya.igarashi on 2016/11/25.
  */
case class Messaging(sender: Sender, recipient: Recipient, timestamp: BigDecimal, message: Message)

object Messaging {
  implicit val messagingFormat: Format[Messaging] = (
    (__ \ "sender").format[Sender] and
      (__ \ "recipient").format[Recipient] and
      (__ \ "timestamp").format[BigDecimal] and
      (__ \ "message").format[Message]
    ) (Messaging.apply, unlift(Messaging.unapply))
}
