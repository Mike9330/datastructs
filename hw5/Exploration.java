package hw5;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.Collection;

public class Exploration {
	private Maze m;
	boolean[][]marked; 
    int[] edgeTo;   
    final int s=0; 
    
	public Exploration(Maze m) {
		this.m = m;
		marked = new boolean[s][s];
		edgeTo = new int[s];
		
	}
	
	// return the number of monsters in maze m
	public int numMonsters() {
		return monsterHelp(m,0,0);
		// TODO
	}
	
	public int monsterHelp(Maze m, int x, int y){
		ArrayList<IntPair>monsters = new ArrayList<IntPair>();
		marked[x][y]=true;
		if(m.peek(Maze.EAST)!=Maze.WALL){
			if(m.peek(Maze.EAST)==Maze.MONSTER){
				IntPair loc=new IntPair(x,y+1);
				monsters.add(loc);
			}

				monsterHelp(m,x,y+1);
			
		}
		if(m.peek(Maze.WEST)!=Maze.WALL){
			if(m.peek(Maze.WEST)==Maze.MONSTER){
				IntPair loc=new IntPair(x,y-1);
				monsters.add(loc);
			}
	
				monsterHelp(m,x,y-1);
			
		}
		if(m.peek(Maze.NORTH)!=Maze.WALL){
			if(m.peek(Maze.NORTH)==Maze.MONSTER){
				IntPair loc=new IntPair(x+1,y);
				monsters.add(loc);
			}
			
				monsterHelp(m,x+1,y);
		
	}
		if(m.peek(Maze.SOUTH)!=Maze.WALL){
			if(m.peek(Maze.SOUTH)==Maze.MONSTER){
				IntPair loc=new IntPair(x-1,y);
				monsters.add(loc);
			}

				monsterHelp(m,x-1,y);
			
		
		
	}
		int counter=0;
		for(int i=0;i<monsters.size();i++){
			counter++;
		}
		return counter;
	}
			

	
	// walk to location l in the maze m
	public void walkTo(IntPair l) {
		walkToHelp(m,0,0,l);
		// TODO
	}
	
	public void walkToHelp(Maze m, int x, int y,IntPair l){
		marked[x][y]=true;
		if(m.peek(Maze.EAST)!=Maze.WALL){
			IntPair current=new IntPair(x,y);
			if(m.peek(Maze.EAST)==l{
				m.walk(Maze.EAST);
				
			}
			
		}
		if(m.peek(Maze.WEST)!=Maze.WALL){
			if(m.peek(Maze.WEST)==l){
				m.walk(Maze.WEST);
			}
	
				monsterHelp(m,x,y-1);
			
		}
		if(m.peek(Maze.NORTH)!=Maze.WALL){
			if(m.peek(Maze.NORTH)==l){
				m.walk(Maze.NORTH);
			}
			
				monsterHelp(m,x+1,y);
		
	}
		if(m.peek(Maze.SOUTH)!=Maze.WALL){
			if(m.peek(Maze.SOUTH)==l){
				m.walk(Maze.SOUTH);
			}

				monsterHelp(m,x-1,y);
			
		
		
	}
	}
	// find the location of a weapon, return null if none exists 
	public IntPair findWeapon() {
		// TODO
		return null;
	}
	
	public void weaponHelp(Maze m, int x, int y,IntPair l){
		marked[x][y]=true;
		if(m.peek(Maze.EAST)!=Maze.WALL){
			IntPair current=new IntPair(x,y);
			if(m.peek(Maze.EAST)==Maze.WEAPON){
				m.pickUpWeapon();;
				
			}
			
		}
		if(m.peek(Maze.WEST)!=Maze.WALL){
			if(m.peek(Maze.WEST)==Maze.WEAPON){
				m.pickUpWeapon();
			}
	
				monsterHelp(m,x,y-1);
			
		}
		if(m.peek(Maze.NORTH)!=Maze.WALL){
			if(m.peek(Maze.NORTH)==Maze.WEAPON){
				m.pickUpWeapon();
			}
			
				monsterHelp(m,x+1,y);
		
	}
		if(m.peek(Maze.SOUTH)!=Maze.WALL){
			if(m.peek(Maze.SOUTH)==Maze.WEAPON){
				m.pickUpWeapon();
			}

				monsterHelp(m,x-1,y);
			
		
		
	}
	}
	
	// find a weapon, pick it up, and kill all of the monsters in maze m 
	public void killMonsters() {
		killHelp(m,0,0);
		// TODO
	}
	
	public void killHelp(Maze m, int x, int y){
		marked[x][y]=true;
		if(m.peek(Maze.EAST)!=Maze.WALL){
			if(m.peek(Maze.EAST)==Maze.MONSTER){
				m.kill();
			}

				monsterHelp(m,x,y+1);
			
		}
		if(m.peek(Maze.WEST)!=Maze.WALL){
			if(m.peek(Maze.WEST)==Maze.MONSTER){
				m.kill();
			}
	
				monsterHelp(m,x,y-1);
			
		}
		if(m.peek(Maze.NORTH)!=Maze.WALL){
			if(m.peek(Maze.NORTH)==Maze.MONSTER){
				m.kill();
			}
			
				monsterHelp(m,x+1,y);
		
	}
		if(m.peek(Maze.SOUTH)!=Maze.WALL){
			if(m.peek(Maze.SOUTH)==Maze.MONSTER){
				m.kill();
			}

				monsterHelp(m,x-1,y);
			
		}
	}
	
	// walk to the nearest exit and return its location
	// distance of an exit from current location is the fewest number of hops needed to reach that exit
	public IntPair findNearestExit() {
		findHelper(m,0,0);
		// TODO
		return null;
	}
	
	public void findHelper(Maze m,int x,int y){
		marked[x][y]=true;
		if(m.peek(Maze.EAST)!=Maze.WALL){
			if(m.peek(Maze.EAST)==Maze.EXIT){
				m.walk(Maze.EAST);
				return;
			}

				monsterHelp(m,x,y+1);
			
		}
		if(m.peek(Maze.WEST)!=Maze.WALL){
			if(m.peek(Maze.WEST)==Maze.EXIT){
				m.walk(Maze.WEST);
				return;
			}
	
				monsterHelp(m,x,y-1);
			
		}
		if(m.peek(Maze.NORTH)!=Maze.WALL){
			if(m.peek(Maze.NORTH)==Maze.EXIT){
				m.walk(Maze.NORTH);
				return;
			}
			
				monsterHelp(m,x+1,y);
		
	}
		if(m.peek(Maze.SOUTH)!=Maze.WALL){
			if(m.peek(Maze.SOUTH)==Maze.EXIT){
				m.walk(Maze.SOUTH);
				return;
			}

				monsterHelp(m,x-1,y);
			
		}
	
	}
}