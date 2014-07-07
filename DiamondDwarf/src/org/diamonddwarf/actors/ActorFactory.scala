package org.diamonddwarf.actors

import org.diamonddwarf.tileobjects.Grass
import org.diamonddwarf.tileobjects.TileObject
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion
import scala.util.Random
import org.diamonddwarf.Resources
import org.diamonddwarf.BoardController
import org.diamonddwarf.boards.ActorBoard
import java.util.logging.Logger
import org.diamonddwarf.tileobjects.TileObject
import org.diamonddwarf.tileobjects.Player
import org.diamonddwarf.tileobjects.Player

final class ActorFactory(resources: Resources) {
  private val logger = Logger.getAnonymousLogger()
  // Replace None with default texture
  def createActorOf(obj: TileObject) = resources.defaultTextureRegions.get(obj) match {
    case Some(regions) =>
      if (regions.size != 0) new DDActor(obj, Some(getRandom(regions))) with AnimatedActor
      else this.actorWithNoTexture(obj)
    case _ => this.actorWithNoTexture(obj)
  }

  def createPlayer = resources.defaultTextureRegions.get(Player) match {
    case Some(regions) if (regions.size != 0) =>
      new PlayerActor(Some(getRandom(regions)))
    case _ => new PlayerActor(None)
  }

  private def actorWithNoTexture(obj: TileObject) = {
    logger.warning("No textures found for " + obj + ".")
    new DDActor(obj, None)
  }

  private def getRandom(regions: List[AtlasRegion]) = regions(Random.nextInt(regions.size))
}