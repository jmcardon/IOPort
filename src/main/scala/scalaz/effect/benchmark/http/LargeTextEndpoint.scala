package scalaz.effect.benchmark.http

import cats.Monad
import cats.effect.Effect
import org.http4s.HttpService
import org.http4s.dsl.Http4sDsl

final class LargeTextEndpoint[F[_]: Effect] extends Http4sDsl[F] {

  def service(implicit F: Monad[F]): HttpService[F] =
    HttpService[F] {
      case GET -> Root / "largetext" / url =>
        Ok("Hello, Large and in Charge!!")
    }

}
