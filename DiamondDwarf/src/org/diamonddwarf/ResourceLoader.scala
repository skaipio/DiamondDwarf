package org.diamonddwarf

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture.TextureFilter
import scala.collection.mutable.MutableList
import org.diamonddwarf.stage._
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.audio.Music

class ResourceLoader {
  val atlas = new TextureAtlas(Gdx.files.internal("packedTextures.atlas"))
  val textureRegionMapForActors = scala.collection.mutable.Map[Actor, Array[AtlasRegion]]()
  val textureRegionMapForVariants = Map(
    Tile.baseTile -> this.getBaseTiles,
    Tile.diggableTile -> this.getDiggableTiles,
    TileObject.stone -> this.getStoneObjects,
    TileObject.hole -> this.getHoleObjects)

  val tracks = this.loadAllTracks

  def loadAllTracks = {
    val handles = Gdx.files.internal("bin/tracks").list();
    for (file <- handles) yield Gdx.audio.newMusic(file)
  }

  def associateActorWithRegion(actor: Actor, region: String) {
    this.textureRegionMapForActors.get(actor) match {
      case Some(_) =>
      case _ =>
        val regions = this.atlas.findRegions(region)
        this.textureRegionMapForActors += actor -> regions
    }

  }

  // TODO: Textures need to be disposed of on game.dispose() call
  // TODO: Call dispose in Game.dispose()
  def dispose() = this.atlas.dispose()

  private def getBaseTiles = atlas.findRegions("tile/base")
  private def getDiggableTiles = atlas.findRegions("tile/grass")
  private def getHoleObjects = atlas.findRegions("tileobj/hole")
  private def getStoneObjects = atlas.findRegions("tileobj/stone")

}