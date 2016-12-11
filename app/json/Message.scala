package json

import play.api.data.validation.ValidationError
import play.api.libs.json._
import play.api.libs.functional.syntax._

/**
  * Created by tomoya.igarashi on 2016/11/25.
  */
case class Message(mid: String, seq: Int, text: Option[String], attachments: Seq[Attachment])

object Message {
  val r1 = """(\Amid\.\d+:\w+\z)""".r
  val v1 = Reads.StringReads.filter(ValidationError("json.message.mid.invalid_format")) { s =>
    s match {
      case r1(_) => true
      case _ => false
    }
  }
  implicit val messageFormat: Format[Message] = (
    (__ \ "mid").format[String](v1) and
      (__ \ "seq").format[Int] and
      (__ \ "text").formatNullable[String] and
      (__ \ "attachments").format[Seq[Attachment]]
    ) (Message.apply, unlift(Message.unapply))
}
