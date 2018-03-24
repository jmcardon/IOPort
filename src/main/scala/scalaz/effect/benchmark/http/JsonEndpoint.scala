package scalaz.effect.benchmark.http

import cats.Monad
import cats.effect.Effect
import org.http4s.HttpService
import org.http4s.dsl.Http4sDsl

final class JsonEndpoint[F[_]: Effect] extends Http4sDsl[F] {

  def service(implicit F: Monad[F]): HttpService[F] =
    HttpService[F] {
      case req @ PUT -> Root / "json" =>
        Ok("Hello, Json!")
    }

}