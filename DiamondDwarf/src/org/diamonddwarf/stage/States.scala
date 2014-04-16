package org.diamonddwarf.stage


class States(moveSpeed: Float, digSpeed: Float) {
  val idle = new State(0)
  val moving = new State(moveSpeed)
  val digging = new State(digSpeed)
  val detectingGems = new State(0.2f)
}