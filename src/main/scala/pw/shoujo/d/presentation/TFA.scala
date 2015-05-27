package pw.shoujo.d.presentation

trait TFA[Tid, Tin, Tpe] {

    def initializer: Seq[Instruction]
}