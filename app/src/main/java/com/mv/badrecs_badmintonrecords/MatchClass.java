package com.mv.badrecs_badmintonrecords;

import java.util.ArrayList;
import java.util.List;

public class MatchClass {

    public static final int MATCH_WIN = 1;
    public static final int MATCH_LOSE = 0;
    public static final int MATCH_UNDECIDED = 2;

    public String finalScore;
    public List<String> scores = new ArrayList<>();
    public String description;
    public long time;
    public String title;
    public Integer win; // 1 if win, 0 if loose
    public String opponent1Name;
    public String opponent2Name;
    public String player1Name;
    public String player2Name;

}
