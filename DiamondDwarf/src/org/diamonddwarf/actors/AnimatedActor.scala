package org.diamonddwarf.actors

import com.badlogic.gdx.graphics.g2d.Batch
import org.diamonddwarf.Game
import org.diamonddwarf.tileobjects.TileObject

trait AnimatedActor extends StateActor {
  var animMap: Option[Map[State, () => Animation]] = None
  var previousState: State = Idle()
  var currentAnimation: Option[Animation] = None

  abstract override def update(delta: Float) {
    super.update(delta)
    currentAnimation.foreach(anim => anim.advance(delta))

    if (previousState != this.getState) {
      animMap.foreach(_.get(this.getState) match {
        case Some(f) => this.currentAnimation = Some(f())
        case _ => this.currentAnimation = None
      })
    }
    previousState = this.getState
  }

  abstract override protected[this] def getTexture =
    currentAnimation match {
      case Some(anim) =>
        anim.getCurrentFrame
      case _ => super.getTexture
    }

}