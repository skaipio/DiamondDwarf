package org.diamonddwarf.resources
import com.badlogic.gdx.Gdx
import org.diamonddwarf.stage._
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion
import com.google.gson.JsonObject
import com.google.gson.Gson
import java.nio.file.Files

class ResourceLoader {
  val atlas = new TextureAtlas(Gdx.files.internal("packedTextures.atlas"))
  val textureRegionMapForActors = scala.collection.mutable.Map[Actor, com.badlogic.gdx.utils.Array[AtlasRegion]]()
  val textureRegionMapForVariants = Map(
    Tile.baseTile -> this.getBaseTiles,
    Tile.diggableTile -> this.getDiggableTiles,
    TileObject.stone -> this.getStoneObjects,
    TileObject.hole -> this.getHoleObjects)

  val tracks = this.loadAllTracks
  
  // Load stage data from "assets/stages"-file
  private val stageData = {
    val file = Gdx.files.internal("stages")
    if (file.exists()){
      val content = new String(file.readBytes())
      val gson = new Gson()
      gson.fromJson(content, classOf[Array[StageData]])
    }else{
      throw new Exception("File \"assets/stages\" is required for instantiating stages, but was not found.")
      Array[StageData]()
    }
    
  }
  
  def getStageDataFor(stageNum: Int) = {
    require(stageNum-1 >= 0 && stageNum-1 <= stageData.length, "Stage number " + stageNum+ " does not exist.")
    stageData(stageNum-1)
  }

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