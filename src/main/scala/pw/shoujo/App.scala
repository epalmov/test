package pw.shoujo

import pw.shoujo.abs._
import pw.shoujo.n._
import pw.shoujo.scheme._

object App extends scala.App {

    def nfaA: n.presentation.TFA[Int, ByChar, String] = {
        val builder = n.presentation.BaseTFABuilder.empty[ByChar, String]
        builder.extend(0, 1)

        builder(0 ~> 0) = ByRule(ByChar('a'))
        builder(0 ~> 1) = Tagged("T")
        builder(1 ~> 1) = ByRule(ByChar('a'))
        builder(1 ~> 2) = ByRule(ByChar('a'))

        builder.immutable
    }

    def nfaB: n.presentation.TFA[Int, ByChar, String] = {
        val builder = n.presentation.BaseTFABuilder.empty[ByChar, String]
        builder.extend(0, 5)

        builder(0 ~> 1) = Epsilon
        builder(1 ~> 2) = ByRule(ByChar('a'))
        builder(2 ~> 6) = Tagged("A")

        builder(0 ~> 3) = Epsilon
        builder(3 ~> 4) = ByRule(ByChar('a'))
        builder(4 ~> 5) = ByRule(ByChar('b'))
        builder(5 ~> 6) = Tagged("B")

        builder.immutable
    }

    val dfa = d.compiler.compile(nfaB)
    println(dfa)
}