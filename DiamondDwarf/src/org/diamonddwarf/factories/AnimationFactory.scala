package org.diamonddwarf.factories

import org.diamonddwarf.resources.ResourceLoader
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.diamonddwarf.ui.Animation
import java.util.logging.Logger
import java.util.logging.Level
import org.diamonddwarf.stage.State
import org.diamonddwarf.stage.TileObject
import org.diamonddwarf.ui.AnimationTemplate
import org.diamonddwarf.resources.ResourceLoader
import org.diamonddwarf.ui.AnimationTemplate

/**
 * Animations are fetched from animationTemplateMap that is built from a file of JSON format.
 * The animation object has a name e.g. "dwarfIdle", and it contains two arrays: "frames" and "times".
 * The "frames"-array lists paths that will be used to fetch texture regions from a texture atlas.
 * Note that a path might be associated with more than one region. For example the "dwarfWalk"-animation
 * is associated with more than one regions and uses the same time, 0.1f, for all regions. Times are
 * associated in order, so for example if the "dwarfWalk"-animation had times 0.1f and 0.2f, then 0.2f
 * would be used for the second region that would be found with the single given path. The last float time in
 * the array will be used for all the rest of the regions if there are not enough times specified.
 */
final class AnimationFactory(defaultTexture: TextureRegion, animationTemplateMap: Map[String, AnimationTemplate], numberMap: Map[Int, TextureRegion]) {
  private val logger = Logger.getAnonymousLogger() // Keep this at top

  def createRefineryWorkAnim = {
    this.createAnimation("refineryWork")
  }

  def createReplenisherWorkAnim = {
    this.createAnimation("replenisherWork")
  }

  def createDwarfIdleAnim = {
    this.createAnimation("dwarfIdle")
  }

  def createDwarfMoveAnim = {
    this.createAnimation("dwarfMove")
  }

  def createDwarfDigAnim = {
    this.createAnimation("dwarfDig")
  }

  def createGemWithQuestionMarkAnim = {
    this.createAnimation("gemWithQuestionMark")
  }

  def createTimesMarkAnim = {
    this.createAnimation("timesMark")
  }
  
  def createStoneIdle = {
    this.createAnimation("stoneIdle")
  }
  
  def createHoleIdle = {
    this.createAnimation("holeIdle")
  }

  def createNumberAnim(number: Int) = {
    this.numberMap.get(number) match {
      case Some(region) => new Animation(Array((region, 1.0f)))
      case _ => new Animation(Array())
    }
  }

  def createAnimation(name: String) = {
    this.animationTemplateMap.get(name) match {
      case Some(template) =>
        this.buildAnimFromTemplate(template)
      case _ =>
        this.noAnimation(name)
        new Animation(Array((defaultTexture, 1.0f)))
    }
  }

  private def buildAnimFromTemplate(template: AnimationTemplate) = {
    new Animation(template.frames)
  }

  private def noAnimation(name: String) {
    logger.log(Level.WARNING, "Could not find animation \"" + name + "\".")
  }
}