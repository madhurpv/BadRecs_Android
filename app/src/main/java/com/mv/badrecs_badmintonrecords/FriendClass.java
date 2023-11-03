package com.mv.badrecs_badmintonrecords;

public class FriendClass {

    String name;
    String description;
    int for_score = 0;        // My score against friend    // Only for 1v1
    int against_score = 0;   // His score against me       // Only for 1v1


    public FriendClass(){

    }

    public FriendClass(String name){
        this.name = name;
    }

}
