package json.facebookmessanger

import play.api.libs.functional.syntax._
import play.api.libs.json._

/**
  * Created by tomoya.igarashi on 2016/12/11.
  */
case class Payload(url: String)

object Payload {
  implicit val payloadFormat: Format[Payload] =
    (__ \ "url").format[String].inmap(Payload.apply, unlift(Payload.unapply))
}
