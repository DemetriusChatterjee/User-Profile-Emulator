package Project1;

public class UserProfileTester {
   public static void main(String[] args) {

        // testing the add methods for adding a new user profile
        UserProfile user1 = new UserProfile("user1");
        user1.addInterest("interest1");
        user1.addInterest("interest2");
        user1.addInterest("interest3");
        // user1.addPost("post1");
        // user1.addPost("post2");
        // user1.addPost("post3");

        // testing the addFriend method
        UserProfile user2 = new UserProfile("user2");
        user2.addInterest("dd");
        user1.addFriend(user2);
        user2.addFriend(user1);

        // adding one more friend to user1
        UserProfile user3 = new UserProfile("user3");
        user1.addFriend(user3);
        user3.addFriend(user1);
        user3.addFriend(user2);
        user2.addFriend(user3);

        // testing the remove methods
        //user1.removeInterest("interest1");
        //user1.removePost("post2");
        //user1.removePost("post2");
        //user1.removeFriend(user1);
        //user3.removeFriend(user1);

        // testing the get methods
        System.out.println("User 1: " + user1.getUsername());
        //user1.outputInterests();
        //user1.outputPosts();
        user1.outputFriends();
        //System.out.println("Friends: " + user1.getFriends());
        //System.out.println("User 2: " + user2.getUsername());
        //user2.outputInterests();
        //user2.outputPosts();
        //System.out.println("Friends: " + user2.getFriends());
        System.out.println("User 3: " + user3.getUsername());
        System.out.println("Friends: " + user3.getFriends());


    }

    //!!!! try to remove a non existing interest, post, and friend
}
