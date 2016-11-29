package json

import play.api.data.validation.ValidationError
import play.api.libs.json._
import play.api.libs.functional.syntax._

/**
  * Created by tomoya.igarashi on 2016/11/25.
  */
case class Recipient(id: String)

object Recipient {
  val r = """(\A\d+\z)""".r
  val v = Reads.StringReads.filter(ValidationError("json.recipient.invalid_format")) { s =>
    s match {
      case r(_) => true
      case _ => false
    }
  }
  implicit val recipientFormat: Format[Recipient] =
    (__ \ "id").format[String](v).inmap(Recipient.apply, unlift(Recipient.unapply))
}
