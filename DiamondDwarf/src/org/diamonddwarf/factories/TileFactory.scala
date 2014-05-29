package org.diamonddwarf.factories

import com.badlogic.gdx.graphics.g2d.TextureRegion
import scala.util.Random
import org.diamonddwarf.stage.Tile
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion
import org.diamonddwarf.resources.ResourceLoader
import com.badlogic.gdx.Graphics

class TileFactory(resourceLoader: ResourceLoader) {

  private def randomRegion(array: com.badlogic.gdx.utils.Array[AtlasRegion]) =
    array.get(Random.nextInt(array.size))

  def createDiggableTile = {
    val tile = new Tile(Tile.diggableID, true, false)
    tile.defaultTexture = randomRegion(resourceLoader.diggableTileTexture)
    tile
  }

  def createBaseTile = {
    val tile = new Tile(Tile.baseID, false, true)
    tile.defaultTexture = randomRegion(resourceLoader.baseTileTexture)
    tile
  }

  
}