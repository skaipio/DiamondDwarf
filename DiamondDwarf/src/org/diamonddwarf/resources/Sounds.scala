package org.diamonddwarf.resources

import com.badlogic.gdx.audio.Sound
import java.util.logging.Logger
import java.util.logging.Level
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Audio

class Sounds(private val resources: ResourceLoader) {
  private val logger = Logger.getAnonymousLogger()
  
  val digging = this.getSound("dig")
  val dwarfNope = this.getSound("nope")
  val foundGems = this.getSound("foundgems")
  val gotGem = this.getSound("gem")

  private def getSound(name: String) = {
    this.resources.getSound(name) match {
      case Some(sound) => sound
      case _ =>
        this.noSound(name)
        null
    }
  }

  private def noSound(name: String) {
    logger.log(Level.WARNING, "could not find sound matching \"" + name + "\"")
  }
}