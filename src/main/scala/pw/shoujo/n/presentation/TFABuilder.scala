package pw.shoujo.n.presentation

import pw.shoujo.scheme.Primitive

trait TFABuilder[Tid, Tin <: Primitive[Tin], Tpe] {

    def immutable: TFA[Tid, Tin, Tpe]
}