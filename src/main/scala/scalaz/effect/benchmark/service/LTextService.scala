package scalaz.effect.benchmark.service

import cats.implicits._
import cats.effect.Effect
import fs2.{Chunk, Stream}
import org.http4s._
import org.http4s.client.Client
import org.http4s.client.blaze.Http1Client

class LTextService[F[_]](client: Client[F])(implicit F: Effect[F]) {
  import LTextService._

  def fetchLargeTextFile(url: String): F[String] =
    Http1Client[F]() >>= (_.get(url)(r => r.as[String]))

  def fetchLargeTextFilestream: F[Stream[F, Chunk[Byte]]] =
    F.pure(client.streaming(Request[F](Method.GET, ltUri))(_.body.chunks))

  def largeTextStream: F[Stream[F, Byte]] =
    F.delay(
      Stream
        .emits("SCALAZ".getBytes("UTF-8"))
        .repeat
        .take(1000000)
        .covary[F])
}

object LTextService {

  private[LTextService] val ltUri: Uri =
    Uri.uri("https://norvig.com/big.txt")
}
