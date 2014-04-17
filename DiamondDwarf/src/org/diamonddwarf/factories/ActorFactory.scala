package org.diamonddwarf.factories

import org.diamonddwarf.resources.ResourceLoader
import org.diamonddwarf.stage.Player
import org.diamonddwarf.ui.AnimationFactory
import org.diamonddwarf.resources.Sounds
import org.diamonddwarf.items.Shovel

final class ActorFactory(private val resources: ResourceLoader,
    private val animFactory : AnimationFactory, private val sounds : Sounds) {
  
  def createPlayer = {
    val player = new Player("Hessu")
    player.activeState = player.states.idle
    player.nextState = player.states.idle

    player.defaultTextureRegion = animFactory.dwarfIdle

    player.associateStateWithAnim(player.states.idle, animFactory.createDwarfIdleAnim)
    player.associateStateWithAnim(player.states.moving, animFactory.createDwarfMoveAnim)
    player.associateStateWithAnim(player.states.digging, animFactory.createDwarfDigAnim)
    
    
    if (sounds.dwarfNope != null)
    	player.associateStateWithSound(player.states.noGemsFound, sounds.dwarfNope)
    if (sounds.foundGems != null)
    	player.associateStateWithSound(player.states.foundGems, sounds.foundGems)
    if (sounds.digging != null)
    	player.associateStateWithSound(player.states.digging, sounds.digging)
    
    player.shovel = Shovel.shabbyShovel
    	
    player
  }
}