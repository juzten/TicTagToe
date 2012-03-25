package net.mattknox.tictagtoe;



public class AiPlayer extends GameManager {
	
	
	

    /* the board */
    private int board[][];    
    /* empty */
    public static final int EMPTY = 0;
    /* player one */
    public static final int ONE = 1;
    /* player two */
    public static final int TWO = 2;   
    
    

    public AiPlayer() {
        board = new int[3][3];
    }
    
    public AiPlayer(TicTagToeGame state) {
    	board = new int[3][3];
    	for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
            	board[i][j] = state.getMoveString((i * 3) + j);    	
    }
    
    
    

    /* get the board value for position (i,j) */
    public int getBoardValue(int i,int j) {
        if(i < 0 || i >= 3) return EMPTY;
        if(j < 0 || j >= 3) return EMPTY;
        return board[i][j];
    }
    
    
    
    /* calculate the winning move for current token */
    public int []nextWinningMove(int token) {
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)            
                if(getBoardValue(i, j)==EMPTY) {
                    board[i][j] = token;
                    boolean win = isWin(token);
                    board[i][j] = EMPTY;
                    if(win) return new int[]{i,j};
                }
        return null;
    }
    
    
    
    /* calculate the best move for current token */
    public int nextMove(int token) {
                
        /* if we can move on the next turn */
        int winMove[] = nextWinningMove(token);
        if(winMove!=null) return (winMove[0] * 3) + winMove[1];

        /* choose the move that prevent enemy to win */
        for(int i=0;i<3;i++) 
            for(int j=0;j<3;j++)
                if(getBoardValue(i, j)==EMPTY)
                {
                    board[i][j] = token;
            boolean ok = nextWinningMove(token==ONE ? TWO : ONE ) == null;                    
                    board[i][j] = EMPTY;
                    if(ok) return (i * 3) + j;
                }
        
        /* lucky position in the center of board*/
        if(getBoardValue(1, 1)==EMPTY) return 4;
        
        /* choose available move */
        for(int i=0;i<3;i++) 
            for(int j=0;j<3;j++)
                if(getBoardValue(i, j)==EMPTY)
                    return (i * 3) + j;
        
        /* no move is available */
        return -1;
    }
    
    
    

    /* determine if current token is win or not win */
    public boolean isWin(int token) {
        final int DI[]={-1,0,1,1};
        final int DJ[]={1,1,1,0};
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++) {    
                
                /* we skip if the token in position(i,j) not equal current token */
                if(getBoardValue(i, j)!=token) continue;
                
                for(int k=0;k<4;k++) {
                    int ctr = 0;
                while(getBoardValue(i+DI[k]*ctr, j+DJ[k]*ctr)==token) ctr++;
                    if(ctr==3) return true;
                }
            }
        return false;
    }
}
