package org.diamonddwarf.ui

import org.diamonddwarf.resources.ResourceLoader
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import org.diamonddwarf.DiamondDwarf
import com.badlogic.gdx.Gdx

class PlayerBar(batch: SpriteBatch, resourceLoader: ResourceLoader) {

  def render {
    val timerProgress = DiamondDwarf.activeMap.currentTime / DiamondDwarf.activeMap.timelimit
    batch.draw(resourceLoader.purkkana, 0, Gdx.graphics.getHeight()-resourceLoader.purkkana.originalHeight)
  }

}