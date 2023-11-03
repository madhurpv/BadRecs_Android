package com.mv.badrecs_badmintonrecords;

import java.util.ArrayList;
import java.util.List;

public class StatsClass {


    List<FriendClass> friendClassList = new ArrayList<>();
    int no_of_friends = 0;


    public void add_friend(FriendClass friendClass){
        friendClassList.add(friendClass);
        no_of_friends++;
    }

    public void add_friend(String friendName){
        FriendClass friendClass = new FriendClass(friendName);
        friendClassList.add(friendClass);
        no_of_friends++;
    }

    public List<String> get_friends_list(){
        List<String> friendsList = new ArrayList<>();
        for(int i=0; i<no_of_friends; i++){
            friendsList.add(friendClassList.get(i).name);
        }
        return friendsList;
    }

    public boolean is_friend(FriendClass friendClass){
        return friendClassList.contains(friendClass);
    }

    public boolean is_friend(String friendName){
        for(int i=0; i<no_of_friends; i++){
            if(friendClassList.get(i).name.equals(friendName)){
                return true;
            }
        }
        return false;
    }

    public FriendClass get_friend(String friendName){
        for(int i=0; i<no_of_friends; i++){
            if(friendClassList.get(i).name.equals(friendName)){
                return friendClassList.get(i);
            }
        }
        return null;
    }

    public void update_friend(FriendClass friendClass){
        for(int i=0; i<no_of_friends; i++){
            if(friendClassList.get(i).equals(friendClass)){
                friendClassList.remove(i);
                friendClassList.add(friendClass);
                return;
            }
        }
    }



}
