/** Represents a social network. The network has users, who follow other uesrs.
 *  Each user is an instance of the User class. */
public class Network {

    private User[] users;
    private int userCount;

    public Network(int maxUserCount) {
        this.users = new User[maxUserCount];
        this.userCount = 0;
    }

    public Network(int maxUserCount, boolean gettingStarted) {
        this(maxUserCount);
        users[0] = new User("Foo");
        users[1] = new User("Bar");
        users[2] = new User("Baz");
        userCount = 3;
    }

    public int getUserCount() {
        return this.userCount;
    }

    public User getUser(String name) {
        for (int i = 0; i < userCount; i++) {
            if (users[i].getName().equals(name)) {
                return users[i];
            }
        }
        return null;
    }

    public boolean addUser(String name) {
        if (userCount >= users.length || getUser(name) != null) {
            return false;
        }
        users[userCount] = new User(name);
        userCount++;
        return true;
    }

    public boolean addFollowee(String name1, String name2) {
        User user1 = getUser(name1);
        User user2 = getUser(name2);
        if (user1 == null || user2 == null) {
            return false;
        }
        return user1.addFollowee(name2);
    }
    
    public String recommendWhoToFollow(String name) {
        User user = getUser(name);
        if (user == null) {
            return null;
        }
        User bestUser = null;
        int maxMutual = 0;
        for (int i = 0; i < userCount; i++) {
            if (!users[i].getName().equals(name) && !user.follows(users[i].getName())) {
                int mutual = user.countMutual(users[i]);
                if (mutual > maxMutual) {
                    maxMutual = mutual;
                    bestUser = users[i];
                }
            }
        }
        return bestUser == null ? null : bestUser.getName();
    }

    public String mostPopularUser() {
        if (userCount == 0) {
            return null;
        }
        String mostPopular = users[0].getName();
        int maxCount = followeeCount(mostPopular);
        for (int i = 1; i < userCount; i++) {
            int count = followeeCount(users[i].getName());
            if (count > maxCount) {
                maxCount = count;
                mostPopular = users[i].getName();
            }
        }
        return mostPopular;
    }

    private int followeeCount(String name) {
        int count = 0;
        for (int i = 0; i < userCount; i++) {
            if (users[i].follows(name)) {
                count++;
            }
        }
        return count;
    }

    public String toString() {
        String result = "Network:\n";
        for (int i = 0; i < userCount; i++) {
            result += users[i].toString() + "\n";
        }
        return result;
    }
}
