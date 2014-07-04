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

class BoardFactory(resources: Resources, actorFactory: ActorFactory) {
  private val layers = 3
  def createBoard(boardID: Int, boardController: BoardController) = {
    require(boardID < resources.boardTemplates.size)
    val template = resources.boardTemplates(boardID)
    val board = new ActorBoard(template.width, template.height, layers)
    board.fill(() => actorFactory.createActorOf(Grass))
    board.add(actorFactory.createActorOf(Player), (template.baseX, template.baseY))
    this.setBaseAround(board, template.baseX, template.baseY)
    board
  }

  private def setBaseAround(board: ActorBoard, x: Int, y: Int) {
    require(this.minTilesFromBorders(board, x, y, 1))

    board.subregion((x - 1, y - 1, DwarfBase.layer), (x + 1, y + 1, DwarfBase.layer)).foreach(c => {
      val dwarfBase = actorFactory.createActorOf(DwarfBase)
      board.add(dwarfBase, (c._1, c._2))
    })
  }

  private def minTilesFromBorders(board: ActorBoard, x: Int, y: Int, padding: Int) = {
    x >= padding && y >= padding && x < board.width - padding && y < board.height - padding
  }
}