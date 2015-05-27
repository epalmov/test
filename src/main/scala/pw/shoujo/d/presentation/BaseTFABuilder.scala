package pw.shoujo.d.presentation

import pw.shoujo.scheme.Primitive
import scala.collection.mutable

class BaseTFABuilder[Tin <: Primitive[Tin], Tpe](val table: mutable.Map[From[Int, Tin], To[Int]] = mutable.Map.empty[From[Int, Tin], To[Int]],
                                                 var initializer: Seq[Instruction] = Nil,
                                                 val finishers: mutable.Map[Int, mutable.ArrayBuffer[Instruction]] = mutable.Map.empty[Int, mutable.ArrayBuffer[Instruction]]) extends TFABuilder[Int, Tin, Tpe] {

    def immutable: TFA[Int, Tin, Tpe] = {
        new BaseTFA[Int, Tin, Tpe](table.toMap, initializer, finishers.toMap)
    }

    def update(transition: Transition[Int], inAndInstructions: (Tin, Seq[Instruction])) {
        table(From(transition.from, inAndInstructions._1)) = To(transition.to, inAndInstructions._2)
    }
}