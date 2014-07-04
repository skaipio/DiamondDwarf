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

class ActionFactory(actorFactory: ActorFactory, controller: BoardController) {
  def digAtSelf = new DigAtSelf()
  def moveSelf(direction: Direction) = new MoveSelf(controller, direction)

  class DigAtSelf() extends Action {
    override def act(delta: Float) = ifDDActorThenDo(this.getActor(), (a: DDActor) => {
      require(a.position().isDefined, "Actor " + actor + " not found on board.")
      controller.addActor(actorFactory.createActorOf(Hole), a.position().get)
    })
  }

  class MoveSelf(controller: BoardController, direction: Direction) extends Action {
    override def act(delta: Float) =
      ifDDActorThenDo(this.getActor(), (a: DDActor) => controller.moveActor(a, direction))
  }

  private def ifDDActorThenDo(actor: Actor, toDo: DDActor => _) = actor match {
    case a: DDActor =>
      toDo(a)
      true
    case _ => false
  }

  private implicit def tuple3totuple2(c: (Int, Int, Int)): (Int, Int) = (c._1, c._2)
}


