package json

import play.api.data.validation.ValidationError
import play.api.libs.json._
import play.api.libs.functional.syntax._

/**
  * Created by tomoya.igarashi on 2016/11/22.
  */
case class Sender(id: String)

object Sender {
  val r = """(\A\d+\z)""".r
  val v = Reads.StringReads.filter(ValidationError("json.sender.id.invalid_format")) { s =>
    s match {
      case r(_) => true
      case _ => false
    }
  }
  implicit val senderFormat: Format[Sender] =
    (__ \ "id").format[String](v).inmap(Sender.apply, unlift(Sender.unapply))
}
