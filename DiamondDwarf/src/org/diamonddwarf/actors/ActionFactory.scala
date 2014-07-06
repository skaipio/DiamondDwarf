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

class ActionFactory(controller: BoardController, actorFactory: ActorFactory, animationFactory: AnimationFactory) {

  def digAtSelf = new DigAtSelf()

  def moveSelf(direction: Direction) = {
    val action = new MoveSelf(controller, direction) with TimedAction
    action.setActionTime(0.15f * 4)
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
      ifAnimatedThenSetAnim(actor, animationFactory.dwarfWalk)
      ifDDActorThenDo(this.getActor(), (a: DDActor) => controller.moveActor(a, direction))
    }

  }

  private def ifDDActorThenDo(actor: Actor, toDo: DDActor => _) = actor match {
    case a: DDActor =>
      toDo(a)
      true
    case _ => false
  }
  private def ifAnimatedThenSetAnim(actor: Actor, anim: Animation) = actor match {
    case a: AnimatedActor => a.currentAnimation = Some(anim)
    case _                =>
  }

  private implicit def tuple3totuple2(c: (Int, Int, Int)): (Int, Int) = (c._1, c._2)
}

trait TimedAction extends Action {
  private var timer: Float = _
  private var lastsFor: Float = _
  private var onFinished: Option[() => _] = None

  def setOnFinished(doOnFinished: () => _) = this.onFinished
  def setActionTime(time: Float) = lastsFor = time
  def getActionTime = lastsFor

  abstract override def act(delta: Float) = {
    if (timer == 0f)
      super.act(delta)
    timer += delta
    if (timer >= lastsFor) {
      this.onFinished.foreach(_())
      true
    } else
      false
  }
}


