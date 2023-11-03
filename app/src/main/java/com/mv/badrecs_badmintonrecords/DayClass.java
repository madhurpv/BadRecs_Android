package com.mv.badrecs_badmintonrecords;

import java.util.ArrayList;
import java.util.List;

public class DayClass {

    List<MatchClass> matches = new ArrayList<>();
    String date;
    String description;
    int no_of_matches = 0;

    public DayClass(String date){
        this.date = date;
    }

    public void add_match(MatchClass newMatch){
        matches.add(newMatch);
        no_of_matches++;
    }

    public void delete_match(int position){
        matches.remove(position);
        no_of_matches--;
    }


    public List<String> getMatchesTitles(){
        List<String> titles = new ArrayList<>();
        for(int i=0; i<no_of_matches; i++){
            titles.add(matches.get(i).title);
        }
        return titles;
    }

    public List<Long> getMatchesTimes(){
        List<Long> times = new ArrayList<>();
        for(int i=0; i<no_of_matches; i++){
            times.add(matches.get(i).time);
        }
        return times;
    }


    public List<String> getMatchesFinalScores(){
        List<String> scores = new ArrayList<>();
        for(int i=0; i<no_of_matches; i++){
            scores.add(matches.get(i).finalScore);
        }
        return scores;
    }


    public List<Integer> getMatchesWins(){
        List<Integer> wins = new ArrayList<>();
        for(int i=0; i<no_of_matches; i++){
            wins.add(matches.get(i).win);
        }
        return wins;
    }


}
