package pw.shoujo.n.presentation

case class Transition[Tid, +Tpe](from: Tid, to: Tid, byTag: Option[Tpe] = None)