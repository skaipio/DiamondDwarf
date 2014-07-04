package org.diamonddwarf

import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.Input.Keys
import org.diamonddwarf.actors.DDActor
import com.badlogic.gdx.scenes.scene2d.Action
import org.diamonddwarf.actors.DDActor
import org.diamonddwarf.actors.ActionFactory
import org.diamonddwarf.actors.ActorFactory
import org.diamonddwarf.actors.ActorFactory

class DDStage(actorFactory: ActorFactory) extends Stage {
  private var controller_ : BoardController = _
  private var actionFactory : ActionFactory = _
  def controller = controller_ 
  def controller_=(c: BoardController) = {
    controller_ = c 
    actionFactory = new ActionFactory(controller, actorFactory)
  } 
  
  override def keyDown(keyCode: Int): Boolean = {
    require(controller != null, "DDStage must be attached to a board controller.")
    keyCode match {
      case Keys.W => this.addActionToPlayer(moveAction(Up))
      case Keys.A => this.addActionToPlayer(moveAction(Left))
      case Keys.S => this.addActionToPlayer(moveAction(Down))
      case Keys.D => this.addActionToPlayer(moveAction(Right))
      case Keys.SPACE => this.addActionToPlayer(actionFactory.digAtSelf)
      case _ => return false
    }
    true
  }

  private def moveAction(direction: Direction) = actionFactory.moveSelf(direction)

  private def addActionToPlayer(action: Action) {
    val player = controller.getPlayerActor
    require(player != null, "Player object is missing from board.")
    player.addAction(action)
  }
}

