package org.diamonddwarf

import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.Input.Keys

trait BoardInputHandler extends BoardController with InputProcessor {

    override def keyDown(keyCode: Int): Boolean = {

      keyCode match {
        case Keys.W => //this.addActionToPlayer(moveAction(Up))
        case Keys.A => //this.addActionToPlayer(moveAction(Left))
        case Keys.S => //this.addActionToPlayer(moveAction(Down))
        case Keys.D => //this.addActionToPlayer(moveAction(Right))
        case Keys.SPACE => //this.addActionToPlayer(actionFactory.digAtSelf)
        case _ => return false
      }
      true
    }

}