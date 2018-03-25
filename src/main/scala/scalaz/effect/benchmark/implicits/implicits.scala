package scalaz.effect.benchmark

import cats.effect.Effect
import io.circe.generic.auto._
import org.http4s._
import org.http4s.circe._
import scalaz.effect.benchmark.model._

package object implicits {

  implicit def urlEncoder[F[_]: Effect]: EntityDecoder[F, Url] =
    jsonOf[F, Url]
}
