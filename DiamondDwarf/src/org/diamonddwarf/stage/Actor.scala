package org.diamonddwarf.stage

class Actor(val speed: Float) {

  var direction = Coordinate.Zero
  var progress = speed
  var moving = false
  
  def update(delta: Float) {
    if (moving) {
      progress += delta
      if (progress >= speed) {moving = false }
    }
    else { progress = speed; }
  }
  
}