package pw.shoujo

import pw.shoujo.d.presentation.Transition
import pw.shoujo.d.scope.BaseScope

package object d extends BaseScope {

    implicit final class TransitionDecorator[Tid](val from: Tid) {

        def ~> (to: Tid): Transition[Tid] = {
            Transition(from, to)
        }
    }
}