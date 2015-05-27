package pw.shoujo.d.presentation

import pw.shoujo.d.presentation.BaseTFABuilder.GoWith
import pw.shoujo.scheme.Primitive
import scala.collection.mutable

class BaseTFABuilder[Tin <: Primitive[Tin], Tpe](val table: mutable.Map[From[Int, Tin], To[Int]] = mutable.Map.empty[From[Int, Tin], To[Int]], var initializer: Seq[Instruction] = Nil) extends TFABuilder[Int, Tin, Tpe] {

    def immutable: TFA[Int, Tin, Tpe] = null

    def update(transition: Transition[Int], goWith: GoWith[Tin]) = {
        table(From(transition.from, goWith.in)) = To(transition.to, goWith.instructions)
    }
}

case object BaseTFABuilder {

    case class GoWith[Tin <: Primitive[Tin]](in: Tin, instructions: Seq[Instruction])
}