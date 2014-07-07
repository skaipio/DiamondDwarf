package org.diamonddwarf.boards

import org.diamonddwarf.Resources
import org.diamonddwarf.actors.DDActor
import org.diamonddwarf.actors.ActorFactory
import org.diamonddwarf.BoardController
import org.diamonddwarf.tileobjects.Grass
import org.diamonddwarf.BoardController
import org.diamonddwarf.tileobjects.DwarfBase
import fs.tileboard.board.Board
import org.diamonddwarf.tileobjects.Player
import fs.tileboard.board.CollisionBoard
import fs.tileboard.board.ObjectTracker
import org.diamonddwarf.tileobjects.CollisionGroups

class BoardFactory(resources: Resources, actorFactory: ActorFactory) {

  private val layers = 3

  def createBoard(boardID: Int) = {
    require(boardID < resources.boardTemplates.size)
    val template = resources.boardTemplates(boardID)
    val board = boardFromTemplate(template)
    board.fill(() => actorFactory.createActorOf(Grass), Grass.layer)
    this.setBaseAround(board, template.baseX, template.baseY)
    board
  }

  private def boardFromTemplate(template: BoardTemplate) =
    new ActorBoard(template.width, template.height, layers,
      CollisionGroups.collisionSet, (template.baseX, template.baseY))

  private def setBaseAround(board: CollisionBoard[DDActor], x: Int, y: Int) {
    require(this.minTilesFromBorders(board, x, y, 1))

    board.subregion((x - 1, y - 1, DwarfBase.layer), (x + 1, y + 1, DwarfBase.layer)).foreach(c => {
      val dwarfBase = actorFactory.createActorOf(DwarfBase)
      board.spawn(dwarfBase, (c._1, c._2, DwarfBase.layer))
    })
  }

  private def minTilesFromBorders(board: CollisionBoard[DDActor], x: Int, y: Int, padding: Int) = {
    x >= padding && y >= padding && x < board.width - padding && y < board.height - padding
  }
}