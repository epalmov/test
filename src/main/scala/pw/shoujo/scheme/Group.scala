package pw.shoujo.scheme

class Group[Tin <: Primitive[Tin], Tpe](val tag: Tpe, val rule: Rule[Tin, Tpe]) extends Rule[Tin, Tpe]

case object Group {

    def apply[Tin <: Primitive[Tin], Tpe, Trl](tag: Tpe, rule: Rule[Tin, Trl]): Group[Tin, Tpe] = {
        new Group[Tin, Tpe](tag, rule.asInstanceOf[Rule[Tin, Tpe]])
    }
}