package org.diamonddwarf

import org.diamonddwarf.stage._
import com.badlogic.gdx.InputProcessor
import scala.collection.mutable.MutableList
import org.diamonddwarf.stage.Coordinate
import com.badlogic.gdx.Input

class Controller(val game: DiamondDwarf) extends InputProcessor {
  override def keyTyped(character: Char): Boolean = character match {
    case '\t' =>
      game.activeMap.buildableIndex =
        (game.activeMap.buildableIndex + 1) % game.activeMap.buildables.length
      println(game.activeMap.buildableIndex)
      true
    case _ =>
      false

  }
  override def keyDown(x: Int): Boolean = false
  override def keyUp(y: Int): Boolean = false
  override def touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = false
  override def touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = false
  override def touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean = false
  override def mouseMoved(screenX: Int, screenY: Int): Boolean = false
  override def scrolled(amount: Int): Boolean = false
}