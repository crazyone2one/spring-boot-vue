package cn.master.phoenix.handler.validator;

import cn.master.phoenix.exception.InvalidEmailException;

/**
 * @author Created by 11's papa on 2025/6/10
 */
public class EmailValidator {
    public void validateEmail(String email) throws InvalidEmailException {
        if (email == null || !email.contains("@")) {
            throw new InvalidEmailException("Invalid email format", 4001);
        }
    }
}
