package pw.shoujo.n.scope

import pw.shoujo.n
import pw.shoujo.scheme.Primitive

trait BaseScope {

    def compiler[Tin <: Primitive[Tin], Tpe]: n.utils.Compiler[Int, Tin, Tpe] = new n.utils.BaseCompiler[Tin, Tpe]
}