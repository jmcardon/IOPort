package scalaz.effect.benchmark.http

import cats.implicits._
import cats.Monad
import cats.effect.Effect
import org.http4s.HttpService
import org.http4s.dsl.Http4sDsl
import scalaz.effect.benchmark.service.LTextService


final class LargeTextEndpoint[F[_]: Effect](ltService: LTextService[F]) extends Http4sDsl[F] {

  def service(implicit F: Monad[F]): HttpService[F] =
    HttpService[F] {
      case GET -> Root / "largetext" =>
        for {
          text <- ltService.fetchLargeTextFile >>= (s => Effect[F].pure(s take 10))
          r <- Ok(text)
        } yield r
    }
}
