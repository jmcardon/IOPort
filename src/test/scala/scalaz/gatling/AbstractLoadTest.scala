package scalaz.gatling

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._
import scalaz.effect.benchmark.model.Url

abstract class AbstractLoadTest(baseUrl: Url,
                                route: String,
                                atOnce: Int,
                                constant: Int,
                                ramp: Int)
    extends Simulation {

  val httpConf = http
    .baseURL(baseUrl.url)
    .acceptHeader("text/html;q=0.9,*/*;q=0.8")
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .contentTypeHeader("text/plain")
    .userAgentHeader("Mozilla/5.0 (Windows NT 5.1; rv:31.0) ScalaBench Ayy")
    .shareConnections

  val scn = scenario(s"Hitting $baseUrl$route")
    .during(5.minutes) {
      exec(http("request_1").get(route))
    }

  setUp(scn.inject(atOnceUsers(16384)))
    .protocols(httpConf)
}
