package pw.shoujo.d.presentation

trait Instruction

case class Save[Tpe](tag: Tpe, address: Int) extends Instruction

case class Copy(from: Int, to: Int) extends Instruction

case class ExposeOnFinish[Tpe](tag: Tpe, address: Int) extends Instruction