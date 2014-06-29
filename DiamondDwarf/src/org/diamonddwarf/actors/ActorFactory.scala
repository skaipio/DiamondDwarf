package org.diamonddwarf.actors

import org.diamonddwarf.stage.tileobjects.Grass
import org.diamonddwarf.stage.tileobjects.TileObject
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.diamonddwarf.stage.TileObjectStage
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion
import scala.util.Random
import org.diamonddwarf.Resources
import org.diamonddwarf.stage.ActorStage

final class ActorFactory(resources: Resources) {
  // Replace None with default texture
  def createTileObject(obj: TileObject, stage: ActorStage) = resources.defaultTextureRegions.get(obj) match {
    case Some(regions) => new DDActor(obj, stage, Some(getRandom(regions)))
    case _ => new DDActor(obj, stage, None)
  }     
  
  private def getRandom(regions : List[AtlasRegion]) = regions(Random.nextInt(regions.size))
}