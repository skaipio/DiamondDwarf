package org.diamonddwarf.stage

class States() {
  
  // player
  val idle = new State(0)
  val moving = new State(0.3f)
  val digging = new State(0.4f)
  val detectingGems = new State(0.5f)
  val noGemsFound = new State(0)
  val foundGems = new GemsFound()
  val gotGem = new State(0)
  val working = new State(1f)


}

class WorkshopStates() extends States() {
  
  
  
  
}