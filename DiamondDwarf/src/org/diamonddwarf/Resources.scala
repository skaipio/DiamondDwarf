package org.diamonddwarf
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
import scala.collection.JavaConverters
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.diamonddwarf.stage.tileobjects._
import scala.collection.JavaConversions
import org.diamonddwarf.stage.StageTemplate

class Resources {
  private val logger = Logger.getAnonymousLogger()
  val textureAtlas = new TextureAtlas(Gdx.files.internal("packedTextures.atlas"))

  val defaultTextureRegions: Map[TileObject, List[AtlasRegion]] = Map(
    Grass -> getRegions("tile/grass"))

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

  def dispose() {
    textureAtlas.dispose()
  }
  
  private def getRegions(name: String) = {
    val regions = textureAtlas.findRegions(name)
    if (regions.size == 0) logger.log(Level.WARNING, "No texture regions for '" + name + "' was found.")
    regions
  }

  private implicit def libgdxArrayToScalaList[A](array: com.badlogic.gdx.utils.Array[A]): List[A] =
    (for (elm <- JavaConversions.asScalaIterator(array.iterator())) yield elm).toList
}