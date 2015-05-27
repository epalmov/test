package pw.shoujo.n.utils

import pw.shoujo.abs.{ByRule, Epsilon, Tagged}
import pw.shoujo.n
import pw.shoujo.n._
import pw.shoujo.n.presentation.BaseTFABuilder
import pw.shoujo.scheme._

class BaseCompiler[Tin <: Primitive[Tin], Tpe] extends Compiler[Int, Tin, Tpe] {

    def compile(rule: Rule[Tin, Tpe]): n.presentation.TFA[Int, Tin, Tpe] = {

        def compileInner(rule: Rule[Tin, Tpe]): BaseTFABuilder[Tin, Tpe] = {
            rule match {
                case primitive: Primitive[Tin] =>
                    val leaf = BaseTFABuilder.empty[Tin, Tpe]
                    leaf(leaf.initial ~> leaf.fin) = ByRule(primitive)
                    leaf
                case or: Or[Tin, Tpe] =>
                    val a = compileInner(or.ruleA)
                    val baseA = a.extend(1, 0)
                    val b = compileInner(or.ruleB)
                    val baseB = b.append(a)
                    b.extend(0, 1)
                    b(b.initial ~> baseA.initial) = Epsilon
                    b(b.initial ~> baseB.initial) = Epsilon
                    b(baseA.fin ~> b.fin) = Epsilon
                    b(baseB.fin ~> b.fin) = Epsilon
                    b
                case sequence: Sequence[Tin, Tpe] =>
                    val a = compileInner(sequence.ruleA)
                    val b = compileInner(sequence.ruleB)
                    b.append(a, overlap = true)
                    b
                case zeroOrMore: ZeroOrMore[Tin, Tpe] =>
                    val inner = compileInner(zeroOrMore.rule)
                    val baseInner = inner.extend(1, 1)
                    inner(inner.initial ~> baseInner.initial) = Epsilon
                    inner(inner.initial ~> inner.fin) = Epsilon
                    inner(baseInner.fin ~> inner.initial) = Epsilon
                    inner(baseInner.fin ~> inner.fin) = Epsilon
                    inner
                case optional: Optional[Tin, Tpe] =>
                    val inner = compileInner(optional.rule)
                    val baseInner = inner.extend(1, 1)
                    inner(inner.initial ~> baseInner.initial) = Epsilon
                    inner(inner.initial ~> inner.fin) = Epsilon
                    inner(baseInner.fin ~> inner.fin) = Epsilon
                    inner
                case group: Group[Tin, Tpe] =>
                    val inner = compileInner(group.rule)
                    val baseInner = inner.extend(1, 1)
                    inner(inner.initial ~> baseInner.initial) = Tagged(group.tag)
                    inner(baseInner.fin ~> inner.fin) = Tagged(group.tag, isLeave = true)
                    inner
            }
        }

        compileInner(rule).immutable
    }
}