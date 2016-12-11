package json

import play.api.libs.json._
import play.api.libs.functional.syntax._

/**
  * Created by tomoya.igarashi on 2016/12/11.
  */
case class Attachment(tipe: String, payload: Payload)

object Attachment {
  implicit val attachmentFormat: Format[Attachment] = (
    (__ \ "type").format[String] and
      (__ \ "payload").format[Payload]
    ) (Attachment.apply, unlift(Attachment.unapply))
}
