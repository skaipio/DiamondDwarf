package org.diamonddwarf

import org.diamonddwarf.stage.Stage
import org.diamonddwarf.stage.Player
import org.diamonddwarf.stage.TileMap
import org.diamonddwarf.stage.HomeBase
import org.diamonddwarf.stage.Coordinate

class DiamondDwarf(val player: Player) {
  private var stage: Stage = null
  private var homebase: HomeBase = null
  private var _activeMap: TileMap = null
  
  def activeMap = this._activeMap
  
  def startStage(stage: Stage){
    this.stage = stage
    this.homebase = new HomeBase(3,3)
    this._activeMap = this.stage
  }

  def movePlayer(direction: Coordinate) {
    val toBePosition = direction + _activeMap.playerPosition
    if (_activeMap.inBounds(toBePosition) && _activeMap.getTileAt(toBePosition).passable) {
      _activeMap.setPlayerPosition(toBePosition)
    }
  }

  def playerDig = player.give(_activeMap.playerTile.dig)
  
  def playerEnterArea = {
    val tile = _activeMap.playerTile
    if (tile.isEntryToBase){
      this._activeMap = this.homebase     
    }
    else if (tile.isExitFromBase)
      this._activeMap = this.stage
      this._activeMap.setPlayerPosition(this.stage.exitToHomeBase)
    }
}