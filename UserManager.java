package Project1;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.HashSet;


public class UserManager {
    private static DoublyLinkedList<UserProfile> userProfiles;
    private static ArrayList<String> allUserProfileNames;

    // constructor
    public UserManager(){
        userProfiles = new DoublyLinkedList<UserProfile>();
        allUserProfileNames = new ArrayList<>();
    }

    // method adds a user profile
    public void addUserProfile(UserProfile user){
        userProfiles.addLast(user);
        allUserProfileNames.add(user.getUsername());
    }

    // method returns the user profile names
    public String getAllUserProfileNames(){
        String s = "";
        for(UserProfile name : userProfiles){
            s += name.getUsername() + ", ";
        }
        return s;
    }

    // method creates a txt file for a user profile 
    // with the user's name as the file name
    // and writes the user's information to the file
    public void createTxtFileForUserProfile(UserProfile user){
        try {
            Path directoryPath = Paths.get("allUserProfiles");
            Path filePath = directoryPath.resolve(user.getUsername() + ".txt");
            Files.createFile(filePath);
            // write the user's information to the file in String format

            List<String> userInfo = Arrays.asList(user.getUsername(),user.getInterests(),user.getPosts(),user.getFriends()); 
            Files.write(filePath, userInfo, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Error creating file for user: " + user.getUsername());
            e.printStackTrace();
        }

    }

    // method deletes the existing txt file for a user profile
    public void deleteTxtFileForUserProfile(UserProfile user){
        try {
            Path directoryPath = Paths.get("allUserProfiles");
            Path filePath = directoryPath.resolve(user.getUsername() + ".txt");
            Files.delete(filePath);
        } catch (IOException e) {
            System.err.println("Error deleting file for user: " + user.getUsername());
            // e.printStackTrace();
            return;
        }
    }

    // method scans all the text files in a folder
    // as one file is dedicated to one user profile
    public void scanAllTxtFilesInFolder(String folderPath) {
        Path dir = Paths.get(folderPath).toAbsolutePath();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.txt")) {
            for (Path entry : stream) {
                try (Scanner scanner = new Scanner(entry)) {
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();

                        // Process each line of the file
                        UserProfile user = new UserProfile(line);
                        userProfiles.addLast(user);

                        line = scanner.nextLine();
                        String[] interests = line.split(",");
                        for(String interest : interests){
                            user.addInterest(interest);
                        }


                        line = scanner.nextLine();
                        String[] posts = line.split("`");
                        for(String post : posts) {
                            String[] postData = post.split("-");
                            String data = postData[0];
                            int year = Integer.parseInt(postData[1]);
                            int month = Integer.parseInt(postData[2]);
                            int day = Integer.parseInt(postData[3]);
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(year, month, day);
                            Date postTimeStamp = calendar.getTime();
                            user.addPost(data, postTimeStamp);
                        }

                        line = scanner.nextLine();
                        String[] friends = line.split(",");
                        for (String friend : friends){
                            UserProfile friendProfile = null;
                            for(UserProfile userProfile : userProfiles){
                                if(userProfile.getUsername().equals(friend)){
                                    friendProfile = userProfile;
                                    break;
                                }
                            }
                            if(friendProfile == null){
                                System.out.println("Friend to add not found.");
                            }else{
                                user.addFriend(friendProfile);
                                friendProfile.addFriend(user);
                            }
                        }
                    }
                } catch (IOException e) {
                    System.err.println("Error reading file: " + entry);
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.err.println("Error accessing directory: " + folderPath);
            e.printStackTrace();
        }
    }    

    public static void main(String[] args) {
        UserManager manager = new UserManager();
        manager.scanAllTxtFilesInFolder("allUserProfiles");
        boolean exit = false;
        Scanner scanner = new Scanner(System.in);
        while(!exit){
            System.out.print("All user profiles: " + manager.getAllUserProfileNames() + "\n");
            System.out.println("Menu: ");
            System.out.println("1. Select a user to display all their posts");
            System.out.println("2. Select a user to display all their friends");
            System.out.println("3. Add a new user"); 
            System.out.println("4. Remove a user");
            System.out.println("5. Login as a user");
            System.out.println("6. Exit");
            System.out.print("Enter your choice number: ");
            try{
                int choice = scanner.nextInt();
                scanner.nextLine();
                while(choice > 6 || choice < 1){
                    System.out.println("Invalid input. Please enter a number between 1 and 5.");
                    System.out.print("Enter your choice number: ");
                    choice = scanner.nextInt();
                    scanner.nextLine();
                }
                if(choice == 1){
                    System.out.print("Enter the username: ");
                    String username = scanner.nextLine();
                   UserProfile user = null;
                    for(UserProfile userProfile : userProfiles){
                        if(userProfile.getUsername().equals(username)){
                            user = userProfile;
                            break;
                        }
                    }
                    if(user == null){
                        System.out.println("User not found.");
                    }else{
                        System.out.println("Posts: " + user.getPosts());
                    }
                }else if(choice == 2){
                    System.out.print("Enter the username: ");
                    String username = scanner.nextLine();
                    UserProfile user = null;
                    for(UserProfile userProfile : userProfiles){
                        if(userProfile.getUsername().equals(username)){
                            user = userProfile;
                            break;
                        }
                    }
                    if(user == null){
                        System.out.println("User not found.");
                    }else{
                        System.out.println("Friends: " + user.getFriends());
                    }
                }else if(choice == 3){
                    System.out.print("Enter the username: ");
                    String username = scanner.nextLine();
                    UserProfile user = new UserProfile(username);
                    System.out.print("Enter the interests (separated by commas(', ')): ");
                    String interests = scanner.nextLine();
                    String[] interestArray = interests.split(",");
                    for(String interest : interestArray){
                        user.addInterest(interest);
                    }
                    System.out.print("Enter the friends (separated by commas (',')): ");
                    String friends = scanner.nextLine();
                    String[] friendArray = friends.split(",");
                    for(String friend : friendArray){
                        UserProfile friendProfile = null;
                        for(UserProfile userProfile : userProfiles){
                            if(userProfile.getUsername().equals(friend)){
                                friendProfile = userProfile;
                                break;
                            }
                        }
                        if(friendProfile == null){
                            System.out.println("Friend not found.");
                        }else{
                            user.addFriend(friendProfile);
                            friendProfile.addFriend(user);
                        }
                    }
                    System.out.print("Enter your first post: ");
                    String post = scanner.nextLine();
                    post += "`";

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(2024, 01, 10);
                    Date postTimeStamp = calendar.getTime();

                    user.addPost(post, postTimeStamp);
                    manager.addUserProfile(user);
                    manager.createTxtFileForUserProfile(user);
                }else if(choice == 4){
                    System.out.print("Enter the username: ");
                    String username = scanner.nextLine();
                    UserProfile userToRemove = null;

                    // seeing if user exists
                    for(UserProfile userProfile : userProfiles){
                        if(userProfile.getUsername().equals(username)){
                            userToRemove = userProfile;
                            break;
                        }
                    }
                    if(userToRemove == null){
                        System.out.println("User not found.");
                    }else{
                        int index = 0;
                        // searching for the user in the friends' list
                        for(UserProfile searchPerson: userToRemove.getFriendsDoubly()){
                            index = 0;
                            // remove user from the userProfiles list
                            for(UserProfile person: userProfiles){
                                if(person.equals(userToRemove)){
                                    userProfiles.removeAtIndex(index);
                                    System.out.println(userToRemove.getUsername() + " removed successfully.");
                                }else if(person.getUsername().equals(searchPerson.getUsername())){
                                    int index2 = 0;
                                    // remove user from all their friends' friend list
                                    for(UserProfile removeFriend: person.getFriendsDoubly()){
                                        if(removeFriend.getUsername().equals(userToRemove.getUsername())){
                                            person.getFriendsDoubly().removeAtIndex(index2);
                                            person.getFriendArrayList().remove(userToRemove.getUsername());
                                            System.out.println("Person removed from: "+person.getUsername());
                                            manager.deleteTxtFileForUserProfile(person);
                                            manager.createTxtFileForUserProfile(person);
                                            break;
                                        }
                                        index2++;
                                    }
                                }
                                index++;
                            }
                        }
                    }
                    manager.deleteTxtFileForUserProfile(userToRemove);
                }else if(choice == 5){
                    System.out.print("Enter the username: ");
                    String username = scanner.nextLine();
                    UserProfile user = null;
                    for(UserProfile userProfile : userProfiles){
                        if(userProfile.getUsername().equals(username)){
                            user = userProfile;
                            System.out.println("Logged in as: " + user.getUsername());
                            break;
                        }
                    }
                    if(user == null){
                        System.out.println("User not found.");
                    }else{
                        boolean loggedIn = true;
                        while(loggedIn){
                            System.out.println("1. Display your friend's posts");
                            System.out.println("2. Add a post");
                            System.out.println("3. Check if two users are friends");
                            System.out.println("4. Find the most popular friend");
                            System.out.println("5. Reccomend a friend");
                            System.out.println("6. Logout");
                            System.out.print("Enter your choice number: ");
                            int userChoice = scanner.nextInt();
                            scanner.nextLine();
                            if(userChoice == 1){
                                for(UserProfile friend : user.getFriendsDoubly()){
                                    System.out.println(friend.getUsername() + "'s posts: " + friend.getPosts());
                                }
                            }else if(userChoice == 2){
                                System.out.print("Enter your post: ");
                                String post = scanner.nextLine();
                                post += "`";

                                Calendar calendar = Calendar.getInstance();
                                calendar.set(2024, 01, 10);
                                Date postTimeStamp = calendar.getTime();

                                user.addPost(post, postTimeStamp);
                                //delete the existing user file
                                manager.deleteTxtFileForUserProfile(user);
                                //write the post to the existing user's file
                                manager.createTxtFileForUserProfile(user);
                            }else if(userChoice == 3){
                                System.out.print("Enter the friend's username: ");
                                String friendUsername1 = scanner.nextLine();
                                System.out.println("Enter the other friend's username: ");
                                String friendUsername2 = scanner.nextLine();
                                UserProfile friendProfile = null;
                                for(UserProfile userProfile : userProfiles){
                                    if(userProfile.getUsername().equals(friendUsername1)){
                                        friendProfile = userProfile;
                                        UserProfile friendProfile2 = null;
                                        for(UserProfile friend : friendProfile.getFriendsDoubly()){
                                            if(friend.getUsername().equals(friendUsername2)){
                                                System.out.println(friendUsername1 + " and " + friendUsername2 + " are friends.");
                                                friendProfile2 = friend;
                                                break;
                                            }
                                        }
                                        if(friendProfile2 == null){
                                            System.out.println(friendUsername1 + " and " + friendUsername2 + " are not friends.");
                                        }
                                        break;
                                    }
                                }
                                if(friendProfile == null){
                                    System.out.println(friendUsername1 + " not found.");
                                }
                            }else if(userChoice == 4){
                                // Find the most popular friend
                                UserProfile mostPopularFriend = null;
                                int maxFriends = 0;
                                for(UserProfile friend : user.getFriendsDoubly()){
                                    if(friend.getFriendArrayList().size() > maxFriends){
                                        maxFriends = friend.getFriendArrayList().size();
                                        mostPopularFriend = friend;
                                    }
                                }
                                if(mostPopularFriend != null){
                                    System.out.println("Most popular friend: " + mostPopularFriend.getUsername() + " with " + maxFriends + " friends.");
                                }else{
                                    System.out.println("No friends found.");
                                }

                            }else if(userChoice == 5){
                                // Recommend a friend to user using a hash set
    
                                HashSet<String> userFriends = new HashSet<>(user.getFriendArrayList());
                                HashSet<String> userFriendsOfFriends;
                                boolean recFound = false;

                                for(UserProfile friend : user.getFriendsDoubly()){
                                    userFriendsOfFriends = new HashSet<>(friend.getFriendArrayList());
                                    userFriendsOfFriends.removeAll(userFriends); // remove user's friends from friend's friends
                                    userFriendsOfFriends.remove(user.getUsername()); // remove the user itself
                                    if(!userFriendsOfFriends.isEmpty()){
                                        System.out.println("Recommended friend: " + userFriendsOfFriends.iterator().next());
                                        recFound = true;
                                    }
                                }
                                if(!recFound){
                                    System.out.println("No recommendations available.");
                                }
                            }else if(userChoice == 6){
                                loggedIn = false;
                                System.out.println("Logged out as: " + user.getUsername()); 
                            }
                        }
                    }
                }else if(choice == 6){
                    exit = true;
                    scanner.close();
                }
            }catch(InputMismatchException e){
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }
    }
}