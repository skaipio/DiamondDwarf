package org.diamonddwarf.factories

import org.diamonddwarf.ui.Effect
import org.diamonddwarf.stage.Coordinate

class EffectFactory(val animFactory: AnimationFactory) {
  def createGemsDetectedPopup(x: Int, y: Int, gemsFound: Int) = {
    val effect = new Effect(1.5f)
    val gem = animFactory.createGemWithQuestionMarkAnim
    val times = animFactory.createTimesMarkAnim
    val number = animFactory.createNumberAnim(gemsFound)
    effect.animations = Array((gem, Coordinate(0, 0)), (times, Coordinate(40, 10)), (number, Coordinate(58,6)))
    effect.x = x
    effect.y = y
    effect.deltaY = 0.3f
    effect.deltaAlpha = {
      (x: Float) =>
        {
          val alpha = (-0.3f) * math.pow(x, 2).toFloat + 1
          alpha
        }
    }
    effect
  }
}