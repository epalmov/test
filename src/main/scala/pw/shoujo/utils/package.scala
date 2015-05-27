package pw.shoujo

import scala.collection.immutable.NumericRange.Inclusive
import scala.collection.mutable

package object utils {

    def intToAlphabetic(int: Int): String = {
        int match {
            case _ if int > -1 && int < 10 =>
                int.toString
            case _ if int > -1 && int < 36 =>
                alphabet(int - 10).toString
            case _ =>
                throw new IllegalArgumentException("arg must be > -1 and < 26")
        }
    }

    val alphabet: Inclusive[Char] = 'a' to 'z'
    
    implicit final class IterableDecorator[Tel](iterable: Iterable[Tel]) {

        def maxOption[Tab >: Tel](implicit ord: Ordering[Tab]): Option[Tel] = {
            if (iterable.isEmpty) {
                None
            } else {
                Some(iterable.max(ord))
            }
        }
    }

    def convertToMutableSet[T](iterable: Iterable[T]): mutable.Set[T] = mutable.Set.empty[T] ++ iterable
}