package pw.shoujo

import pw.shoujo.n.presentation.Transition
import pw.shoujo.n.scope.BaseScope

package object n extends BaseScope {

    implicit final class TransitionDecorator[Tid](val from: Tid) {

        def ~> (to: Tid): Transition[Tid, Nothing] = {
            Transition(from, to)
        }
    }
}