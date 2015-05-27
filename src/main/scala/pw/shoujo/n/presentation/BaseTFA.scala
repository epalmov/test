package pw.shoujo.n.presentation

import pw.shoujo.abs.{ByRule, Epsilon, State, Tagged}
import pw.shoujo.scheme.Primitive

class BaseTFA[Tin <: Primitive[Tin], Tpe](val table: Array[Array[State]]) extends TFA[Int, Tin, Tpe] {

    val initial = 0

    val fin = table.length - 1

    val T: Set[Tpe] = {
        for {
            r <- table
            Tagged(tag: Tpe, false) <- r
        } yield tag
    }.toSet

    val K: Set[Int] = {
        initial to fin
    }.toSet

    val L: Set[Tin] = {
        for {
            r <- table
            ByRule(primitive) <- r
        } yield primitive.asInstanceOf[Tin]
    }.toSet

    def epsilonTransitions(from: Int): Seq[Transition[Int, Tpe]] = {
        table(from).toSeq.zipWithIndex.collect {
            case (Epsilon, to) => Transition(from, to)
            case (Tagged(tag: Tpe, _), to) => Transition(from, to, Some(tag))
        }
    }

    def transitions(from: Int, rule: Tin): Seq[Transition[Int, Tpe]] = {
        table(from).toSeq.zipWithIndex.collect {
            case (byRule: ByRule[Tin], to) if byRule.rule == rule => Transition(from, to)
        }
    }
}