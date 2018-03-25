package scalaz

package object effect {

  type \/[E, A] = Either[E, A]
  type \/-[E, A] = Right[E, A]
  type -\/[E, A] = Left[E, A]

  object \/- {
    @inline def apply[E, A](a: A): E \/ A = Right(a)
    @inline def unapply[E, A](e: Right[E, A]): Option[A] = Right.unapply(e)
  }

  object -\/ {
    @inline def apply[E, A](a: E): E \/ A = Left(a)
    @inline def unapply[E, A](e: Left[E, A]): Option[E] = Left.unapply(e)
  }

  implicit class FiberSyntax[A](val f: Fiber[A]) extends AnyVal {
    def toCatsFiber: cats.effect.Fiber[IO, A] = new cats.effect.Fiber[IO, A] {
      def cancel: IO[Unit] =
        f.interruptIgnore(new Exception).catchAll(_ => IO.unit)

      def join: IO[A] = f.join
    }
  }

}
