package org.diamonddwarf.ui

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import org.diamonddwarf.DiamondDwarf
import org.diamonddwarf.resources.ResourceLoader

class StageTimer(batch: SpriteBatch, resourceLoader: ResourceLoader) {

  private def lerp(start: Float, end: Float, percent: Float) = start + percent * (end - start)

  def renderTimer {
    val timerProgress = DiamondDwarf.activeMap.currentTime / DiamondDwarf.activeMap.timelimit
    batch.draw(resourceLoader.timerBackground, 100, 700)
    batch.draw(resourceLoader.timerSprite, lerp(100, 700 - 64, Math.min(1, timerProgress)), 700)
  }

}