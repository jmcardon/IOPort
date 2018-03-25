package scalaz.effect.benchmark

import cats.effect.Effect
import fs2.StreamApp
import monix.eval.Task
import monix.eval.instances._
import monix.execution.Scheduler.Implicits.global
import org.http4s.client.blaze.Http1Client
import org.http4s.server.blaze.BlazeBuilder
import scalaz.effect.IO
import scalaz.effect.benchmark.module.ApiModule

object IOServer extends Server[IO]

object CIOServer extends Server[cats.effect.IO]

object TaskServer extends Server[Task]

class Server[F[_]: Effect] extends StreamApp[F] {

  override def stream(
      args: List[String],
      requestShutdown: F[Unit]): fs2.Stream[F, StreamApp.ExitCode] =
    for {
      client <- Http1Client.stream[F]()
      ctx = new ApiModule[F](client)
      init <- BlazeBuilder[F]
        .bindHttp(8080, "localhost")
        .mountService(ctx.api)
        .serve
    } yield init

}
