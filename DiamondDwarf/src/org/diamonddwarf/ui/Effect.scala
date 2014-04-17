package org.diamonddwarf.ui

import org.diamonddwarf.stage.Coordinate

class Effect(val runtime: Float) {
  // Keep these at top
  private var colorMin = 0f
  private var colorMax = 1f

  var animations: Array[(Animation, Coordinate)] = null
  var deltaR = colorMin
  var deltaG = colorMin
  var deltaB = colorMin
  var deltaAlpha : Float => Float = null

  var red = colorMax
  var green = colorMax
  var blue = colorMax
  var alpha = colorMax

  var deltaX = 0f
  var deltaY = 0f
  var x = 0f
  var y = 0f

  private var runningTime = 0f

  def isFinished = this.runningTime >= runtime

  def reset {
    this.alpha = colorMin
    this.runningTime = 0f
  }

  def update(delta: Float) {
    this.updateAnimations(delta)

    red = this.clamp(red + deltaR, colorMin, colorMax)
    green = this.clamp(green + deltaG, colorMin, colorMax)
    blue = this.clamp(blue + deltaB, colorMin, colorMax)
    alpha = this.clamp(deltaAlpha(runningTime), colorMin, colorMax)

    x += deltaX
    y += deltaY
    
    runningTime += delta
  }

  private def updateAnimations(delta: Float) {
    if (this.animations != null) {
      this.animations.foreach(_._1.update(delta))
    }
  }

  private def clamp(n: Float, min: Float, max: Float) = math.max(math.min(n, max), min)
}