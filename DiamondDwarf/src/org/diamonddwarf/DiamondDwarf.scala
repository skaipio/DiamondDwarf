package org.diamonddwarf

import org.diamonddwarf.stage.Stage
import org.diamonddwarf.stage.Player
import org.diamonddwarf.stage.TileMap
import org.diamonddwarf.stage.Coordinate
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
    if (_activeMap.inBounds(toBePosition) && _activeMap.getTileAt(toBePosition).passable) {
      _activeMap.setPlayerPosition(toBePosition)
    }
  }

  def playerDig = {
    if (this.activeMap.playerTile.canBeDug && this.player.dig) {
      player.give(_activeMap.playerTile.dig)
    }
  }
}