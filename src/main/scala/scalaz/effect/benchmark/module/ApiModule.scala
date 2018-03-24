package scalaz.effect.benchmark.module

import cats.effect.Effect
import org.http4s.HttpService
import scalaz.effect.benchmark.http.{JsonEndpoint, LargeTextEndpoint, ShortTextEndpoint}
import cats.syntax.semigroupk._

class ApiModule[F[_]: Effect] {


  private[module] val jsonEndpoint: HttpService[F] =
    new JsonEndpoint[F].service
  private[module] val largeTextEndpoint: HttpService[F] =
    new LargeTextEndpoint[F].service
  private[module] val shortTextEndpoint: HttpService[F] =
    new ShortTextEndpoint[F].service

  val api: HttpService[F] =
    jsonEndpoint <+> largeTextEndpoint <+> shortTextEndpoint
}
