package pw.shoujo.d.utils

import pw.shoujo.scheme.Primitive
import pw.shoujo.{d, n}

trait Compiler[Tid, Tin <: Primitive[Tin], Tpe] {

    def compile(fa: n.presentation.TFA[Tid, Tin, Tpe]): d.presentation.TFA[Tid, Tin, Tpe]
}