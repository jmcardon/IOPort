package scalaz.gatling

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import scala.concurrent.duration._

class SimpleGat extends Simulation { // 3

  val httpConf = http // 4
    .baseURL("http://localhost:8080") // 5
    .acceptHeader(
      "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8") // 6
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .contentTypeHeader("text/plain")
    .userAgentHeader(
      "Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0")

  val scn = scenario("BasicSimulation") // 7
    .exec(
      http("request_1")
        .get("/shorttext/hi"))

  setUp(
    scn.inject(atOnceUsers(10000)) // 12
  ).protocols(httpConf) // 13
}
