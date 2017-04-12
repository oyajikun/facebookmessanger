package json.facebookmessanger

import play.api.data.validation.ValidationError
import play.api.libs.functional.syntax._
import play.api.libs.json._

/**
  * Created by tomoya.igarashi on 2016/11/25.
  */
case class Entry(id: String, time: BigDecimal, messaging: Seq[Messaging])

object Entry {
  val r = """(\A\d+\z)""".r
  val v = Reads.StringReads.filter(ValidationError("json.entry.id.invalid_format")) { s =>
    s match {
      case r(_) => true
      case _ => false
    }
  }
  implicit val entryFormat: Format[Entry] = (
    (__ \ "id").format[String](v) and
      (__ \ "time").format[BigDecimal] and
      (__ \ "messaging").format[Seq[Messaging]]
    ) (Entry.apply, unlift(Entry.unapply))
}
