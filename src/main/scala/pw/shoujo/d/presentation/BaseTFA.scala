package pw.shoujo.d.presentation

import pw.shoujo.scheme.Primitive

class BaseTFA[Tid, Tin <: Primitive[Tin], Tpe](val table: Map[From[Tid, Tin], To[Tid]],
                                               val initializer: Seq[Instruction],
                                               val finishers: Map[Tid, Seq[Instruction]]) extends TFA[Tid, Tin, Tpe]