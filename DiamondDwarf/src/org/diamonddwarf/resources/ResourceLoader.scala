package org.diamonddwarf.resources
import com.badlogic.gdx.Gdx
import org.diamonddwarf.stage._
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion
import com.google.gson.JsonObject
import com.google.gson.Gson
import java.nio.file.Files
import com.badlogic.gdx.graphics.g2d.TextureRegion
import java.util.logging.Logger
import java.util.logging.Level

class ResourceLoader {
  private val logger = Logger.getAnonymousLogger()

  val atlas = new TextureAtlas(Gdx.files.internal("packedTextures.atlas"))
  val textureRegionMapForActors = scala.collection.mutable.Map[Actor, com.badlogic.gdx.utils.Array[AtlasRegion]]()
  val textureRegionMapForVariants = Map(
    Tile.baseTile -> regions("tile/base"),
    Tile.diggableTile -> regions("tile/grass"),
    TileObject.stone -> regions("tileobj/stone"),
    TileObject.hole -> regions("tileobj/hole"),
    Workshop.refinery -> regions("tileobj/refinery"),
    Workshop.replenisher -> regions("tileobj/replenisher"))

  val timerSprite = regions("ui/timer").get(0)
  val timerBackground = regions("ui/timerbg").get(0)
    
  val seamMap = {
    val regions = this.regions("tile/seam")
    val grassBaseSeam = (Tile.diggableTile, Tile.baseTile) -> regions.get(0)
    Map(grassBaseSeam)
  }

  private val tracks = {
    val handles = Gdx.files.internal("bin/tracks").list();
    for (file <- handles) yield Gdx.audio.newMusic(file)
  }

  private val sounds = {
    val handles = Gdx.files.internal("bin/soundeffects").list();
    val soundArray = for (file <- handles) yield (file.nameWithoutExtension() -> Gdx.audio.newSound(file))
    soundArray.toMap
  }

  // Load stage data from "assets/stages"-file
  private val stageData = {
    val file = Gdx.files.internal("stages")
    if (file.exists()) {
      val content = new String(file.readBytes())
      val gson = new Gson()
      gson.fromJson(content, classOf[Array[StageData]])
    } else {
      throw new Exception("File \"assets/stages\" is required for instantiating stages, but was not found.")
      Array[StageData]()
    }

  }

  def getTrack(trackNum: Int) = {
    require(trackNum >= 0 && trackNum < tracks.length, "Track number " + trackNum + " does not exist.")
    tracks(trackNum)
  }

  def hasTrack(trackNum: Int) = trackNum >= 0 && trackNum < tracks.length

  def getSound(name: String) = this.sounds.get(name)

  def getStageData(stageNum: Int) = {
    require(stageNum >= 0 && stageNum <= stageData.length, "Stage number " + stageNum + " does not exist.")
    stageData(stageNum)
  }

  // TODO: Textures need to be disposed of on game.dispose() call
  // TODO: Call dispose in Game.dispose()
  def dispose() = this.atlas.dispose()
  
  private def regions(path: String) =  {
    val regions = atlas.findRegions(path)
    if (regions.size == 0)
      this.noRegion(path)
    regions
  }

  private def noRegion(name: String) {
    logger.log(Level.WARNING, "Could not find region \"" + name + "\" in texture atlas.")
  }

}