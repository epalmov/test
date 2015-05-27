package pw.shoujo.n.utils

import pw.shoujo.n
import pw.shoujo.scheme.{Primitive, Rule}

trait Compiler[Tid, Tin <: Primitive[Tin], Tpe] {

    def compile(rule: Rule[Tin, Tpe]): n.presentation.TFA[Tid, Tin, Tpe]
}