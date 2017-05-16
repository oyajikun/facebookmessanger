package controllers

import javax.inject._

import akka.actor.ActorSystem
import play.api._
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class HomeController @Inject()(actorSystem: ActorSystem) extends Controller {
  implicit val myExecutionContext: ExecutionContext = actorSystem.dispatchers.lookup("my-context")

  /**
    * Create an Action to render an HTML page with a welcome message.
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def futures = Action.async {
/*    val f1 = Future {
      val start = new java.util.Date()
      println(s"[${Thread.currentThread().getName}]:start ${start}")
      Thread.sleep(2000)
      val end = new java.util.Date()
      println(s"[${Thread.currentThread().getName}]:end   ${end}")
      1
    }
    val f2 = Future {
      val start = new java.util.Date()
      println(s"[${Thread.currentThread().getName}]:start ${start}")
      Thread.sleep(2000)
      val end = new java.util.Date()
      println(s"[${Thread.currentThread().getName}]:end   ${end}")
      2
    }
    val fs1 = Future.sequence(Seq(f1, f2))
    val fs2 = Future {
      for {
        i <- (1 to 2)
      } yield {
        val start = new java.util.Date()
        println(s"[${Thread.currentThread().getName}]:start ${start}")
        Thread.sleep(2000)
        val end = new java.util.Date()
        println(s"[${Thread.currentThread().getName}]:end   ${end}")
        i
      }
    }*/
    val xs = (1 to 32).map { i =>
      Future {
        val start = new java.util.Date()
        println(s"[${Thread.currentThread().getName}]:start ${start}")
        Thread.sleep(2000)
        val end = new java.util.Date()
        println(s"[${Thread.currentThread().getName}]:end   ${end}")
        i
      }
    }
    val fs3 = Future.sequence(xs)
    for {
      xs <- fs3
    } yield {
      println(xs.sum)
      Ok("futures")
    }
  }
}
