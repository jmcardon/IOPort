package scalaz.effect.benchmark.service

import cats.implicits._
import cats.effect.Effect
import fs2.{ Chunk, Stream }
import org.http4s._
import org.http4s.client.blaze.Http1Client

class LTextService[F[_]](implicit F: Effect[F]) {
  import LTextService._

  def fetchLargeTextFile(url: String): F[String] =
    Http1Client[F]() >>= (_.get(url)(r => r.as[String]))

  def fetchLargeTextFilestream: F[Stream[F, Chunk[Byte]]] =
   for {
     req <- F.delay(Request[F](Method.GET, ltUri))
     client <- Http1Client[F]()
     res <- F.delay(client.streaming(req)(_.body.chunks))
   } yield res


  def largeTextStream: F[Stream[F, Byte]] =
    F.delay(Stream
      .emits("SCALAZ".getBytes("UTF-8"))
      .repeat
      .take(1000000)
      .covary[F])
}

object LTextService {

  private[LTextService] val ltUri: Uri =
    Uri.uri("https://norvig.com/big.txt")
}
