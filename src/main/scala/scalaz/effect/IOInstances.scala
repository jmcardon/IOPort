// Copyright (C) 2017 John A. De Goes. All rights reserved.
package scalaz.effect

import cats.effect.{Effect, IO => CatsIO}

trait IOInstances extends RTS {
  implicit val eff: Effect[IO] = new Effect[IO] {
    def runAsync[A](fa: IO[A])(
        cb: Either[Throwable, A] => CatsIO[Unit]): CatsIO[Unit] = {
      CatsIO {
        unsafePerformIO(
          fa.attempt
            .flatMap[Unit] { r =>
              IO.async { innerCb =>
                val p = cb(r).unsafeRunAsync(_ => ())
                innerCb(\/-(p))
              }
            }
            .fork)
      }
    }

    def async[A](k: (Either[Throwable, A] => Unit) => Unit): IO[A] = IO.async(k)

    def suspend[A](thunk: => IO[A]): IO[A] = IO.suspend(thunk)

    def raiseError[A](e: Throwable): IO[A] = IO.fail(e)

    def handleErrorWith[A](fa: IO[A])(f: Throwable => IO[A]): IO[A] =
      fa.catchAll(f)

    def pure[A](x: A): IO[A] = IO.now(x)

    def flatMap[A, B](fa: IO[A])(f: A => IO[B]): IO[B] = fa.flatMap(f)

    /** This can srsly fk off -_- **/
    def tailRecM[A, B](a: A)(f: A => IO[Either[A, B]]): IO[B] =
      f(a).flatMap(_.fold[IO[B]](tailRecM(_)(f), IO.now))
  }
}
