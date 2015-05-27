package pw.shoujo.d.utils

import pw.shoujo.d._
import pw.shoujo.d.presentation.BaseTFABuilder.GoWith
import pw.shoujo.d.presentation.{BaseTFABuilder, Save}
import pw.shoujo.n.presentation.Transition
import pw.shoujo.scheme.Primitive
import pw.shoujo.utils._
import pw.shoujo.{d, n}
import scala.collection.mutable

class BaseCompiler[Tin <: Primitive[Tin], Tpe] extends Compiler[Int, Tin, Tpe] {

    protected type MapItems = Map[Tpe, Int]

    protected def ≺ (mapItemsA: MapItems, mapItemsB: MapItems): Boolean = {
        if (mapItemsA.size == 1 && mapItemsA.keys == mapItemsB.keys) {
            val a = mapItemsA.values.head
            val b = mapItemsB.values.head
            a > b
        } else {
            throw new RuntimeException("uncomparable tag value functions")
        }
    }

    protected case class ClosureItem(state: Int, mapItems: MapItems)

    /**
     * Method computes ε-closure (tag-wise unambiguously) for any consistent automata.
     */
    protected def teClosure(fa: ♄, cis: Set[ClosureItem]): Set[ClosureItem] = {
        case class Prioritized(ci: ClosureItem, priority: Int)
        val queue = mutable.Queue.concat(cis.map(Prioritized(_, 0)))
        val closure = convertToMutableSet(queue)
        var addressFakeCounter = cis.map(_.mapItems.values.maxOption.getOrElse(-1)).maxOption.getOrElse(-1)
        while (queue.nonEmpty) {
            val p = queue.dequeue()
            val reachable = fa.epsilonTransitions(p.ci.state).map { case Transition(_, to, byTag) =>
                val mapItems: MapItems = byTag match {
                    case Some(tag) =>
                        addressFakeCounter += 1
                        Map(tag -> addressFakeCounter)
                    case None =>
                        Map.empty
                }
                Prioritized(ClosureItem(to, mapItems), p.priority + 1)
            }
            // Find a state with priority lower then we found in the current wave.
            // It should be simple removed from the closure.
            closure.find { suspected =>
                reachable.exists(r => suspected.ci.state == r.ci.state && suspected.priority > r.priority)
            } foreach {
                closure.remove
            }
            reachable.filterNot(closure.contains).foreach { unmarked =>
                closure += unmarked
                queue.enqueue(unmarked)
            }
        }
        closure.map(_.ci).toSet
    }

    def compile(fa: ♄): ☉ = {
        val initialAsClosure = teClosure(fa, Set(ClosureItem(fa.initial, Map.empty)))
        val initializer = for {
            ci <- initialAsClosure.toSeq
            (tag, tagAddress) <- ci.mapItems
        } yield {
            Save(tag, tagAddress)
        }
        val builder = new BaseTFABuilder[Tin, Tpe](initializer = initializer)
        var stateId = -1
        def generateStateId: Int = {
            stateId += 1
            stateId
        }
        val cacheOfStates = mutable.Map(initialAsClosure -> generateStateId)
        val queue = mutable.Queue(initialAsClosure)

        while (queue.nonEmpty) {
            val fromClosure = queue.dequeue()
            val from = cacheOfStates(fromClosure)
            for {
                in <- fa.L
            } {
                val wave = for {
                    ci <- fromClosure
                    tr <- fa.transitions(ci.state, in)
                } yield {
                    ClosureItem(tr.to, ci.mapItems)
                }
                if (wave.nonEmpty) {
                    val closure = teClosure(fa, wave)
                    val closureUnambiguous = closure.groupBy(_.state).map(Function.tupled(disambiguate)).toSet
                    val to = cacheOfStates.getOrElseUpdate(closureUnambiguous, {
                        queue.enqueue(closureUnambiguous)
                        generateStateId
                    })
                    builder(from ~> to) = GoWith(in, Nil)
                }
            }
        }

        builder.immutable
    }

    private type ♄ = n.presentation.TFA[Int, Tin, Tpe]

    private type ☉ = d.presentation.TFA[Int, Tin, Tpe]

    private def disambiguate(state: Int, ambiguousItems: Set[ClosureItem]): ClosureItem = {
        val mapItems = ambiguousItems.map(_.mapItems).toSeq.sortWith(≺).head
        ClosureItem(state, mapItems)
    }
}