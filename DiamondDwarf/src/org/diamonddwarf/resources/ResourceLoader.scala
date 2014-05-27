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
import org.diamonddwarf.ui.AnimationTemplate
import org.diamonddwarf.ui.AnimationTemplate
import scala.collection.JavaConverters
import com.google.gson.GsonBuilder
import org.diamonddwarf.ui.AnimationTemplate
import org.diamonddwarf.ui.AnimationTemplate
import org.diamonddwarf.ui.AnimationTemplateDeserializer
import com.google.gson.reflect.TypeToken
import org.diamonddwarf.ui.AnimationTemplate

class ResourceLoader {
  private val logger = Logger.getAnonymousLogger()
  private val gsonBuilder = new GsonBuilder
  gsonBuilder.registerTypeAdapter(classOf[AnimationTemplate], new AnimationTemplateDeserializer(this))

  val atlas = new TextureAtlas(Gdx.files.internal("packedTextures.atlas"))
  val defaultTexture = this.atlas.findRegion("default")
  val textureRegionMapForActors = scala.collection.mutable.Map[Actor, com.badlogic.gdx.utils.Array[AtlasRegion]]()
  val tileTextureMap = Map(
    Tile.diggableID -> getRegions("tile/grass"),
    Tile.baseID -> getRegions("tile/base"))

  val timerSprite = getRegions("ui/timer").get(0)
  val timerBackground = getRegions("ui/timerbg").get(0)
  
  val stageMenuCell = getRegions("ui/stageMenuCell").get(0)

  val seamMap = {
    val regions = this.getRegions("tile/seam")
    val grassBaseSeam = (Tile.diggableID, Tile.baseID) -> regions.get(0)
    Map(grassBaseSeam)
  }
  
  val animationTemplateMap = {
    val file = Gdx.files.internal("animationTemplates")
    if (file.exists()){
      val content = new String(file.readBytes())
      val gson = gsonBuilder.create()
      val mapType = new TypeToken[java.util.Map[String, AnimationTemplate]]() {}.getType()
      val map : java.util.Map[String, AnimationTemplate] = gson.fromJson(content, mapType)
       scala.collection.JavaConversions.asScalaMap(map).toMap
    }else{
      throw new Exception("File \"assets/animationTemplates\" is required for animations, but was not found.")
      Map[String, AnimationTemplate]()
    }
  }
  
  val numberMap = {
    var i = 0
    var map = scala.collection.mutable.Map[Int, TextureRegion]()
    val regions = this.getRegions("effects/digits")
    while(i < regions.size){
      val region = regions.get(i)
      map += region.index -> region
      i+=1
    }
    map.toMap
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
  val stageTemplates = {
    val file = Gdx.files.internal("stageTemplates")
    if (file.exists()) {
      val content = new String(file.readBytes())
      val gson = new Gson()
      gson.fromJson(content, classOf[Array[StageTemplate]])
    } else {
      throw new Exception("File \"assets/stageTemplates\" is required for instantiating stages, but was not found.")
      Array[StageTemplate]()
    }

  }

  def getTrack(trackNum: Int) = {
    require(trackNum >= 0 && trackNum < tracks.length, "Track number " + trackNum + " does not exist.")
    tracks(trackNum)
  }

  def hasTrack(trackNum: Int) = trackNum >= 0 && trackNum < tracks.length

  def getSound(name: String) = this.sounds.get(name)

  // TODO: Textures need to be disposed of on game.dispose() call
  // TODO: Call dispose in Game.dispose()
  def dispose() = {
    this.atlas.dispose()
  }

  private def getRegions(path: String) = {
    val regions = atlas.findRegions(path)
    if (regions.size == 0)
      this.noRegion(path)
    regions
  }
  
  private def getRegion(path: String) = {
    val region = atlas.findRegion(path)
    if (region == null)
      this.noRegion(path)
    region
  }

  private def noRegion(name: String) {
    logger.log(Level.WARNING, "Could not find region \"" + name + "\" in texture atlas.")
  }

}