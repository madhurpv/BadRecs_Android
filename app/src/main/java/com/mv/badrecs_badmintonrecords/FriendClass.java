package com.mv.badrecs_badmintonrecords;

public class FriendClass {

    String name;
    String description;
    int for_score = 0;        // My wins against friend
    int against_score = 0;   // His wins against me
    int won_with = 0;       // Matches won as teammate
    int lost_with = 0;     // Matches lost as teammate

    int for_points = 0;
    int against_points = 0;


    public FriendClass(){

    }

    public FriendClass(String name){
        this.name = name;
    }

}
