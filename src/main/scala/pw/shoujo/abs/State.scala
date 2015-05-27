package pw.shoujo.abs

import pw.shoujo.scheme.Primitive

trait State

case object Epsilon extends State

case object Unknown extends State

case class Tagged[Tpe](tag: Tpe, isLeave: Boolean = false) extends State

case class ByRule[Tin <: Primitive[Tin]](rule: Primitive[Tin]) extends State