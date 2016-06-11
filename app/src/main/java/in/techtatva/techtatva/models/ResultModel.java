package in.techtatva.techtatva.models;

/**
 * Created by Naman on 6/9/2016.
 */
public class ResultModel {

    private int resultName;
    private int winnerTeamID;
    private int qualifiedTeam1ID;
    private int qualifiedTeam2ID;

    public ResultModel(){

    }

    public int getResultName() {
        return resultName;
    }

    public void setResultName(int resultName) {
        this.resultName = resultName;
    }

    public int getWinnerTeamID() {
        return winnerTeamID;
    }

    public void setWinnerTeamID(int winnerTeamID) {
        this.winnerTeamID = winnerTeamID;
    }

    public int getQualifiedTeam1ID() {
        return qualifiedTeam1ID;
    }

    public void setQualifiedTeam1ID(int qualifiedTeam1ID) {
        this.qualifiedTeam1ID = qualifiedTeam1ID;
    }

    public int getQualifiedTeam2ID() {
        return qualifiedTeam2ID;
    }

    public void setQualifiedTeam2ID(int qualifiedTeam2ID) {
        this.qualifiedTeam2ID = qualifiedTeam2ID;
    }
}
