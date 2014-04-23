package org.diamonddwarf.factories

import com.badlogic.gdx.graphics.g2d.TextureRegion
import scala.util.Random
import org.diamonddwarf.stage.Tile
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion

class TileFactory(defaultTexture: TextureRegion, tileTextureMap: Map[Int, com.badlogic.gdx.utils.Array[AtlasRegion]]) {
  def createDiggableTile = {
    val tile = new Tile(Tile.diggableID, true, false)
    tile.defaultTexture = this.getTileTexture(Tile.diggableID)
    tile
  }
  
  def createBaseTile = {
    val tile = new Tile(Tile.baseID, false, true)
    tile.defaultTexture = this.getTileTexture(Tile.baseID)
    tile
  }

  private def getTileTexture(id: Int) = {
    this.tileTextureMap.get(id) match {
      case Some(regions) =>
        val r = Random.nextInt(regions.size)
        regions.get(r)
      case _ => defaultTexture
    }
  }
}