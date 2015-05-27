package pw.shoujo.d.presentation

trait TFABuilder[Tid, Tin, Tpe] {

    def immutable: TFA[Tid, Tin, Tpe]
}