package org.diamonddwarf.actors

import org.diamonddwarf.tileobjects.Player
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion
import org.diamonddwarf.Direction

class PlayerActor(textureRegion: Option[AtlasRegion])
  extends TileObjectActor(Player, textureRegion) with AnimatedActor with MovingActor {
  
  this.speed = 2.0f

  override def setState(state: State) = {
    state match {
      case _: Moving => state.delay = 1f/this.speed
      case _: Digging => state.delay = 1f
      case _ =>
    }
    super.setState(state)
  }

}