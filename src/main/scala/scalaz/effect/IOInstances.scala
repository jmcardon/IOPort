// Copyright (C) 2017 John A. De Goes. All rights reserved.
package scalaz.effect

import cats.effect
import cats.effect.{ConcurrentEffect, IO => CatsIO}
import cats.syntax.either._

trait IOInstances extends RTS {

  implicit val eff: ConcurrentEffect[IO] = new ConcurrentEffect[IO] {
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

    def runCancelable[A](fa: IO[A])(
        cb: Either[Throwable, A] => CatsIO[Unit]): CatsIO[CatsIO[Unit]] = {
      CatsIO {
        val run = unsafePerformIO(
          fa.attempt
            .flatMap[Unit] { r =>
              IO.async { innerCb =>
                val p = cb(r).unsafeRunCancelable(_ => ())
                innerCb(\/-(p))
              }
            }
            .fork)
        CatsIO(run.interrupt(new Exception))
      }
    }

    def cancelable[A](
        k: (Either[Throwable, A] => Unit) => CatsIO[Unit]): IO[A] = {
      val kk =
        k.andThen(i =>
          AsyncReturn.maybeLater[A](_ => i.unsafeRunAsync(_ => ())))
      IO.async0 {
        kk
      }
    }

    def uncancelable[A](fa: IO[A]): IO[A] = fa.uninterruptibly

    def onCancelRaiseError[A](fa: IO[A], e: Throwable): IO[A] = {
      fa.catchAll(e => IO.fail(e))
    }

    def start[A](fa: IO[A]): IO[effect.Fiber[IO, A]] = {
      fa.fork.map(_.toCatsFiber)
    }

    def racePair[A, B](fa: IO[A], fb: IO[B])
      : IO[Either[(A, effect.Fiber[IO, B]), (effect.Fiber[IO, A], B)]] =
      fa.raceWith(fb)(s =>
        IO.now(s.bimap({
          case (l, r) => (l, r.toCatsFiber)
        }, {
          case (l2, r2) => (r2.toCatsFiber, l2)
        })))
  }
}
