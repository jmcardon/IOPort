package scalaz.effect.benchmark.module

import cats.effect.Effect
import org.http4s.HttpService

import scalaz.effect.benchmark.http.{
  JsonEndpoint,
  LargeTextEndpoint,
  ShortTextEndpoint
}
import cats.syntax.semigroupk._
import org.http4s.client.Client

import scalaz.effect.benchmark.service.LTextService

class ApiModule[F[_]: Effect](client: Client[F]) {

  private[module] val ltService: LTextService[F] =
    new LTextService[F](client)

  private[module] val jsonEndpoint: HttpService[F] =
    new JsonEndpoint[F].service
  private[module] val largeTextEndpoint: HttpService[F] =
    new LargeTextEndpoint[F](ltService).service

  private[module] val shortTextEndpoint: HttpService[F] =
    new ShortTextEndpoint[F].service

  val api: HttpService[F] =
    jsonEndpoint <+> largeTextEndpoint <+> shortTextEndpoint
}
