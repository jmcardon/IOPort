package scalaz.effect.benchmark.http

import cats.implicits._
import cats.Monad
import cats.effect.Effect
import org.http4s.HttpService
import org.http4s.dsl.Http4sDsl
import scalaz.effect.benchmark.service.LTextService
import scalaz.effect.benchmark.model.Url
import scalaz.effect.benchmark.implicits._

final class LargeTextEndpoint[F[_]: Effect](ltService: LTextService[F])
    extends Http4sDsl[F] {

  def service(implicit F: Monad[F]): HttpService[F] = HttpService[F] {
    case GET -> Root / "largetext" =>
      ltService.largeTextStream >>= (Ok(_))

    case req @ POST -> Root / "largetext" =>
      for {
        url <- req.as[Url]
        text <- ltService.fetchLargeTextFile(url.url) map (_ take 1000)
        r <- Ok(text)
      } yield r
  }
}
