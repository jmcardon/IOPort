package scalaz.effect.benchmark.http

import cats.Monad
import cats.effect.Effect
import fs2._
import org.http4s._
import org.http4s.dsl.Http4sDsl

final class LargeTextEndpoint[F[_]](implicit F: Effect[F])
    extends Http4sDsl[F] {

  def service(implicit F: Monad[F]): HttpService[F] =
    HttpService[F] {
      case GET -> Root / "largetext" / url =>
        F.pure(Response[F](Ok).withBodyStream(largeTxtEndpoint))
    }

  def largeTxtEndpoint: Stream[F, Byte] =
    Stream
      .emits("REEEEEEEEEEEEE".getBytes("UTF-8"))
      .repeat
      .take(1000000)
      .covary[F]

}
