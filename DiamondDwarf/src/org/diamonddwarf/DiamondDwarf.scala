package org.diamonddwarf

import org.diamonddwarf.stage.Stage
import org.diamonddwarf.stage.Player

class DiamondDwarf(val player: Player) {
  var activeStage: Stage = null

  def movePlayer(direction: Coordinate) {
    val toBePosition = direction + activeStage.playerPosition
    if (activeStage.inBounds(toBePosition) && activeStage.tileAt(toBePosition).passable) {
      activeStage.setPlayerPosition(toBePosition)
    }
  }

  def playerDig = player.give(activeStage.playerTile.dig)
}