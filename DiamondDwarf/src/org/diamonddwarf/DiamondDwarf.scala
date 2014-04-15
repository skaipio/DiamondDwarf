package org.diamonddwarf

import org.diamonddwarf.stage._
import org.diamonddwarf.items.Shovel

class DiamondDwarf(val player: Player) {
  private var _activeMap: TileMap = null

  def activeMap = this._activeMap

  def startStage(stage: Stage) {
    this._activeMap = stage
    this.player.shovel = Shovel.shabbyShovel
  }

  def movePlayer(direction: Coordinate) {
    val toBePosition = direction + _activeMap.playerPosition
    if (_activeMap.inBounds(toBePosition) && _activeMap.getTileObjectAt(toBePosition).isPassable) {
      player.states.activate(player.states.moving) 
      _activeMap.setPlayerPosition(toBePosition)
    }
  }

  def hessu { println("hessu") }
  
  def playerDig = {
    player.states.activeState.onceComplete(hessu)
    player.states.activate(player.states.digging) 
    player.resetAnimOfState(player.states.digging)
    if (this.activeMap.playerTile == Tile.diggableTile && !this.activeMap.isDug(this.activeMap.playerPosition) && player.canDig) {
      activeMap.removeGemAt(this.activeMap.playerPosition) match {
        case Some(gem) => player.give(gem)
        case _ =>
      }
      activeMap.setDugAt(this.activeMap.playerPosition)
      player.depleteShovel
    }
  }
}