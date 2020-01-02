package com.katas.billywonka.dieciseisdenoviembre;

import java.util.ArrayList;
import java.util.List;

public class FPT {
	private Solution s= new Solution();
	
	private int[] stations;
	public enum StrategyType {
		NORTH,
		NORTHEAST,
		EAST,
		SOUTHEAST,
		SOUTH,
		SOUTHWEST,
		WEST,
		NORTHWEST;
	}
	
	class BadPathException extends Exception{
		/**
		 * 
		 */
		private static final long serialVersionUID = -2501877605453161674L;		
	}
	/***
	 * 
	 * @param s
	 */
	public static void trace(String s){
		System.out.println(s);
	}
	/***
	 * 
	 * @param stations
	 */
	public FPT(int[] stations) {
		this.stations=stations;
		s.initialize(stations);
	}
	public int surroundingOne(){
		// TODO: calcular el rodeo
				return 60;
	}
	/*****
	 * 
	 * @return
	 * @throws BadPathException 
	 */
	public Solution surroundOne() throws BadPathException{
		int[] extendedStations=new int[5];
		extendedStations[0]=stations[0];
		extendedStations[1]=stations[1];
		extendedStations[2]=stations[2];
		extendedStations[3]=surroundingOne();
		extendedStations[4]=stations[3];
		Solution s1= new Solution();
		s1.initialize(extendedStations);
		s1.solve(0);
		return s1;
	}
	/******
	 * 
	 * @return
	 */
	public int surroundingThree(){
		// TODO: calcular el rodeo
		int fromX=(int)(stations[0]/10);
		int fromY=stations[0]%10;
		int toX=(int)(stations[0]/10);
		int toY=stations[0]%10;
		if(stations[0].)
		return 26;
	}
	public Solution surroundThree() throws BadPathException{
		int[] extendedStations=new int[5];
		extendedStations[0]=stations[0];
		extendedStations[1]=surroundingThree();
		extendedStations[2]=stations[1];
		extendedStations[3]=stations[2];
		extendedStations[4]=stations[3];
		Solution s1= new Solution();
		s1.initialize(extendedStations);
		s1.solve(0);
		return s1;
	}

	public List<Integer> solve() {
		Solution s1 = null,s2=null;
		try {
			s.solve(0);
			return (s.resultado!=null)?s.resultado:null;
		} catch (BadPathException e) {
			trace(">> S cross itself!");
			try {
				s1= surroundOne();
			} catch (BadPathException e1) {
				trace(">> S1 cross itself!");
			}
			try {
				s2= surroundThree();
			} catch (BadPathException e1) {
				trace(">> S2 cross itself!");
			}
		}
		if((s1!=null)&&(s2!=null))
			return (s1.resultado.size()<s2.resultado.size())?s1.resultado:s2.resultado;
		return null;
	}
	class Solution{
		private List<Integer> resultado=new ArrayList<Integer>();
		Station[] static_stations=null;
		public void initialize(int[] stations) {
			static_stations=new Station[stations.length];
			for(int i=0;i<stations.length;i++)
				static_stations[i]=new Station((int)(stations[i]/10), stations[i]%10, i);
			for(Station s:static_stations)
				s.setStrategy();
		}
		public void solve(int grado) throws BadPathException{
			static_stations[grado].track();
			if(grado<(static_stations.length-2))
				solve(grado+1);
			else
				resultado.add(10*static_stations[static_stations.length-1].x+static_stations[static_stations.length-1].y);
		}
	
