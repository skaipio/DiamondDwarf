package org.diamonddwarf

import org.diamonddwarf.stage._
import org.diamonddwarf.items.Equipment
import scala.collection.mutable.Set
import org.diamonddwarf.stage.tileobjects.TileObject
import org.diamonddwarf.stage.tileobjects.Player

object DiamondDwarf {

  private var _activeMap: Stage = null
  var player : Player = null
  val actors : Set[Actor] = Set()

  def activeMap = this._activeMap

  def startStage(stage: Stage) {
    require(this.player != null)
    this._activeMap = stage
    actors += this.player
  }

  def moveOrBreakStone(direction: Coordinate) {
    val toBePosition = direction + _activeMap.playerPosition
    if (_activeMap.inBounds(toBePosition)) {
      if (_activeMap.getTileObjectAt(toBePosition).isPassable) {
        movePlayer(direction)
      } else if (_activeMap.getTileObjectAt(toBePosition) == TileObject.stone && player.canDig) {
        println("mining")
        player.activate(player.states.mining)
        player.activeState.doLast = () => miningFinished
        player.resetAnimOfState(player.states.digging)
      }
    }

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

    val canDig = this.activeMap.playerTile.isDiggable &&
      this.activeMap.getTileObjectAt(player.position) == TileObject.empty &&
      !this.activeMap.isDug(this.activeMap.playerPosition) &&
      player.canDig

    if (canDig) {
      player.activate(player.states.digging)
      player.activeState.doLast = () => digFinished
      player.resetAnimOfState(player.states.digging)
    }
  }

  private def miningFinished {
    activeMap.setTileObjectAt(this.activeMap.playerPosition + player.direction, TileObject.minedStone)
    player.depleteShovel
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
      val toBuild = this.activeMap.buildables(this.activeMap.buildableIndex);
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
      }
    }
  }

}