package HRS;

import javax.management.relation.Role;
import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private Map<String, User> users;

    public UserManager() {
        users = new HashMap<>();
    }

    // Add a user to the UserManager
    public void addUser(User user) {
        users.put(user.getUsername(), user);
    }

    // Retrieve a user by username
    public User getUser(String username) {
        return users.get(username);
    }

    // Authenticate a user based on username and password
    public boolean authenticate(String username, String password) {
        User user = users.get(username);
        if (user != null) {
            return user.getPassword().equals(password);
        }
        return false;
    }

    // Authorize a user based on username and a required permission
    public boolean authorize(String username, String requiredPermission) {
        User user = users.get(username);
        if (user != null) {
            for (Role role : user.getRoles()) {
                if (role.getRoleName().contains(requiredPermission)) {
                    return true; // User has the required permission through a role
                }
            }
        }
        return false; // User does not have the required permission
    }

    // Assign a role to a user
    public void assignRole(String username, Role role) {
        User user = users.get(username);
        if (user != null) {
            user.addRole(role);
        }
    }

    // Remove a role from a user
    public void removeRole(String username, Role role) {
        User user = users.get(username);
        if (user != null) {
            user.removeRole(role);
        }
    }
}

