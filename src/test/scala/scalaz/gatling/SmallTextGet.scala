package scalaz.gatling

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._

import scala.concurrent.duration._
import scalaz.effect.benchmark.model.Url

abstract class SmallTextGet(url: Url, atOnce: Int, constant: Int, ramp: Int)
    extends AbstractLoadTest(url, "/shorttext", atOnce, constant, ramp)

object SmallTextGetEndpoint {
  val Endpoint = ""
}

class StreamSmallText extends SmallTextGet(Url("http://52.43.212.119:8080"), 55000, 55000, 55000)
