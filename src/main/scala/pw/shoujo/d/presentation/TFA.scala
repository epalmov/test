package pw.shoujo.d.presentation

trait TFA[Tid, Tin, Tpe] {

    def initializer: Seq[Instruction]

    def finishers: Map[Tid, Seq[Instruction]]
}