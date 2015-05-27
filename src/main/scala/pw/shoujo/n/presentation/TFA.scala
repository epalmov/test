package pw.shoujo.n.presentation

import pw.shoujo.scheme.Primitive

trait TFA[Tid, Tin <: Primitive[Tin], Tpe] {

    def T: Set[Tpe]

    def K: Set[Tid]

    def L: Set[Tin]

    def initial: Tid

    def fin: Tid

    def epsilonTransitions(from: Tid): Seq[Transition[Tid, Tpe]]

    def transitions(from: Tid, rule: Tin): Seq[Transition[Tid, Tpe]]
}