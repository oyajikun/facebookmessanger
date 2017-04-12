package json.facebookmessanger

import org.specs2.mutable._
import play.api.libs.json.Json

/**
  * Created by tomoya.igarashi on 2016/11/23.
  */
class SenderSpec extends Specification {

  "Sender" >> {
    "positive" >> {
      "parse JSON" >> {
        val serialized =
          """{
            |  "id": "1078845895560033"
            |}""".stripMargin
        val value = Json.parse(serialized).validate[Sender].get
        value must_== Sender("1078845895560033")
      }
    }
  }
}