	class Station{
		public int x,y,level;
		private StrategyType strategy;
		public boolean northFlankBlocked,southFlankBlocked,eastFlankBlocked,westFlankBlocked;
		public Station(int x, int y, int level){
			this.x=x;
			this.y=y;
			this.level=level;
			for(int i=0;i<level;i++)
			{
				if((static_stations[i].x==x)&&(static_stations[i].y==(y+1))){
					eastFlankBlocked=true;
					static_stations[i].westFlankBlocked=true;
				}
				if((static_stations[i].x==x)&&(static_stations[i].y==(y-1))){
					westFlankBlocked=true;
					static_stations[i].eastFlankBlocked=true;
				}
				if((static_stations[i].x==(x+1))&&(static_stations[i].y==y)){
					southFlankBlocked=true;
					static_stations[i].northFlankBlocked=true;
				}
				if((static_stations[i].x==(x-1))&&(static_stations[i].y==y)){
					northFlankBlocked=true;
					static_stations[i].southFlankBlocked=true;
				}
			}
		}
		private void check(int newCell) throws BadPathException{
			if(resultado.contains(newCell))
				throw new BadPathException();
		}
		/********************************************************************
		 * 
		 * @param level
		 * @return
		 */
		void track() throws BadPathException{
			int fromY=y;
			int toY=static_stations[level+1].y;
			int fromX=x;
			int toX=static_stations[level+1].x;

			switch(strategy) {
			case NORTH:
				trace("Goes north!");
				if(fromX>toX)
					for(int i=fromX;i>toX;i--){
						int newCell=10*i+toY;
						check(newCell);
						resultado.add(newCell);
					}
				break;
			case NORTHEAST:
				trace("Goes northeast!");
				if(eastFlankBlocked){
					resultado.add(10*fromX+fromY);
					fromX--;
				}
				if(fromY<toY)
					for(int i=fromY;i<toY;i++){
						int newCell=10*fromX+i;
						try{
							check(newCell);							
						}catch(BadPathException e)
						{
							if(fromX>toX){
								newCell=10*(fromX-1)+i-1;
								fromX--;
								i--;
							}
						}
						resultado.add(newCell);
					}
				if(fromX>toX)
					for(int i=fromX;i>toX;i--){
						int newCell=10*i+toY;
						check(newCell);
						resultado.add(newCell);
					}
				break;
			case EAST:
				trace("Goes east!");
				if(fromY<toY)
					for(int i=fromY;i<toY;i++){
						int newCell=10*fromX+i;
						check(newCell);
						resultado.add(newCell);
					}
				static_stations[level+1].westFlankBlocked=true;
				break;
			case SOUTHEAST:
				trace("Goes southeast!");
				if(eastFlankBlocked){
					resultado.add(10*fromX+fromY);
					fromX++;
				}
				if(fromY<toY)
					for(int i=fromY;i<toY;i++){
						int newCell=10*fromX+i;
						try{
							check(newCell);
						}catch(BadPathException e){
							if(fromX<toX){
								newCell=10*(fromX+1)+i-1;
								fromX++;
								i--;
							}
						}
						resultado.add(newCell);
					}
				if(fromX<toX)
					for(int i=fromX;i<toX;i++){
						int newCell=10*i+toY;
						check(newCell);
						resultado.add(newCell);
					}
				break;
			case SOUTH:
				trace("Goes south!");
				if(fromX<toX)
					for(int i=fromX;i<toX;i++){
						int newCell=10*i+toY;
						check(newCell);
						resultado.add(newCell);
					}
				break;
			case SOUTHWEST:
				trace("Goes southwest!");
				if(westFlankBlocked){
					resultado.add(10*fromX+fromY);
					fromX++;
				}
				if(fromY>toY)
					for(int i=fromY;i>toY;i--){
						int newCell=10*fromX+i;
						try{
							check(newCell);
						}catch(BadPathException e){
							if(fromX<toX){
								newCell=10*(fromX+1)+i+1;
								fromX++;
								i++;
							}
						}
						resultado.add(newCell);
					}
				if(fromX<toX)
					for(int i=fromX;i<toX;i++){
						int newCell=10*i+toY;
						check(newCell);
						resultado.add(newCell);
					}
				break;
			case WEST:
				trace("Goes west!");
				if(fromY>toY)
					for(int i=fromY;i>toY;i--){
						int newCell=10*fromX+i;
						check(newCell);
						resultado.add(newCell);
					}
				static_stations[level+1].eastFlankBlocked=true;
				break;
			case NORTHWEST:
				trace("Goes northwest!");
				if(westFlankBlocked){
					resultado.add(10*fromX+fromY);
					fromX--;
				}
				if(fromY>toY)
					for(int i=fromY;i>toY;i--){
						int newCell=10*fromX+i;
						try{
							check(newCell);
						}catch(BadPathException e){
							if(fromX>toX){
								newCell=10*(fromX-1)+i-1;
								fromX--;
								i--;
							}
						}
						resultado.add(newCell);
					}
				if(fromX>toX)
					for(int i=fromX;i>toX;i--){
						int newCell=10*i+toY;
						check(newCell);
						resultado.add(newCell);
					}
				break;
			default:
				// code block
			}
		}

		public void setStrategy(){
			if(level<(static_stations.length-1)){
				int fromY=y;
				int toY=static_stations[level+1].y;
				int fromX=x;
				int toX=static_stations[level+1].x;
				if(fromY<toY)
					if(fromX<toX)
						strategy=StrategyType.SOUTHEAST;
					else
						if(fromX>toX)
							strategy=StrategyType.NORTHEAST;
						else
							strategy=StrategyType.EAST;
				else
					if(fromY>toY)
						if(fromX<toX)
							strategy=StrategyType.SOUTHWEST;
						else
							if(fromX>toX)
								strategy=StrategyType.NORTHWEST;
							else
								strategy=StrategyType.WEST;
					else
						if(fromX<toX)
							strategy=StrategyType.SOUTH;
						else
							strategy=StrategyType.NORTH;
			}
		}
	}
	}
}
