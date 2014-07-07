package org.diamonddwarf.boards

import org.diamonddwarf.actors.DDActor
import org.diamonddwarf.tileobjects.CollisionGroups
import fs.tileboard.board.ObjectTracker
import fs.tileboard.board.CollisionBoard
import org.diamonddwarf.actors.TileObjectActor

class ActorBoard(width: Int, height: Int, depth: Int, collisionSet : Set[(Int, Set[Int])], val base: (Int, Int))
  extends CollisionBoard[TileObjectActor](width, height, depth, CollisionGroups.collisionSet) with ObjectTracker[TileObjectActor]