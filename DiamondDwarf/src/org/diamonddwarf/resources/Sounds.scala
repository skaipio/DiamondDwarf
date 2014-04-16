package org.diamonddwarf.resources

import com.badlogic.gdx.audio.Sound
import java.util.logging.Logger
import java.util.logging.Level
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Audio

class Sounds(private val resources: ResourceLoader) {
  private val logger = Logger.getAnonymousLogger()
  
  val dwarfNope = this.getSound("nope")

  private def getSound(name: String) = {
    this.resources.getSound(name) match {
      case Some(sound) => sound
      case _ =>
        this.warnNoSoundFound(name)
        null
    }
  }

  private def warnNoSoundFound(name: String) {
    logger.log(Level.WARNING, "could not find sound matching " + name)
  }
}