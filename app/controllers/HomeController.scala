package controllers

import javax.inject._

import akka.actor.ActorSystem
import play.api._
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

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
    for { // parallel
      x <- f1
      y <- f2
    } yield {
      println(x + y)
      Ok("futures")
    }
    val fs1 = Future.sequence(Seq(f1, f2))
    for { // parallel
      xs <- fs1
    } yield {
      println(xs.sum)
      Ok("futures")
    }
    val fs1 = Future {
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
    }
    for { // sequential
      xs <- fs1
    } yield {
      println(xs.sum)
      Ok("futures")
    }*/
    val xs = (1 to 8).map { i =>
      Future {
        val start = new java.util.Date()
        println(s"[${Thread.currentThread().getName}]:start ${start}")
        Thread.sleep(2000)
        val end = new java.util.Date()
        println(s"[${Thread.currentThread().getName}]:end   ${end}")
        i
      }
    }
    val ef1 = Future {
      Thread.sleep(1000)
      throw new RuntimeException("hogehoge")
      1
    }
    val ys = ef1 +: xs
    val fs3 = Future.sequence(ys)
    // TODO: fs3の結果を受けて次のFutureを実行する実験
    val f1 = for { // parallel
      xs <- fs3
    } yield {
      println(xs.sum)
      Ok("futures")
    }
    f1.recover {
      case e => {
        println(e)
        BadRequest(e.getMessage)
      }
    }
  }
}
