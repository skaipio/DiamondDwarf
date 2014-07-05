package org.diamonddwarf.actors

import com.badlogic.gdx.graphics.g2d.Batch
import org.diamonddwarf.Game

trait AnimatedActor extends DDActor {
  var currentAnimation: Option[Animation] = None

  abstract override def act(delta: Float) = {
    super.act(delta)
    currentAnimation.foreach(anim => anim.advance(delta))
  }

  abstract override def draw(batch: Batch, parentAlpha: Float) = currentAnimation match {
    case Some(anim) => anim.getCurrentFrame match {
      case Some(frame) => this.position().foreach(c => (batch.draw(frame, c._1*Game.tilesize, c._2*Game.tilesize )))
      case _ => super.draw(batch, parentAlpha)
    }
    case _ => super.draw(batch, parentAlpha)
  }
}