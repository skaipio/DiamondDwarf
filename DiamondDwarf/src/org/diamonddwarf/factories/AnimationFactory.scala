package org.diamonddwarf.factories

import org.diamonddwarf.resources.ResourceLoader
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.diamonddwarf.ui.Animation
import java.util.logging.Logger
import java.util.logging.Level

final class AnimationFactory(private val resourceLoader: ResourceLoader) {
  private val logger = Logger.getAnonymousLogger() // Keep this at top

  val dwarfIdle = this.getRegion("tileobj/dwarf")
  val dwarfWalk = this.getRegions("tileobj/dwarf_walk")
  val dwarfDig = this.getRegions("tileobj/dwarf_dig")
  val gemWithQuestionMark = this.getRegion("effects/gems_question")
  val timesMark = this.getRegion("effects/times")
  val numbers = this.getRegions("effects/number")

  def createDwarfIdleAnim = {
    val frames = Array[(TextureRegion, Float)]((dwarfIdle, 1.0f))
    new Animation(frames)
  }

  def createDwarfMoveAnim = {
    val frames = for (i <- 0 until dwarfWalk.size) yield (dwarfWalk.get(i), 0.1f)
    new Animation(frames.toArray)
  }

  def createDwarfDigAnim = {
    val frames = new Array[(TextureRegion, Float)](3);
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

  def createNumberAnim(number: Int) = {
    //require(number >= 0 && number < this.numbers.size, "No assets/effects/number textures found")
    var anim: Animation = null
    if (number >= 0 && number < this.numbers.size) {
      val region = this.numbers.get(number)
      anim = new Animation(Array((region, 1.0f)))
    }else{
      anim = new Animation(Array())
    }
    anim
  }

  private def getRegion(name: String) = {
    val region = resourceLoader.atlas.findRegion(name)
    if (region == null)
      this.noAnimation(name)
    region
  }

  private def getRegions(name: String) = {
    val regions = resourceLoader.atlas.findRegions(name)
    if (regions.size == 0)
      this.noAnimation(name)
    regions
  }

  private def noAnimation(name: String) {
    logger.log(Level.WARNING, "could not find animation matching \"" + name + "\"")
  }
}