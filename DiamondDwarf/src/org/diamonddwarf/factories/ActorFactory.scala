package org.diamonddwarf.factories

import org.diamonddwarf.resources.ResourceLoader
import org.diamonddwarf.stage.Player
import org.diamonddwarf.resources.Sounds
import org.diamonddwarf.items.Shovel
import org.diamonddwarf.stage.State
import org.diamonddwarf.stage.GemsFound
import com.badlogic.gdx.audio.Sound

final class ActorFactory(private val resources: ResourceLoader, private val effectFactory : EffectFactory,
    private val animFactory : AnimationFactory, private val sounds : Sounds) {

  def createPlayer = {
    val player = new Player("Hessu")
    player.activeState = player.states.idle
    player.nextState = player.states.idle

    player.defaultTextureRegion = animFactory.dwarfIdle

    player.associateStateWithAnim(player.states.idle, animFactory.createDwarfIdleAnim)
    player.associateStateWithAnim(player.states.moving, animFactory.createDwarfMoveAnim)
    player.associateStateWithAnim(player.states.digging, animFactory.createDwarfDigAnim)
    
     def addEffect(s: State) = s match {
      case state : GemsFound =>      
    	val x = player.position.x*64-9
    	val y = player.position.y*64+65
    	player.addEffect(effectFactory.createGemsDetectedPopup(x, y, state.gemsFound))	
      case _ =>
    }

    def setStateSound(state: State, sound: Sound) {
      if (state != null) player.associateStateWithSound(state, sound)
    }
    
    player.associateStateWithMethod(player.states.foundGems, addEffect)

    setStateSound(player.states.noGemsFound, sounds.dwarfNope)
    setStateSound(player.states.foundGems, sounds.foundGems)
    setStateSound(player.states.digging, sounds.digging)
    setStateSound(player.states.gotGem, sounds.gotGem)
    setStateSound(player.states.moving, sounds.moving)
    
    player.shovel = Shovel.shabbyShovel

    player
  }

}