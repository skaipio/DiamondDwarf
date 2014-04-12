package org.diamonddwarf

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture.TextureFilter
import scala.collection.mutable.MutableList
import org.diamonddwarf.stage._

protected object ResourceLoader {
  def getBaseTiles = getTexturesFromPaths("textures/base.png")
  def getStoneTiles = getTexturesFromPaths("textures/stone.png")
  def getDiggableTiles = getTexturesFromPaths("textures/grass1.png","textures/grass2.png")
  def getHoleObjects = getTexturesFromPaths("textures/hole.png")
  def getStoneObjects = getTexturesFromPaths("textures/stone.png")
  
  // TODO: Textures need to be disposed of on game.dispose() call
   def spriteMap = Map(
    Tile.baseTile -> ResourceLoader.getBaseTiles,
    Tile.diggableTile -> ResourceLoader.getDiggableTiles,
    TileObject.stone -> ResourceLoader.getStoneObjects,
    TileObject.hole -> ResourceLoader.getHoleObjects)
    
  private def getTexturesFromPaths(paths : String*) = {
    val textures = for (path <- paths) yield {
      val tex = new Texture(Gdx.files.internal(path))
      tex.setFilter(TextureFilter.Linear, TextureFilter.Linear)
      tex
    }
    textures.toArray
  }
}