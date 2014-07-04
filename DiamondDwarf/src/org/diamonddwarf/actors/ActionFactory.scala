package org.diamonddwarf.actors

import com.badlogic.gdx.scenes.scene2d.Action
import org.diamonddwarf.BoardController
import org.diamonddwarf.Direction
import org.diamonddwarf.Up
import org.diamonddwarf.Left
import org.diamonddwarf.Down
import org.diamonddwarf.Right
import org.diamonddwarf.tileobjects.Hole
import com.badlogic.gdx.scenes.scene2d.Actor

class ActionFactory(controller: BoardController, actorFactory: ActorFactory) {
  def digAtSelf = new DigAtSelf()
  def moveSelf(direction: Direction) = {
    val action = new MoveSelf(controller, direction) with TimedAction
    action.setActionTime(0.5f)
    action
  }

  case class DigAtSelf() extends Action {
    override def act(delta: Float) = ifDDActorThenDo(this.getActor(), (a: DDActor) => {
      require(a.position().isDefined, "Actor " + actor + " not found on board.")
      controller.addActor(actorFactory.createActorOf(Hole), a.position().get)
    })
  }

  case class MoveSelf(controller: BoardController, val direction: Direction) extends Action {
    override def act(delta: Float) = {
      ifDDActorThenDo(this.getActor(), (a: DDActor) => controller.moveActor(a, direction))
    }

  }

  private def ifDDActorThenDo(actor: Actor, toDo: DDActor => _) = actor match {
    case a: DDActor =>
      toDo(a)
      true
    case _ => false
  }

  private implicit def tuple3totuple2(c: (Int, Int, Int)): (Int, Int) = (c._1, c._2)
}

trait TimedAction extends Action {
  private var timer: Float = _
  private var lastsFor: Float = _

  def setActionTime(time: Float) = lastsFor = time
  def getActionTime = lastsFor

  abstract override def act(delta: Float) = {
    var isFinished = false
    if (timer == 0f) super.act(delta)
    timer += delta
    if (timer >= lastsFor) {
      timer = 0
      isFinished = true
    }
    isFinished
  }
}


