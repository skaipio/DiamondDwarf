package org.diamonddwarf.ui

import com.badlogic.gdx.InputProcessor
import org.diamonddwarf.DiamondDwarf
import org.diamonddwarf.menu.StageMenuGrid

class MenuInputProcessor(stageMenuGrid: StageMenuGrid) extends InputProcessor {
  override def keyTyped(character: Char): Boolean = character match {
    case 'w' =>
      stageMenuGrid.selectorUp
      true
    case 'a' =>
      stageMenuGrid.selectorLeft
      true
    case 's' =>
      stageMenuGrid.selectorDown
      true
    case 'd' =>
      stageMenuGrid.selectorRight
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