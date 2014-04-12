package org.diamonddwarf

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture.TextureFilter
import scala.collection.mutable.MutableList
import org.diamonddwarf.stage.Tile

protected object ResourceLoader {
  def getBaseTiles = getTexturesFromPaths("textures/base.png")
  def getStoneTiles = getTexturesFromPaths("textures/base.png")
  def getDiggableTiles = getTexturesFromPaths("textures/grass1.png","textures/grass2.png")
  
  // TODO: Textures need to be disposed of on game.dispose() call
   def spriteMap = Map(
    Tile.baseTile -> ResourceLoader.getBaseTiles,
    Tile.stoneTile -> ResourceLoader.getStoneTiles,
    Tile.diggableTile -> ResourceLoader.getDiggableTiles)
    
  private def getTexturesFromPaths(paths : String*) = {
    val textures = for (path <- paths) yield {
      val tex = new Texture(Gdx.files.internal(path))
      tex.setFilter(TextureFilter.Linear, TextureFilter.Linear)
      tex
    }
    textures.toArray
  }
}