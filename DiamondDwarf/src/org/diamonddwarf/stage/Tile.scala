package org.diamonddwarf.stage

private[stage] class Tile (val symbol: String, private var gem: Gem, val passable: Boolean) {

    def this(symbol: String, gem: Gem) = this(symbol, gem, true)
	def this(symbol: String, passable: Boolean) = this(symbol, Gem.noGem, passable)
  
  
	private var dug = false
	
	def dig = {
	  this.dug = true
	  val dugGem = this.gem
	  this.gem = Gem.noGem
	  dugGem 
	}
		
	def hasGem = this.gem != Gem.noGem
	def isDug = this.dug
  
	override def toString = if (this.dug) "O" else this.symbol
	
	

}