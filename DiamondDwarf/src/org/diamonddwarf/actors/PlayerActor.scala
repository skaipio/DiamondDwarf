package org.diamonddwarf.actors

import org.diamonddwarf.tileobjects.Player
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion
import org.diamonddwarf.Direction

class PlayerActor(textureRegion: Option[AtlasRegion]) extends TileObjectActor(Player, textureRegion) with MovingActor