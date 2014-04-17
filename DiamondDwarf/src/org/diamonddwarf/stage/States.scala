package org.diamonddwarf.stage


class States() {
  val idle = new State(0)
  val moving = new State(0.3f)
  val digging = new State(0.4f)
  val detectingGems = new State(0.2f)
  val noGemsFound = new State(0)
  val foundGems = new State(0)
}