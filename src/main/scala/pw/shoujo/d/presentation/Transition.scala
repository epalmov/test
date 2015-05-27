package pw.shoujo.d.presentation

import pw.shoujo.scheme.Primitive

case class From[Tid, Tin <: Primitive[Tin]](id: Tid, in: Tin)

case class To[Tid](id: Tid, instructions: Seq[Instruction])

case class Transition[Tid](from: Tid, to: Tid)