package org.diamonddwarf

import org.diamonddwarf.stage._
import org.diamonddwarf.items.Shovel
import scala.collection.mutable.Set

class DiamondDwarf(val player: Player) {
  private var _activeMap: Stage = null

  val actors = Set[Actor](player)

  def activeMap = this._activeMap

  def startStage(stage: Stage) {
    this._activeMap = stage
  }

  def movePlayer(direction: Coordinate) {
    val toBePosition = direction + _activeMap.playerPosition
    if (_activeMap.inBounds(toBePosition) && _activeMap.getTileObjectAt(toBePosition).isPassable) {
      player.activate(player.states.moving)
      _activeMap.setPlayerPosition(toBePosition)
      player.position = toBePosition
    }
  }

  def playerDig {
    if (this.activeMap.playerTile == Tile.diggableTile && !this.activeMap.isDug(this.activeMap.playerPosition) && player.canDig) {
      player.activate(player.states.digging)
      player.activeState.onceComplete(digFinished)
      player.resetAnimOfState(player.states.digging)
    }
  }

  private def digFinished {
    activeMap.removeGemAt(this.activeMap.playerPosition) match {
      case Some(gem) =>
        player.give(gem)
        player.activate(player.states.gotGem)
      case _ =>
    }

    activeMap.setDugAt(this.activeMap.playerPosition)
    player.depleteShovel

  }

  def detectGems = {
    player.activate(player.states.detectingGems)
    val playerPos = this.activeMap.playerPosition
    val gemsFound = this.activeMap.gemsBetween(playerPos + Coordinate(-1, -1), playerPos + Coordinate(1, 1)).length
    if (gemsFound == 0) {
      player.nextState = player.states.noGemsFound
    } else {
      player.nextState = player.states.foundGems
      player.states.foundGems.gemsFound = gemsFound
    }
    gemsFound
  }

  def build {
    val buildPos = this.player.front
    if (this.activeMap.hasBaseAt(buildPos) && !this.activeMap.hasTileObjectAt(buildPos)) {
      val toBuild = this.activeMap.buildables(this.activeMap.buildableIndex);q
      actors += toBuild
      this.activeMap.setTileObjectAt(buildPos, toBuild)
    }
  }

  def use {
    val usePosition = this.player.front
    if (this.activeMap.hasWorkshopAt(usePosition)) {
      val workshop = this.activeMap.getWorkshopAt(usePosition)
      if (workshop.activeState == workshop.states.idle) {
        workshop.activate(workshop.states.working)
        workshop.use(this.player)
      }
    }
  }

}