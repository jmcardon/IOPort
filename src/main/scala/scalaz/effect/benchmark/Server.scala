package scalaz.effect.benchmark

import cats.effect.Effect
import fs2.StreamApp
import org.http4s.client.blaze.Http1Client
import org.http4s.server.blaze.BlazeBuilder
import scalaz.effect.IO
import scalaz.effect.benchmark.module.ApiModule

import scala.concurrent.ExecutionContext.Implicits.global

object Main extends Server[IO](8081)

object CatsMain extends Server[cats.effect.IO](8080)

class Server[F[_]: Effect](port: Int) extends StreamApp[F] {

  override def stream(
      args: List[String],
      requestShutdown: F[Unit]): fs2.Stream[F, StreamApp.ExitCode] =
    for {
      client <- Http1Client.stream[F]()
      ctx = new ApiModule[F]
      init <- BlazeBuilder[F]
        .bindHttp(port, "localhost")
        .mountService(ctx.api)
        .serve
    } yield init

}
