package pw.shoujo.n.presentation

import pw.shoujo.abs.{State, Unknown}
import pw.shoujo.n.presentation.BaseTFABuilder.Change
import pw.shoujo.scheme.Primitive
import scala.collection.mutable

class BaseTFABuilder[Tin <: Primitive[Tin], Tpe](val table: mutable.ArrayBuffer[mutable.ArrayBuffer[State]]) extends TFABuilder[Int, Tin, Tpe] {

    def initial: Int = 0

    def fin: Int = table.size - 1

    def extend(paddingBegin: Int, paddingEnd: Int): Change = {
        val size = table.size
        table.insertAll(0, Seq.fill(paddingBegin)(mutable.ArrayBuffer.fill(size)(Unknown)))
        table.appendAll(Seq.fill(paddingEnd)(mutable.ArrayBuffer.fill(size)(Unknown)))
        table.foreach { row =>
            row.insertAll(0, Seq.fill(paddingBegin)(Unknown))
            row.appendAll(Seq.fill(paddingEnd)(Unknown))
        }
        Change(paddingBegin, size - 1 + paddingBegin)
    }

    def append(that: BaseTFABuilder[Tin, Tpe], overlap: Boolean = false): Change = {
        val base = this.extend(that.table.size - (if (overlap) 1 else 0), 0)
        for {
            x <- that.initial to that.fin
            y <- that.initial to that.fin
        } {
            this.table(x)(y) = that.table(x)(y)
        }
        base
    }

    def update(transition: Transition[Int, Tpe], state: State) {
        table(transition.from)(transition.to) = state
    }

    def immutable: BaseTFA[Tin, Tpe] = new BaseTFA(table.map(_.toArray).toArray)
}

case object BaseTFABuilder {

    def empty[Tin <: Primitive[Tin], Tpe]: BaseTFABuilder[Tin, Tpe] = {
        new BaseTFABuilder(mutable.ArrayBuffer.fill(2)(mutable.ArrayBuffer.fill(2)(Unknown)))
    }

    case class Change(initial: Int, fin: Int)
}