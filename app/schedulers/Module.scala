package schedulers

import com.google.inject.AbstractModule
import play.api.libs.concurrent.AkkaGuiceSupport

/**
  * Created by tomoya.igarashi on 2016/11/29.
  */
class Module extends AbstractModule with AkkaGuiceSupport {
  def configure() = {
    bindActor[MessageQueueActor]("message-queue-actor")
    bind(classOf[Scheduler]).asEagerSingleton()
  }
}
