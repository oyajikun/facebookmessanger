package json.facebookmessanger

import play.api.data.validation.ValidationError
import play.api.libs.functional.syntax._
import play.api.libs.json._

/**
  * Created by tomoya.igarashi on 2016/11/25.
  */
case class Message(
                    mid: String,
                    seq: Int,
                    text: Option[String],
                    attachments: Option[Seq[Attachment]])

object Message {
  val r1 = """\Amid\.\$[\s\S]+\z""".r
  val e1 = ValidationError("json.message.mid.invalid_format")
  val v1 = Reads.StringReads.filter(e1)(r1.findFirstIn(_).nonEmpty)
  implicit val messageFormat: Format[Message] = (
    (__ \ "mid").format[String](v1) and
      (__ \ "seq").format[Int] and
      (__ \ "text").formatNullable[String] and
      (__ \ "attachments").formatNullable[Seq[Attachment]]
    ) (Message.apply, unlift(Message.unapply))
}
