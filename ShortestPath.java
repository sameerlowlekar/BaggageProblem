//Description : An implementation of a node traversal as part of Baggage problem statements

package com.baggage.details;

import java.util.Scanner;

public class ShortestPath {
	
	public void GetShortestPath(int entry, int exit){
		Scanner scan = new Scanner(System.in);
		int[][] matrix = new int[5][5];
		int [] dist = new int[5];
		int [] visited = new int[5];
		int [] previous = new int[5];
		int min;
		int nextN = 0;
		int entrygate=entry;
		int exitgate=exit;
		
		System.out.println("Enter the matrix corresponding to Graph");
		
		
		for(int i=0; i<5;i++){
			
			visited[i]=0;
			previous[i]=0;
			
			for(int j=0;j<5;j++){
				
				matrix[i][j]=scan.nextInt();
				if(matrix[i][j]==0){
					matrix[i][j]=999;
				}
			}
			
		}
		dist = matrix[entrygate];
		dist[entrygate]=0;
		visited[entrygate]=1;
		
		//Algorithm.
		
		for(int i=0;i<5;i++){
			min =999;
			for(int j=0;j<5;j++){
				if(min>dist[j] && visited[j]!=1){
					min=dist[j];
					nextN=j;
					
				}
			}
			
			visited[nextN]=1;			
			for(int c=0;c<5;c++){
				
				if(visited[c]!=1){
					if(min+matrix[nextN][c]<dist[c]){
						dist[c] = min+matrix[nextN][c];
						previous[c] = nextN;
					}
				}
			}
			
		}
		
		for(int i=0;i<5;i++){
			if(i==exitgate)
			System.out.print("|The time taken to travel from Entry Gate 'A"+entrygate+"' to Exit Gate: 'A"+exitgate+"' is : " + dist[i] +"\n");
			
		}
		System.out.print("|");
		
		// Paths traversed from root node to others
		
		for(int i=0;i<5;i++){
			int j;
			
			System.out.println("Path= " + i);
			j=i;
			
			do{
				j = previous[j];
				System.out.println("<-" + j);
				
			}while(j!=0);
			
			System.out.println();
			
		}
		
		
	}


}
