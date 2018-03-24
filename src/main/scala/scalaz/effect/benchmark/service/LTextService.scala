package scalaz.effect.benchmark.service

import cats.implicits._
import cats.effect.Effect
import org.http4s.client.blaze.Http1Client

class LTextService[F[_]](implicit F: Effect[F]) {
  import LTextService._

  def fetchLargeTextFile: F[String] =
    Http1Client[F]() >>= (_.get(url)(r => r.as[String]))

}

object LTextService {

  private[LTextService] val url: String =
    "https://norvig.com/big.txt"
}
