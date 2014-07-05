package org.diamonddwarf

import java.util.logging.Level
import java.util.logging.Logger
import scala.collection.JavaConversions
import org.diamonddwarf.boards.BoardTemplate
import org.diamonddwarf.tileobjects.DwarfBase
import org.diamonddwarf.tileobjects.Grass
import org.diamonddwarf.tileobjects.Hole
import org.diamonddwarf.tileobjects.Player
import org.diamonddwarf.tileobjects.TileObject
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion
import com.google.gson.Gson

class Resources {
  private val logger = Logger.getAnonymousLogger()
  val textureAtlas = new TextureAtlas(Gdx.files.internal("packedTextures.atlas"))
  val errorTexture = textureAtlas.findRegion("default")

  val defaultTextureRegions: Map[TileObject, List[AtlasRegion]] = Map(
    Grass -> getRegions("tile/grass"),
    DwarfBase -> getRegions("tile/base"),
    Hole -> getRegions("tileobj/hole"),
    Player -> getRegions("tileobj/dwarf"))

  val animations: Map[Int, List[AtlasRegion]] = Map(
    0 -> getRegions("tileobj/dwarf_walk"))

  // Load stage data from "assets/stages"-file
  val boardTemplates = {
    val file = Gdx.files.internal("stageTemplates")
    if (file.exists()) {
      val content = new String(file.readBytes())
      val gson = new Gson()
      gson.fromJson(content, classOf[Array[BoardTemplate]])
    } else {
      throw new Exception("File \"assets/stageTemplates\" is required for instantiating stages, but was not found.")
      Array[BoardTemplate]()
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