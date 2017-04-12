package json

import org.specs2.mutable._
import play.api.libs.json.{JsError, Json}

/**
  * Created by tomoya.igarashi on 2017/04/10.
  */
class MessageSpec extends Specification {

  "Message" >> {
    "positive" >> {
      "parse JSON" >> {
        val serialized =
          """{
            |  "mid" : "mid.$cAAFBX8kgiBxhhGNJ1lbUx4BhJEC0",
            |  "seq" : 7218,
            |  "text" : "あ"
            |}""".stripMargin
        val value = Json.parse(serialized).validate[Message].get
        value must_== Message("mid.$cAAFBX8kgiBxhhGNJ1lbUx4BhJEC0", 7218, Some("あ"), None)
      }
    }
    "negative" >> {
      "mid: invalid format" >> {
        val serialized =
          """{
            |  "mid" : "hogehoge",
            |  "seq" : 7218,
            |  "text" : "あ"
            |}""".stripMargin
        val value = Json.parse(serialized).validate[Message]
        value must haveClass[JsError]
      }
    }
  }

}
