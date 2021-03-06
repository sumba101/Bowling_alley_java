import java.util.Date;
import java.util.HashMap;

public class Scorer {
    int bowlIndex;
    int[] curScores;
    int[][] cumulScores;
    int[][] finalScores;
    HashMap<Object, int[]> scores;

    public Scorer() {
        scores = new HashMap<>();
    }


    void iterateBalls(int[] curScore, int current) {
        for (int i = 0; i != current + 2; i++) {
            if (ballType(curScore, current, i)) break;
        }
    }

    boolean ballType(int[] curScore, int current, int i) {
        if (i % 2 == 1 && curScore[i - 1] + curScore[i] == 10 && i < current - 1 && i < 19) {
            //This ball was a the second of a spare.
            //Also, we're not on the current ball.
            //Add the next ball to the ith one in cumul.
            cumulScores[bowlIndex][(i / 2)] += curScore[i + 1] + curScore[i];
            //cumulScores[bowlIndex][i/2] += cumulScores[bowlIndex][i/2 -1];
        }
        else if (i < current && i % 2 == 0 && curScore[i] == 10 && i < 18) {
            return firstStrike(curScore, i);
        }
        else {
            normalThrow(curScore, i);
        }
        return false;
    }

    boolean firstStrike(int[] curScore, int i) {
        int strikeballs = getStrikeballs(curScore, i);
        return doubleStrikeStreak(curScore, i, strikeballs);
    }

    int getStrikeballs(int[] curScore, int i) {
        int strikeballs= 0;
        //This ball is the first ball, and was a strike.
        //If we can get 2 balls after it, good add them to cumul.
        if (curScore[i + 2] != -1) {
            strikeballs = 1;
            if (curScore[i + 3] != -1 || curScore[i + 4] != -1) {
                //Ok, got it.
                strikeballs = 2;
            }
        }
        return strikeballs;
    }

    boolean doubleStrikeStreak(int[] curScore, int i, int strikeballs) {
        if (strikeballs == 2) {
            //Add up the strike.
            //Add the next two balls to the current cumulscore.
            cumulScores[bowlIndex][i / 2] += 10;
            if (curScore[i + 1] != -1) {
                cumulScores[bowlIndex][i / 2] += curScore[i + 1] + cumulScores[bowlIndex][(i / 2) - 1];
                if (curScore[i + 2] != -1 && curScore[i + 2] != -2) {
                    cumulScores[bowlIndex][(i / 2)] += curScore[i + 2];
                } else if (curScore[i + 3] != -2) {
                    cumulScores[bowlIndex][(i / 2)] += curScore[i + 3];
                }
            } else {
                cumulScores[bowlIndex][i / 2] += curScore[i + 2];
                if (i / 2 > 0) {
                    cumulScores[bowlIndex][i / 2] += cumulScores[bowlIndex][(i / 2) - 1];
                }
                if (curScore[i + 3] != -1 && curScore[i + 3] != -2) {
                    cumulScores[bowlIndex][(i / 2)] += curScore[i + 3];
                }
                else {
                    cumulScores[bowlIndex][(i / 2)] += curScore[i + 4];
                }
            }
        }
        else {
            return true;
        }
        return false;
    }

    void normalThrow(int[] curScore, int i) {
        //We're dealing with a normal throw, add it and be on our way.
        if (i % 2 == 0 && i < 18) {
            if (i / 2 == 0 && curScore[i] != -2) {
                cumulScores[bowlIndex][i / 2] += curScore[i];
            }
            else {
                cumulScores[bowlIndex][i / 2] += cumulScores[bowlIndex][i / 2 - 1];
                //add his last frame's cumul to this ball, make it this frame's cumul.
                if (curScore[i] != -2) {
                    cumulScores[bowlIndex][i / 2] += curScore[i];
                }
            }
        }
        else if (i < 18) {
            if (curScore[i] != -1 && i > 2 && curScore[i] != -2) {
                cumulScores[bowlIndex][i / 2] += curScore[i];
            }
        }
        if (i / 2 == 9) {
            if (i == 18) {
                cumulScores[bowlIndex][9] += cumulScores[bowlIndex][8];
            }
            if (curScore[i] != -2) {
                cumulScores[bowlIndex][9] += curScore[i];
            }
        } else if (i / 2 == 10 && curScore[i] != -2) {
            cumulScores[bowlIndex][9] += curScore[i];
        }
    }


    /** getScore()
     *
     * Method that calculates a bowlers score
     *
     * @param Cur		The bowler that is currently up
     * @param frame	The frame the current bowler is on
     *
     * @return			The bowlers total score
     */
    void getScore( Bowler Cur, int frame ,int  ball) {
        int[] curScore;
        curScore = this.scores.get(Cur);
        for (int i = 0; i != 10; i++){
            this.cumulScores[this.bowlIndex][i] = 0;
        }
        int current = 2*(frame - 1)+ball-1;
        //Iterate through each ball until the current one.
        this.iterateBalls(curScore, current);
    }



    public void Onlastframe(int GameNumber, Bowler currentThrower){
        finalScores[bowlIndex][GameNumber] = cumulScores[bowlIndex][9];
        try{
            Date date = new Date();
            String dateString = "" + date.getHours() + ":" + date.getMinutes() + " " + date.getMonth() + "/" + date.getDay() + "/" + (date.getYear() + 1900);
            ScoreHistoryFile.addScore(currentThrower.getNick(), dateString, Integer.toString(cumulScores[bowlIndex][9]));
        } catch (Exception e) {System.err.println("Exception in addScore. "+ e );}
    }

    /** markScore()
     *
     * Method that marks a bowlers score on the board.
     *
     * @param Cur		The current bowler
     * @param frame	The frame that bowler is on
     * @param ball		The ball the bowler is on
     * @param score	The bowler's score
     */
    public void markScore( Bowler Cur, int frame, int ball, int score ){
        int[] curScore;
        int index =  ( (frame - 1) * 2 + ball);
        curScore = scores.get(Cur);
        curScore[ index - 1] = score;
        scores.put(Cur, curScore);
        getScore(Cur,frame,ball);
    }

    public void resetScore(Party party)
    {
        for (Object o : party.getMembers()) {
            int[] toPut = new int[25];
            for (int i = 0; i != 25; i++) {
                toPut[i] = -1;
            }
            scores.put(o, toPut);
        }
    }

}