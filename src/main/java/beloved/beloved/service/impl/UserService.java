package beloved.beloved.service.impl;

import beloved.beloved.entity.User;
import beloved.beloved.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void addUser(User user) {

    }

}
