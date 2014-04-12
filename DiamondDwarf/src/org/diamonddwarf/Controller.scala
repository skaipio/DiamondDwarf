package org.diamonddwarf

import org.diamonddwarf.stage._
import com.badlogic.gdx.InputProcessor
import scala.collection.mutable.MutableList
import org.diamonddwarf.stage.Coordinate
import com.badlogic.gdx.Input

class Controller(val game: DiamondDwarf) extends InputProcessor {
  override def keyTyped(character: Char): Boolean = {
   // if (game.player.moving) return false
//    character match {
//      case 'a' =>
//        game.player.direction = Coordinate.Left
//        game.movePlayer(Coordinate.Left)
//      case 'd' =>
//        game.movePlayer(Coordinate.Right)
//        game.player.direction = Coordinate.Right
//      case 'w' =>
//        game.movePlayer(Coordinate.Up)
//        game.player.direction = Coordinate.Up
//      case 's' =>
//        game.movePlayer(Coordinate.Down)
//        game.player.direction = Coordinate.Down
//      case ' ' => game.playerDig
//      case _ => 
//    }
    true
  }

  override def keyDown(x: Int): Boolean = {
//    if (game.player.moving) return false
//    x match {
//      case Input.Keys.A =>
//        game.player.direction = Coordinate.Left
//        game.movePlayer(Coordinate.Left)
//      case Input.Keys.D =>
//        game.movePlayer(Coordinate.Right)
//        game.player.direction = Coordinate.Right
//      case Input.Keys.W =>
//        game.movePlayer(Coordinate.Up)
//        game.player.direction = Coordinate.Up
//      case Input.Keys.S =>
//        game.movePlayer(Coordinate.Down)
//        game.player.direction = Coordinate.Down
//      case Input.Keys.SPACE => game.playerDig
//      case _ => 
//    }
    true
    
  }
  override def keyUp(y: Int): Boolean = false
  override def touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = false
  override def touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = false
  override def touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean = false
  override def mouseMoved(screenX: Int, screenY: Int): Boolean = false
  override def scrolled(amount: Int): Boolean = false
}