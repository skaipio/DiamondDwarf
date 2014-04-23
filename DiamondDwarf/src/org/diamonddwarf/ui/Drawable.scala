package org.diamonddwarf.ui

import com.badlogic.gdx.graphics.g2d.TextureRegion

trait Drawable {
  var defaultTexture : TextureRegion = null
  def getTexture = this.defaultTexture
}