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

}
