package org.diamonddwarf.actors

import org.diamonddwarf.Game
import fs.tileboard.board.Board.B
import org.diamonddwarf.Direction
import com.badlogic.gdx.graphics.g2d.Batch
import org.diamonddwarf.Direction
import org.diamonddwarf.Stay

trait MovingActor extends DDActor {
  private var fromPosition = 0f
  var speed = 1.0f // one tile per second
  var direction: B = Stay

  def setDirection(dir: B) = this.direction = dir
  def setMoving(dir: B) = {
    setDirection(dir)
    this.fromPosition = -1
  }
  
  abstract override def drawingPosition = {
    val (origx, origy) = super.drawingPosition
    val (dx, dy) = this.lerp(direction)
    (origx+dx*Game.tilesize , origy+dy*Game.tilesize )
  }

  abstract override def update(delta: Float) {
    super.update(delta)
    if (direction != Stay)
      this.fromPosition += speed * delta
    if (this.fromPosition >= 0) {
      this.direction = Stay
      this.fromPosition = 0
    }
  }
  
  private def lerp(d: (Int, Int)): (Float, Float) = (d._1 * fromPosition, d._2 * fromPosition)
}