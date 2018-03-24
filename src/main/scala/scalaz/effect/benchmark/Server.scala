package scalaz.effect.benchmark

import cats.effect.Effect
import fs2.StreamApp
import org.http4s.client.blaze.Http1Client
import org.http4s.server.blaze.BlazeBuilder
import scalaz.effect.IO
import scalaz.effect.benchmark.module.ApiModule

import scala.concurrent.ExecutionContext.Implicits.global

object Main extends Server[IO]

class Server[F[_]: Effect] extends StreamApp[F] {

  override def stream(
                       args: List[String],
                       requestShutdown: F[Unit]): fs2.Stream[F, StreamApp.ExitCode] =
      for {
        client <- Http1Client.stream[F]()
        ctx = new ApiModule[F]
        init <- BlazeBuilder[F]
          .bindHttp(8080, "localhost")
          .mountService(ctx.api)
          .serve
      } yield init

}
