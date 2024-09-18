package com.odeon.odeonDemo.business.rules;

import com.odeon.odeonDemo.business.messages.UserMessages;
import com.odeon.odeonDemo.core.utilities.exceptions.types.BusinessException;
import com.odeon.odeonDemo.dataAccess.abstracts.UserRepository;
import com.odeon.odeonDemo.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserBusinessRules {
    UserRepository userRepository;

    public void userEmailCanNotBeDuplicated(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            throw new BusinessException(UserMessages.EMAIL_ALREADY_EXISTS);
        }
    }
}
