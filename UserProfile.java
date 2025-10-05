package Project1;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class UserProfile {
    private String username;
    private DoublyLinkedList<String> interests;
    private DoublyLinkedList<Post> posts;
    private DoublyLinkedList<UserProfile> friends;

    // array lists for the names of the interests, posts, and friends 
    // which will help find the index of specific interest, post, or friend to remove
    private ArrayList<String> interestNames;
    private ArrayList<String> postNames;
    private ArrayList<String> friendNames;
    private ArrayList<Date> postTimeStampList;

    // constructor
    public UserProfile(String username){
        this.username = username;
        this.interests = new DoublyLinkedList<String>();
        this.posts = new DoublyLinkedList<Post>();
        this.friends = new DoublyLinkedList<UserProfile>();
        this.interestNames = new ArrayList<>();
        this.postNames = new ArrayList<>();
        this.friendNames = new ArrayList<>();
        this.postTimeStampList = new ArrayList<>();
    }

    // method gets the username
    public String getUsername(){
        return username;
    }

    // method returns head
    public DoublyLinkedList<String>.Node<String> getInterestHead(){
        return interests.getHead();
    }

    // method returns head
    public DoublyLinkedList<Post>.Node<Post> getPostHead(){
        return posts.getHead();
    }

    // method returns the interests of the user
    public String getInterests(){
        String s = "";
        for(String interest : interests){
            s += interest + ", ";
        }
        return s;
    }

    // method returns the posts of the user in order of creation
    public String getPosts(){
        String s = "";
        Calendar calendar = Calendar.getInstance();
        int totalPosts = postNames.size();

        for(int i = 0; i < totalPosts; i++){
            calendar.setTime(postTimeStampList.get(i));
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH); 
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            s += postNames.get(i) + "-" + year + "-" + month + "-" + day + "` ";  // format is "postData-YYYY-MM-DD"
        }
        return s;
    }

    // method gets the friends of the user
    public String getFriends(){
        String s = "";
        for(String friend : friendNames) {
            s += friend + ", ";
        }
        return s;
    }

    // returns the doubly linked list of friends
    public DoublyLinkedList<UserProfile> getFriendsDoubly(){
        return friends;
    }

    // returns the array list of friends
    public ArrayList<String> getFriendArrayList(){
        return friendNames;
    }

    // returns the post date list
    public ArrayList<Date> getPostTimeStampList(){
        return postTimeStampList;
    }

    // method adds an interest to the user
    public void addInterest(String interest){
        interests.addLast(interest);
        interestNames.add(interest);
    }

    // method adds a post to the user
    public void addPost(String postData, Date postTimeStamp){
        Post newPost = new Post(postData, postTimeStamp);
        posts.addLast(newPost);
        postNames.add(postData);
        postTimeStampList.add(postTimeStamp);
    }

    // method adds a friend to the user
    public void addFriend(UserProfile friend){
        friends.addLast(friend);
        friendNames.add(friend.getUsername());
        System.out.println(friend.getUsername() + " added as a friend.");
    }

    // method removes the specific interest from the user
    public void removeInterest(String interest){
        int index = 0;
        for(String interestName : interestNames){
            if(interestName.equals(interest)){
                interests.removeAtIndex(index);
                interestNames.remove(interest);
                System.out.println("Interest removed.");
                return;
            }
            index++;
        }
        System.out.println("Interest to remove not found.");
    }

    // method removes the last post from the user
    public void removePost(String post){
        int index = 0;
        for(String postName : postNames){
            if(postName.equals(post)){
                posts.removeAtIndex(index);
                postNames.remove(post);
                postTimeStampList.remove(index);
                System.out.println("Post removed.");
                return;
            }
            index++;
        }
        System.out.println("Post to remove not found.");
    }

    // method removes the last friend from the user
    public void removeFriend(UserProfile friend, int index){
        friends.removeAtIndex(index);
        friendNames.remove(friend.getUsername());
        System.out.println(friend.getUsername() + " removed.");
        
        if(friend.getUsername().equals(this.getUsername())){
            System.out.println("You cannot remove yourself as a friend.");
        }
    }

    // method removes the last friend from the user, function overloading
    public void removeFriend(String friend, ArrayList<String> friendNames, DoublyLinkedList<UserProfile> friends){
        int index = 0;
        for(String friendName : friendNames){
            if(friendName.equals(friend)){
                friends.removeAtIndex(index);
                friendNames.remove(friend);
                System.out.println("Friend removed.");
                return;
            }
            index++;
        }
        System.out.println("Friend to remove not found.");
    }

    // method to output the friends of the user
    public void outputFriends(){
        System.out.print("Friends: ");
        friends.printList();
    }

    // method to output the interests of the user
    public void outputInterests(){
        System.out.print("Interests: ");
        interests.printList();
    }

    // method to output the posts of the user
    public void outputPosts(){
        System.out.print("Posts: ");
        posts.printList();
    }
}