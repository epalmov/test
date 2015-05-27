package pw.shoujo.scheme

trait Rule[Tin <: Primitive[Tin], Tpe]

trait Primitive[Tin <: Primitive[Tin]] extends Rule[Tin, Nothing]

// Predefined:

case class Or[Tin <: Primitive[Tin], Tpe](ruleA: Rule[Tin, Tpe], ruleB: Rule[Tin, Tpe]) extends Rule[Tin, Tpe]

case class Sequence[Tin <: Primitive[Tin], Tpe](ruleA: Rule[Tin, Tpe], ruleB: Rule[Tin, Tpe]) extends Rule[Tin, Tpe]

case class Optional[Tin <: Primitive[Tin], Tpe](rule: Rule[Tin, Tpe]) extends Rule[Tin, Tpe]

case class ZeroOrMore[Tin <: Primitive[Tin], Tpe](rule: Rule[Tin, Tpe]) extends Rule[Tin, Tpe]

case class ByChar(char: Char) extends Primitive[ByChar]