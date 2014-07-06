package org.diamonddwarf

case class ActionTimer(toPerform: () => Unit, cooldown: Float) {

  private var progress = 0.0f

  /** Adds time to total */
  def tick(timePassed: Float): Boolean = {
    progress = Math.min(timePassed + progress, cooldown);
    isReady
  }

  /** Adds time to total, performs action if time, resets timer */

  def tickTryReset(timePassed: Float) = {
    if (tick(timePassed)) {
      toPerform()
      reset()
      true
    } else
      false
  }

  /** Adds time to total, performs action if time, doesn't reset timer */
  def tickTry(timePassed: Float) = {
    if (tick(timePassed)) {
      toPerform()
    }
    isReady
  }

  def isReady = progress >= cooldown
  def reset() = progress = 0.0f

}