package pw.shoujo.d.scope

import pw.shoujo.d
import pw.shoujo.scheme.Primitive

trait BaseScope {

    def compiler[Tin <: Primitive[Tin], Tpe]: d.utils.Compiler[Int, Tin, Tpe] = new d.utils.BaseCompiler[Tin, Tpe]
}