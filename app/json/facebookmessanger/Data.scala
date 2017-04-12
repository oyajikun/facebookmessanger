package json.facebookmessanger

import play.api.data.validation.ValidationError
import play.api.libs.functional.syntax._
import play.api.libs.json._

/**
  * Created by tomoya.igarashi on 2016/11/25.
  */
case class Data(objekt: String, entry: Seq[Entry])

object Data {
  val r = """(\Apage\z)""".r
  val v = Reads.StringReads.filter(ValidationError("json.data.object.invalid_format")) { s =>
    s match {
      case r(_) => true
      case _ => false
    }
  }
  implicit val dataFormat: Format[Data] = (
    (__ \ "object").format[String](v) and
      (__ \ "entry").format[Seq[Entry]]
    ) (Data.apply, unlift(Data.unapply))
}
