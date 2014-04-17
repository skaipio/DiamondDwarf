package org.diamonddwarf.factories

import org.diamonddwarf.resources.ResourceLoader
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.diamonddwarf.ui.Animation
import java.util.logging.Logger
import java.util.logging.Level

final class AnimationFactory(private val resourceLoader: ResourceLoader) {
  private val logger = Logger.getAnonymousLogger()
  
  val dwarfIdle = this.getRegion("tileobj/dwarf")
  val dwarfWalk = resourceLoader.atlas.findRegions("tileobj/dwarf_walk")
  val dwarfDig = resourceLoader.atlas.findRegions("tileobj/dwarf_dig")
  val gemWithQuestionMark = this.getRegion("effects/gems_question")
  val timesMark = this.getRegion("effects/times")
  
  
  
  def createDwarfIdleAnim = {
    val frames = Array[(TextureRegion, Float)]((dwarfIdle, 1.0f))
    new Animation(frames)
  }

  def createDwarfMoveAnim = {
    val frames = for (i <- 0 until dwarfWalk.size) yield (dwarfWalk.get(i), 0.1f)
    new Animation(frames.toArray)
  }
  
  def createDwarfDigAnim = {
    val frames = new Array[(TextureRegion,Float)](3);
    frames(0) = (dwarfDig.get(0), 0.1f)
    frames(1) = (dwarfDig.get(1), 0.1f)
    frames(2) = (dwarfDig.get(2), 0.2f)
    new Animation(frames)
  }
  
  def createGemWithQuestionMarkAnim = {	
    val frames = Array[(TextureRegion, Float)]((gemWithQuestionMark, 1.0f)) 
    new Animation(frames)
  }
  
  def createTimesMarkAnim = {
    val frames = Array[(TextureRegion, Float)]((timesMark, 1.0f))
    new Animation(frames)
  }
  
  private def getRegion(name: String) = {   
    val region = resourceLoader.atlas.findRegion(name)
    if (region == null)
      this.noAnimation(name)
    region
  }

  private def noAnimation(name: String) {
    logger.log(Level.WARNING, "could not find animation matching \"" + name + "\"")
  }
}