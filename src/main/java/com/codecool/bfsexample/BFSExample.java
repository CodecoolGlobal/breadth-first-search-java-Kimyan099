package com.codecool.bfsexample;

import com.codecool.bfsexample.model.UserNode;

import java.util.*;

public class BFSExample {

    private static void populateDB() {


        RandomDataGenerator generator = new RandomDataGenerator();
        List<UserNode> users = generator.generate();

        UserNode starterUser = users.get(6);
        UserNode searchedUser = users.get(34);

        GraphPlotter graphPlotter = new GraphPlotter(users);

        Set<UserNode> starter = new HashSet<>(Arrays.asList(starterUser));
        graphPlotter.highlightNodes(starter, searchedUser);
        int distance = calculateMinimumDistance(starterUser, searchedUser);
        Set<UserNode> friends = getFriendsAtGivenDistance(starterUser,2);
        System.out.println(starterUser + "friends are: " +friends);
        System.out.println(distance);
        System.out.println("Done!");
    }

    private static int calculateMinimumDistance(UserNode starterNode, UserNode searchedNode) {
        System.out.println("starter friend name: " + starterNode.getFirstName() + " " + starterNode.getLastName());
        System.out.println("Searched friend name: " + searchedNode.getFirstName() + " " + searchedNode.getLastName());

        Queue<UserNode> nodeQueue = new LinkedList<>();
        int depth = 0;
        nodeQueue.add(starterNode);

        while (!nodeQueue.isEmpty()) {
            int numberOfNodes = nodeQueue.size();

            while (numberOfNodes != 0) {
                UserNode currentNode = nodeQueue.element();
                if (isMatch(currentNode, searchedNode)) {
                    depth++;
                    return depth;
                }
                Set<UserNode> currentFriends = currentNode.getFriends();
                for (UserNode currentFriend : currentFriends) {
                    if (isMatch(currentFriend, searchedNode)) {
                        depth++;
                        return depth;
                    }
                    nodeQueue.add(currentFriend);
                }
                numberOfNodes--;
                nodeQueue.poll();
            }
            depth++;
        }
        System.out.println("Friend not found");
        return depth;
    }

    private static boolean isMatch(UserNode currentNode, UserNode searchedNode) {
        return currentNode.getFirstName().equals(searchedNode.getFirstName())
                && currentNode.getLastName().equals(searchedNode.getLastName());
    }

    private static Set<UserNode> getFriendsAtGivenDistance(UserNode starterUser, int distance) {
        Set<UserNode> friends = new HashSet<>();
        Queue<UserNode> notVisitedQueue = new LinkedList<>();
        Queue<UserNode> visitedQueue = new LinkedList<>();
        int depth = 0;
        notVisitedQueue.add(starterUser);

        while(depth < distance) {
            int numberOfNodes = notVisitedQueue.size();
            while(numberOfNodes != 0) {
                UserNode currentNode = notVisitedQueue.element();
                Set<UserNode> currentFriends = currentNode.getFriends();
                for (UserNode currentFriend : currentFriends) {
                    notVisitedQueue.add(currentFriend);
                    if(depth + 1 == distance && !isCurrentFriendVisited(currentFriend, visitedQueue)) {
                        friends.add(currentFriend);
                    }
                }
                UserNode visitedNode = notVisitedQueue.poll();
                visitedQueue.add(visitedNode);
                numberOfNodes--;
            }
            depth++;
        }
        return friends;
    }

    private static boolean isCurrentFriendVisited(UserNode currentFriend, Queue<UserNode> visitedQueue) {
        boolean isVisited = false;
        for (UserNode userNode : visitedQueue) {
            if (userNode.equals(currentFriend)) {
                isVisited = true;
            }
        }
        return isVisited;
    }







    public static void main(String[] args) {
        populateDB();
    }
}
