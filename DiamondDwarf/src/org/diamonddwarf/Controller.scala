package org.diamonddwarf

import org.diamonddwarf.stage._
import com.badlogic.gdx.InputProcessor
import scala.collection.mutable.MutableList

class Controller(val game : DiamondDwarf) extends InputProcessor {
  override def keyTyped(character: Char): Boolean = {
    character match {
      case 'a' => game.movePlayer(Coordinate.Left)
      case 'd' => game.movePlayer(Coordinate.Right)
      case 'w' => game.movePlayer(Coordinate.Up)
      case 's' => game.movePlayer(Coordinate.Down)
      case ' ' => game.playerDig
      case _ =>
    }
    true
  }

  override def keyDown(x: Int): Boolean = false
  override def keyUp(y: Int): Boolean= false
  override def touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean= false
  override def touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean= false
  override def touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean= false
  override def mouseMoved(screenX: Int, screenY: Int): Boolean= false
  override def scrolled(amount: Int): Boolean= false
}