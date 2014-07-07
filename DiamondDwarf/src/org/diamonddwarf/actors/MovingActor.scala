package org.diamonddwarf.actors

import org.diamonddwarf.Game
import org.diamonddwarf.Direction
import com.badlogic.gdx.graphics.g2d.Batch
import org.diamonddwarf.Direction

trait MovingActor extends DDActor {
  private var progress = 0f
  var moving = false
  var speed = 1.0f // one tile per second
  var direction: Option[Direction] = None

  abstract override def update(delta: Float) {
    super.update(delta)
    if (moving) {
      this.progress += speed * delta
    }
  }

  abstract override def draw(batch: Batch) = (direction, this.position(), this.textureRegion) match {
    case (Some(d), Some(p), Some(t)) =>
      val (dx, dy) = this.lerp(d)
      batch.draw(t, (p._1 + dx) * Game.tilesize, (p._2 + dy) * Game.tilesize)
    case _ => super.draw(batch)
  }

  private def lerp(d: Direction): (Float, Float) = (d._1 * progress, d._2 * progress)
}