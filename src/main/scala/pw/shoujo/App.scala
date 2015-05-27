package pw.shoujo

import pw.shoujo.abs._
import pw.shoujo.n._
import pw.shoujo.scheme._

object App extends scala.App {

    val nfa = n.compiler.compile(Or(Group("A", Sequence[ByChar, Nothing](ByChar('a'), ByChar('b'))), Group("B", ByChar('a'))))
//    val nfaBuilder = n.presentation.BaseTFABuilder.empty[ByChar, String]
//    nfaBuilder.extend(0, 1)
//    nfaBuilder(0 ~> 0) = ByRule(ByChar('a'))
//    nfaBuilder(0 ~> 1) = Tagged("T")
//    nfaBuilder(1 ~> 1) = ByRule(ByChar('a'))
//    nfaBuilder(1 ~> 2) = ByRule(ByChar('a'))
//    val nfa = nfaBuilder.immutable
    val dfa = d.compiler.compile(nfa)
    println(dfa)
}